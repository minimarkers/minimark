package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Assessment;
import java.util.Date;
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
 * The controller managing the assessments.
 *
 * @author lucio
 */
@Controller
@RequestMapping("/assessment/*.html")
@SessionAttributes(AssessmentController.ASSESSMENT_ATTR_NAME)
public class AssessmentController {

    public static final String ASSESSMENT_ATTR_NAME = "assessment";
    public static final String EDIT_VIEW = "assessment/edit";
    public static final String LIST_VIEW = "assessment/list";
    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private UserProfileBo userProfileBo;

    @RequestMapping
    public String edit(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        assessment.setNewPassword(assessment.getPassword());
        assessment.setConfirmPassword(assessment.getPassword());
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessment);
        return EDIT_VIEW;
    }

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(@ModelAttribute(ASSESSMENT_ATTR_NAME) Assessment assessment,
            BindingResult result, SessionStatus status) {
        assessment.setPassword(assessment.getNewPassword());
        assessmentDao.store(assessment);
        status.setComplete();
        return "redirect:list.html";
    }

    @RequestMapping
    public String list(Model model) {
        List<Assessment> assessments = assessmentDao.findByTeacherUsername(userProfileBo.getAuthenticatedUsername());
        model.addAttribute("assessments", assessments);
        return LIST_VIEW;
    }

    @RequestMapping
    public String create(@RequestParam("courseId") Long courseId, Model model) {
        Assessment assessment = new Assessment();
        assessment.setCourse(courseDao.get(courseId));
        assessment.setAssessmentDate(new Date());
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessment);
        return EDIT_VIEW;
    }

    @RequestMapping
    public String delete(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        assessmentDao.delete(assessment);
        return "redirect:list.html";
    }
}
