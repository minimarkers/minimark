package com.benfante.minimark.controllers;

import com.benfante.minimark.beans.ImportQuestionsBean;
import com.benfante.minimark.blo.ImporterBo;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.Question;
import java.io.StringReader;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute(COURSE_ATTR_NAME, course);
        return EDIT_VIEW;
    }

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(@ModelAttribute(COURSE_ATTR_NAME) Course course,
            BindingResult result, SessionStatus status) {
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
        model.addAttribute(COURSE_ATTR_NAME, course);
        model.addAttribute(IMPORT_QUESTIONS_ATTR_NAME, new ImportQuestionsBean());
    }

    @RequestMapping
    public String doImportQuestions(
            @ModelAttribute(COURSE_ATTR_NAME) Course course, @ModelAttribute(
            IMPORT_QUESTIONS_ATTR_NAME) ImportQuestionsBean importQuestionsBean,
            BindingResult result, HttpServletRequest request,
            SessionStatus status, Locale locale) {
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
