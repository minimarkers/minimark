package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.Question;
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
@RequestMapping("/question/*.html")
@SessionAttributes(QuestionController.QUESTION_ATTR_NAME)
public class QuestionController {

    public static final String QUESTION_ATTR_NAME = "question";
    public static final String EDIT_VIEW = "question/edit";
    public static final String LIST_VIEW = "question/list";
    @Resource
    private CourseDao courseDao;
    @Resource
    private QuestionDao questionDao;
    @Resource
    private UserProfileBo userProfileBo;

    @RequestMapping
    public String edit(@RequestParam("id") Long id, Model model) {
        Question question = questionDao.get(id);
        if (question == null) {
            throw new RuntimeException("Question not found");
        }
        model.addAttribute(QUESTION_ATTR_NAME, question);
        return EDIT_VIEW;
    }

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(@ModelAttribute(QUESTION_ATTR_NAME) Question question,
            BindingResult result, SessionStatus status) {
        questionDao.store(question);
        status.setComplete();
        Long courseId = question.getCourse().getId();
        return "redirect:list.html?courseId="+courseId;
    }

    @RequestMapping
    public String list(@RequestParam("courseId") Long courseId, Model model) {
        Course course = courseDao.get(courseId);
        List<Question> questions = questionDao.findByCourseId(courseId);
        model.addAttribute("course", course);
        model.addAttribute("questions", questions);
        return LIST_VIEW;
    }

    @RequestMapping
    public String create(@RequestParam("courseId") Long courseId, Model model) {
        Course course = courseDao.get(courseId);
        course.addTeacher(userProfileBo.getCurrentUser());
        model.addAttribute(QUESTION_ATTR_NAME, course);
        return EDIT_VIEW;
    }

    @RequestMapping
    public String delete(@RequestParam("id") Long id, Model model) {
        Question question = questionDao.get(id);
        if (question == null) {
            throw new RuntimeException("Question not found");
        }
        Long courseId = question.getCourse().getId();
        questionDao.delete(question);
        return "redirect:list.html?courseId="+courseId;
    }
}
