package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Course;
import java.util.List;
import javax.annotation.Resource;
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
 * The controller managing the courses.
 *
 * @author lucio
 */
@Controller
@RequestMapping("/course/*.html")
@SessionAttributes(CourseController.COURSE_ATTR_NAME)
public class CourseController {

    public static final String COURSE_ATTR_NAME = "course";
    public static final String EDIT_VIEW = "course/edit";
    public static final String LIST_VIEW = "course/list";
    @Resource
    private CourseDao courseDao;
    @Resource
    private UserProfileBo userProfileBo;

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
        List<Course> courses = courseDao.findByTeacherUsername(userProfileBo.getAuthenticatedUsername());
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
    public String delete(@RequestParam("id") Long id, Model model) {
        Course course = courseDao.get(id);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        courseDao.delete(course);
        return "redirect:list.html";
    }
}
