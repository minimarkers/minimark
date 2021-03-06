/**
 * Copyright (C) 2009 Lucio Benfante <lucio.benfante@gmail.com>
 *
 * This file is part of minimark Web Application.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.benfante.minimark.controllers;

import com.benfante.minimark.beans.ImportQuestionsBean;
import com.benfante.minimark.blo.ImporterBo;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.Question;
import com.benfante.minimark.util.Utilities;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.parancoe.web.util.FlashHelper;
import org.parancoe.web.validation.Validation;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

/**
 * The controller managing the courses.
 *
 * @author lucio
 */
@Controller
@RequestMapping("/course/*.html")
@SessionAttributes({CourseController.COURSE_ATTR_NAME,
    CourseController.IMPORT_QUESTIONS_ATTR_NAME})
public class CourseController {

    private static final Logger logger = Logger.getLogger(CourseController.class);
    public static final String COURSE_ATTR_NAME = "course";
    public static final String IMPORT_QUESTIONS_ATTR_NAME = "importQuestions";
    public static final String EDIT_VIEW = "course/edit";
    public static final String LIST_VIEW = "course/list";
    @Resource
    private CourseDao courseDao;
    @Resource
    private UserProfileBo userProfileBo;
    @Resource
    private ImporterBo importerBo;
    @Resource
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping
    public String edit(@RequestParam("id") Long id, Model model) {
        Course course = courseDao.get(id);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        userProfileBo.checkEditAuthorization(course);
        model.addAttribute(COURSE_ATTR_NAME, course);
        return EDIT_VIEW;
    }

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(@ModelAttribute(COURSE_ATTR_NAME) Course course,
            BindingResult result, SessionStatus status) {
        userProfileBo.checkEditAuthorization(course);
        courseDao.store(course);
        status.setComplete();
        return "redirect:list.html";
    }

    @RequestMapping
    public String list(Model model) {
        List<Course> courses = courseDao.findByTeacherUsername(userProfileBo.
                getAuthenticatedUsername());
        model.addAttribute("courses", courses);
        return LIST_VIEW;
    }

    @RequestMapping
    public String create(Model model) {
        Course course = new Course();
        course.addTeacher(userProfileBo.getCurrentUser());
        model.addAttribute(COURSE_ATTR_NAME, course);
        return EDIT_VIEW;
    }

    @RequestMapping
    public String delete(@RequestParam("id") Long id, HttpServletRequest req,
            Model model) {
        Course course = courseDao.get(id);
        userProfileBo.checkEditAuthorization(course);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        if (course.getAssessments() != null && !course.getAssessments().isEmpty()) {
            FlashHelper.setRedirectError(req,
                    "Flash.CantDeleteCourseBecauseAssessments");
        } else {
            courseDao.delete(course);
            FlashHelper.setRedirectNotice(req, "Flash.CourseDeleted");
        }
        return "redirect:list.html";
    }

    @RequestMapping
    public void importQuestions(@RequestParam("courseId") Long id, Model model) {
        Course course = courseDao.get(id);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        userProfileBo.checkEditAuthorization(course);
        model.addAttribute(COURSE_ATTR_NAME, course);
        model.addAttribute(IMPORT_QUESTIONS_ATTR_NAME, new ImportQuestionsBean());
    }

    @RequestMapping
    public void exportQuestions(@RequestParam("courseId") Long id, HttpServletRequest req, HttpServletResponse res) {
        String result = "";
        Course course = courseDao.get(id);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        userProfileBo.checkEditAuthorization(course);
        result = importerBo.exportCourseQuestions(course);
        OutputStream out = null;
        try {
            byte[] contentBytes = result.getBytes("UTF-8");
            out = res.getOutputStream();
            res.setContentType("text/plain");
            res.setContentLength(contentBytes.length);
            res.setHeader("Content-Disposition",
                    " attachment; filename=\"" + course.getName() + "_questions.txt\"");
            res.setHeader("Expires", "0");
            res.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            res.setHeader("Pragma", "public");
            out.write(contentBytes);
            out.flush();
        } catch (Exception ex) {
            logger.error("Can't export questions for course " +
                    course.getName() + " (" + course.getId() + ")", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                }
            }
        }
    }
    
    @RequestMapping
    public String doImportQuestions(
            @ModelAttribute(COURSE_ATTR_NAME) Course course, @ModelAttribute(
            IMPORT_QUESTIONS_ATTR_NAME) ImportQuestionsBean importQuestionsBean,
            BindingResult result, HttpServletRequest request,
            SessionStatus status, Locale locale) {
        userProfileBo.checkEditAuthorization(course);
        try {
            List<Question> questions = importerBo.readQuestionSet(
                    new StringReader(new String(importQuestionsBean.
                    getImportFile(), "UTF-8")));
            importerBo.addQuestionsToCourse(course, questions);
            status.setComplete();
            FlashHelper.setRedirectNotice(request,
                    "QuestionsImportedSuccessfully");
        } catch (Exception ex) {
            StringBuilder message = new StringBuilder();
            message.append(messageSource.getMessage("ErrorImportingQuestions",
                    null, locale)).append(": ").append(ex.getLocalizedMessage());
            if (ex instanceof ParseException) {
                message.append(" (").append(messageSource.getMessage("atLine",
                        new Object[]{((ParseException) ex).getErrorOffset()},
                        locale)).append(")");
            }
            FlashHelper.setError(request, message.toString());
            return "course/importQuestions";
        }
        return "redirect:list.html";
    }
}
