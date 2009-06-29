package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.AssessmentFillingBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Resource
    private AssessmentFillingBo assessmentFillingBo;

    @RequestMapping
    public String logon(@RequestParam("id") Long id, HttpServletRequest req, HttpSession session, Model model) {
        AssessmentFilling assessmentFilling = (AssessmentFilling) session.getAttribute(ASSESSMENT_ATTR_NAME);
        if (assessmentFilling != null) {
            if (assessmentFilling.getLoggedIn() != null && assessmentFilling.getLoggedIn()) {
                FlashHelper.setRedirectError(req, "AssessmentAlreadyStarted");
                return "redirect:fill.html";
            } else {
                assessmentFillingDao.delete(assessmentFilling);
            }
        }
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        assessmentFilling = assessmentFillingBo.generateAndStoreAssessmentFilling(assessment);
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return FORM_VIEW;
    }

    @RequestMapping
    @Validation(view = FORM_VIEW)
    public String start(@ModelAttribute(ASSESSMENT_ATTR_NAME) AssessmentFilling assessmentFilling,
            BindingResult result, SessionStatus status) {
        assessmentFilling.setLoggedIn(Boolean.TRUE);
        assessmentFilling.setStartDate(new Date());
        assessmentFillingDao.store(assessmentFilling);
        return "redirect:fill.html";
    }

    @RequestMapping
    public String fill(HttpSession session, Model model) {
        AssessmentFilling assessmentFilling = (AssessmentFilling) session.getAttribute(ASSESSMENT_ATTR_NAME);
        assessmentFilling = assessmentFillingDao.get(assessmentFilling.getId()); // refresh from DB
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return FILL_VIEW;
    }

    @RequestMapping
    public String store(HttpSession session, Model model, SessionStatus status) {
        AssessmentFilling assessmentFilling = (AssessmentFilling) session.getAttribute(ASSESSMENT_ATTR_NAME);
        assessmentFilling = assessmentFillingDao.get(assessmentFilling.getId()); // refresh from DB
        assessmentFilling.setSubmittedDate(new Date());
        status.setComplete();
        return "redirect:showResult.html?id=" + assessmentFilling.getId();
    }

    @RequestMapping
    public String showResult(@RequestParam("id") Long id, HttpServletRequest req) {
        AssessmentFilling assessmentFilling = assessmentFillingDao.get(id);
        req.setAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return RESULT_VIEW;
    }
}
