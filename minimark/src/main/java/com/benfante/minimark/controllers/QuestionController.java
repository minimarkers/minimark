package com.benfante.minimark.controllers;

import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.blo.QuestionBo;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.FixedAnswer;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.Question;
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
    public static final String QUESTION_SEARCH_ATTR_NAME = "questionSearch";
    public static final String QUESTION_SEARCH_RESULT_ATTR_NAME =
            "questionSearchResult";
    public static final String EDIT_VIEW = "question/edit";
    public static final String LIST_VIEW = "question/list";
    @Resource
    private CourseDao courseDao;
    @Resource
    private QuestionDao questionDao;
    @Resource
    private QuestionBo questionBo;
    @Resource
    private UserProfileBo userProfileBo;

    @RequestMapping
    public String edit(@RequestParam("id") Long id, Model model) {
        Question question = questionDao.get(id);
        if (question == null) {
            throw new RuntimeException("Question not found");
        }
        userProfileBo.checkEditAuthorization(question.getCourse());
        QuestionBean questionBean = new QuestionBean();
        questionBean.setId(question.getId());
        questionBean.setTitle(question.getTitle());
        questionBean.setContent(question.getContent());
        questionBean.setContentFilter(question.getContentFilter());
        questionBean.setVisualization(question.getVisualization());
        questionBean.setWeight(question.getWeight());
        questionBean.setTags(question.getTagList());
        questionBean.setCourse(question.getCourse());
        if (question instanceof OpenQuestion) {
            OpenQuestion openQuestion = (OpenQuestion) question;
            questionBean.setType("open");
            questionBean.setAnswerMaxLength(openQuestion.getAnswerMaxLength());
        } else if (question instanceof ClosedQuestion) {
            ClosedQuestion closedQuestion = (ClosedQuestion) question;
            questionBean.setType("closed");
            questionBean.setFixedAnswers(closedQuestion.getFixedAnswers());
        }
        model.addAttribute(QUESTION_ATTR_NAME, questionBean);
        return EDIT_VIEW;
    }

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(
            @ModelAttribute(QUESTION_ATTR_NAME) QuestionBean questionBean,
            BindingResult result, SessionStatus status) {
        Question question = null;
        if (questionBean.getId() == null) {
            if ("open".equals(questionBean.getType())) {
                question = new OpenQuestion();
            } else if ("closed".equals(questionBean.getType())) {
                question = new ClosedQuestion();
            } else {
                throw new IllegalArgumentException("Unknown question type");
            }
            question.setCourse(questionBean.getCourse());
        } else {
            question = questionDao.get(questionBean.getId());
        }
        userProfileBo.checkEditAuthorization(question.getCourse());
        question.setTitle(questionBean.getTitle());
        question.setContent(questionBean.getContent());
        question.setContentFilter(questionBean.getContentFilter());
        question.setVisualization(questionBean.getVisualization());
        question.setWeight(questionBean.getWeight());
        question.setTagList(questionBean.getTags());
        if (question instanceof OpenQuestion) {
            OpenQuestion openQuestion = (OpenQuestion) question;
            openQuestion.setAnswerMaxLength(questionBean.getAnswerMaxLength());
        } else if (question instanceof ClosedQuestion) {
            ClosedQuestion closedQuestion = (ClosedQuestion) question;
            for (FixedAnswer fixedAnswer : questionBean.getFixedAnswers()) {
                fixedAnswer.setQuestion(closedQuestion);
            }
            closedQuestion.setFixedAnswers(questionBean.getFixedAnswers());
        }
        questionBo.save(question);
        status.setComplete();
        return "redirect:list.html?course.id=" + questionBean.getCourse().getId();
    }

    @RequestMapping
    public String list(@RequestParam("course.id") Long courseId, Model model) {
        Course course = courseDao.get(courseId);
        userProfileBo.checkEditAuthorization(course);
        final QuestionBean questionBean = new QuestionBean();
        Course courseBean = new Course();
        courseBean.setId(courseId);
        questionBean.setCourse(courseBean);
        model.addAttribute(QUESTION_SEARCH_ATTR_NAME, questionBean);
        model.addAttribute(QUESTION_SEARCH_RESULT_ATTR_NAME, questionBo.search(
                questionBean));
        model.addAttribute("course", course);
        return LIST_VIEW;
    }

    @RequestMapping
    public String search(
            @ModelAttribute(QUESTION_SEARCH_ATTR_NAME) QuestionBean questionBean,
            Model model) {
        Course course = courseDao.get(questionBean.getCourse().getId());
        userProfileBo.checkEditAuthorization(course);
        model.addAttribute(QUESTION_SEARCH_ATTR_NAME, questionBean);
        model.addAttribute(QUESTION_SEARCH_RESULT_ATTR_NAME, questionBo.search(
                questionBean));
        model.addAttribute("course", course);
        return LIST_VIEW;
    }

    @RequestMapping
    public String create(@RequestParam("courseId") Long courseId, Model model) {
        Course course = courseDao.get(courseId);
        userProfileBo.checkEditAuthorization(course);
        QuestionBean questionBean = new QuestionBean();
        questionBean.setCourse(course);
        model.addAttribute(QUESTION_ATTR_NAME, questionBean);
        return EDIT_VIEW;
    }

    @RequestMapping
    public String delete(@RequestParam("id") Long id, Model model) {
        Question question = questionDao.get(id);
        if (question == null) {
            throw new RuntimeException("Question not found");
        }
        userProfileBo.checkEditAuthorization(question.getCourse());
        Long courseId = question.getCourse().getId();
        questionDao.delete(question);
        return "redirect:list.html?course.id=" + courseId;
    }

}
