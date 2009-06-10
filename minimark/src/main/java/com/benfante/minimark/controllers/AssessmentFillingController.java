package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.ClosedQuestion;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
 * The controller managing the fillin of assessments.
 *
 * @author lucio
 */
@Controller
@RequestMapping("/assessmentFilling/*.html")
@SessionAttributes(AssessmentFillingController.ASSESSMENT_ATTR_NAME)
public class AssessmentFillingController {

    public static final String ASSESSMENT_ATTR_NAME = "assessmentFilling";
    public static final String FORM_VIEW = "assessmentFilling/logon";
    public static final String FILL_VIEW = "assessmentFilling/fill";
    public static final String RESULT_VIEW = "assessmentFilling/result";
    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private AssessmentFillingDao assessmentFillingDao;

    @RequestMapping
    public String logon(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        AssessmentFilling assessmentFilling = new AssessmentFilling();
        assessmentFilling.setAssessment(assessment);
        for (AssessmentQuestion assessmentQuestion : assessment.getQuestions()) { // for lazy initialization (also needed for copy)
            if (assessmentQuestion.getQuestion() instanceof ClosedQuestion) {
                ((ClosedQuestion)assessmentQuestion.getQuestion()).getFixedAnswers().size();
            }
        }
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return FORM_VIEW;
    }

    @RequestMapping
    @Validation(view = FORM_VIEW)
    public String start(@ModelAttribute(ASSESSMENT_ATTR_NAME) AssessmentFilling assessmentFilling,
            BindingResult result, SessionStatus status) {
        assessmentFilling.setLoggedIn(Boolean.TRUE);
        assessmentFillingDao.store(assessmentFilling);
        return "redirect:fill.html";
    }

    @RequestMapping
    public String fill(HttpSession session, Model model) {
        AssessmentFilling assessmentFilling = (AssessmentFilling)session.getAttribute(ASSESSMENT_ATTR_NAME);
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return FILL_VIEW;
    }

    @RequestMapping
    public String store(Model model) {
        AssessmentFilling assessmentFilling = (AssessmentFilling)model.asMap().get(ASSESSMENT_ATTR_NAME);
        return RESULT_VIEW;
    }

}
