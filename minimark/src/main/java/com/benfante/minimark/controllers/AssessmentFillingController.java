package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.AssessmentFillingBo;
import com.benfante.minimark.blo.AssessmentPdfBuilder;
import com.benfante.minimark.blo.ResultCalculationBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.util.Utilities;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(
            AssessmentFillingController.class);
    public static final String ASSESSMENT_ATTR_NAME = "assessmentFilling";
    public static final String STUDENT_ID_ATTR_NAME = "studentId";
    public static final String FORM_VIEW = "assessmentFilling/logon";
    public static final String FILL_VIEW = "assessmentFilling/fill";
    public static final String RESULT_VIEW = "assessmentFilling/result";
    public static final String EXIT_WITH_ERROR_PAGE = "redirect:/";
    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentFillingBo assessmentFillingBo;
    @Resource
    private ResultCalculationBo resultCalculationBo;
    @Resource
    private AssessmentPdfBuilder assessmentPdfBuilder;

    @RequestMapping
    public String logon(@RequestParam("id") Long id, HttpServletRequest req,
            HttpSession session, Model model) {
        AssessmentFilling assessmentFilling = (AssessmentFilling) session.
                getAttribute(ASSESSMENT_ATTR_NAME);
        if (assessmentFilling != null) {
            if (assessmentFilling.getLoggedIn() != null && assessmentFilling.
                    getLoggedIn()) {
                FlashHelper.setRedirectError(req, "AssessmentAlreadyStarted");
                return "redirect:fill.html";
            }
        }
        Assessment assessment = assessmentDao.get(id);
        if (assessment == null) {
            throw new RuntimeException("Assessment not found");
        }
        assessmentFilling = assessmentFillingBo.generateAssessmentFilling(
                assessment);
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return FORM_VIEW;
    }

    @RequestMapping
    @Validation(view = FORM_VIEW)
    public String start(
            @ModelAttribute(ASSESSMENT_ATTR_NAME) AssessmentFilling assessmentFilling,
            BindingResult result, SessionStatus status, HttpServletRequest req, HttpSession session,
            Model model) {
        session.setAttribute(STUDENT_ID_ATTR_NAME, assessmentFilling.getIdentifier());
        AssessmentFilling prevFilling =
                assessmentFillingDao.findByAssessmentIdAndIdentifier(assessmentFilling.getAssessment().
                getId(), assessmentFilling.getIdentifier());
        if (prevFilling != null) { // this student already started this assessment
            model.addAttribute(ASSESSMENT_ATTR_NAME, prevFilling);
            FlashHelper.setRedirectError(req, "AssessmentAlreadyStarted");
            if (prevFilling.getSubmittedDate() != null) { // already submitted, return to the resul
                status.setComplete();
                return "redirect:showResult.html?id=" + prevFilling.getId();
            } else { // return to the filling page
                return "redirect:fill.html";
            }
        } else {
            assessmentFilling.setLoggedIn(Boolean.TRUE);
            assessmentFilling.setStartDate(new Date());
            assessmentFillingDao.store(assessmentFilling);
            return "redirect:fill.html";
        }
    }

    @RequestMapping
    public String fill(HttpSession session, Model model) {
        AssessmentFilling assessmentFilling = (AssessmentFilling) session.
                getAttribute(ASSESSMENT_ATTR_NAME);
        assessmentFilling = assessmentFillingDao.get(assessmentFilling.getId()); // refresh from DB
        model.addAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return FILL_VIEW;
    }

    @RequestMapping
    public String store(HttpSession session, Model model, SessionStatus status) {
        AssessmentFilling assessmentFilling = (AssessmentFilling) session.
                getAttribute(ASSESSMENT_ATTR_NAME);
        assessmentFilling = assessmentFillingDao.get(assessmentFilling.getId()); // refresh from DB
        assessmentFilling.setSubmittedDate(new Date());
        resultCalculationBo.calculate(assessmentFilling);
        status.setComplete();
        return "redirect:showResult.html?id=" + assessmentFilling.getId();
    }

    @RequestMapping
    public String showResult(@RequestParam("id") Long id, HttpServletRequest req, HttpSession session) {
        AssessmentFilling assessmentFilling = assessmentFillingDao.get(id);
        boolean validStudent = checkStudent(session, assessmentFilling);
        if (!validStudent) {
            FlashHelper.setRedirectError(req, "Flash.NotAllowedToSeeAssessment");
            return EXIT_WITH_ERROR_PAGE;
        }
        req.setAttribute(ASSESSMENT_ATTR_NAME, assessmentFilling);
        return RESULT_VIEW;
    }

    @RequestMapping
    public String pdf(@RequestParam("id") Long id, HttpServletRequest req,
            HttpServletResponse res, HttpSession session, Locale locale) {
        AssessmentFilling assessmentInfo = assessmentFillingDao.get(id);
        boolean validStudent = checkStudent(session, assessmentInfo);
        if (!validStudent) {
            FlashHelper.setRedirectError(req, "Flash.NotAllowedToSeeAssessment");
            return EXIT_WITH_ERROR_PAGE;
        }
        OutputStream out = null;
        try {
            byte[] pdfBytes =
                    assessmentPdfBuilder.buildPdf(assessmentInfo,
                    Utilities.getBaseUrl(req), locale);
            out = res.getOutputStream();
            res.setContentType("application/pdf");
            res.setContentLength(pdfBytes.length);
            res.setHeader("Content-Disposition",
                    " attachment; filename=\"" + assessmentInfo.getLastName() + "_" + assessmentInfo.
                    getFirstName() + ".pdf\"");
            res.setHeader("Expires", "0");
            res.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            res.setHeader("Pragma", "public");
            out.write(pdfBytes);
            out.flush();
        } catch (Exception ex) {
            logger.error("Can't build PDF file for " +
                    assessmentInfo.getIdentifier() + " " +
                    assessmentInfo.getFirstName() + " " + assessmentInfo.
                    getLastName(), ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                }
            }
        }
        return null;
    }

    private boolean checkStudent(HttpSession session,
            AssessmentFilling assessmentFilling) {
        boolean result = false;
        String studentId = (String) session.getAttribute(STUDENT_ID_ATTR_NAME);
        if (studentId != null && studentId.equals(assessmentFilling.getIdentifier())) {
            result = true;
        }
        return result;
    }

}
