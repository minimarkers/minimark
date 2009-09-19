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

import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.blo.AssessmentBo;
import com.benfante.minimark.blo.QuestionBo;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.Course;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.parancoe.web.util.FlashHelper;
import org.parancoe.web.validation.Validation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * The controller managing the assessments.
 *
 * @author lucio
 */
@Controller
@RequestMapping("/assessment/*.html")
@SessionAttributes(AssessmentController.ASSESSMENT_ATTR_NAME)
public class AssessmentController {

    public static final String ASSESSMENT_ATTR_NAME = "assessment";
    public static final String ASSESSMENT_QUESTIONS_ATTR_NAME =
            "assessmentQuestions";
    public static final String QUESTION_SEARCH_ATTR_NAME = "questionSearch";
    public static final String QUESTION_SEARCH_RESULT_ATTR_NAME =
            "questionSearchResult";
    public static final String EDIT_VIEW = "assessment/edit";
    public static final String LIST_VIEW = "assessment/list";
    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private UserProfileBo userProfileBo;
    @Resource
    private QuestionBo questionBo;
    @Resource
    private AssessmentBo assessmentBo;

    @RequestMapping
    public String edit(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        userProfileBo.checkEditAuthorization(assessment);
        assessment.setNewPassword(assessment.getPassword());
        assessment.setConfirmPassword(assessment.getPassword());
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessment);
        return EDIT_VIEW;
    }

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(
            @ModelAttribute(ASSESSMENT_ATTR_NAME) Assessment assessment,
            BindingResult result, SessionStatus status) {
        userProfileBo.checkEditAuthorization(assessment);
        assessment.setPassword(assessment.getNewPassword());
        assessmentDao.store(assessment);
        status.setComplete();
        return "redirect:list.html";
    }

    @RequestMapping
    public String list(Model model, SessionStatus status) {
        status.setComplete(); // clean start
        List<Assessment> assessments = assessmentDao.findByTeacherUsername(
                userProfileBo.getAuthenticatedUsername());
        model.addAttribute("assessments", assessments);
        return LIST_VIEW;
    }

    @RequestMapping
    public String create(@RequestParam("courseId") Long courseId, Model model) {
        final Course course = courseDao.get(courseId);
        userProfileBo.checkEditAuthorization(course);
        Assessment assessment = new Assessment();
        assessment.setCourse(course);
        assessment.setAssessmentDate(new Date());
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessment);
        return EDIT_VIEW;
    }

    @RequestMapping
    public String delete(@RequestParam("id") Long id, HttpServletRequest req,
            Model model) {
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        userProfileBo.checkEditAuthorization(assessment);
        if (assessment.getAssessmentFillings() != null && !assessment.
                getAssessmentFillings().isEmpty()) {
            FlashHelper.setRedirectError(req,
                    "Flash.CantDeleteAssessmentBecauseFillings");
        } else {
            assessmentDao.delete(assessment);
            FlashHelper.setRedirectNotice(req, "Flash.AssessmentDeleted");
        }
        return "redirect:list.html";
    }

    @RequestMapping
    public void questions(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        userProfileBo.checkEditAuthorization(assessment);
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessment);
        final QuestionBean questionBean = new QuestionBean();
        Course courseBean = new Course();
        courseBean.setId(assessment.getCourse().getId());
        questionBean.setCourse(courseBean);
        model.addAttribute(QUESTION_SEARCH_ATTR_NAME, questionBean);
        model.addAttribute(QUESTION_SEARCH_RESULT_ATTR_NAME, questionBo.search(
                questionBean));
        model.addAttribute(ASSESSMENT_QUESTIONS_ATTR_NAME, assessment.
                getQuestions());
    }

    @RequestMapping
    public String copy(@RequestParam("id") Long id, Model model, Locale locale) {
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        userProfileBo.checkEditAuthorization(assessment);
        Assessment newAssessment = assessmentBo.copyAssessment(assessment, locale);
        model.addAttribute(ASSESSMENT_ATTR_NAME, newAssessment);
        return EDIT_VIEW;
    }

}
