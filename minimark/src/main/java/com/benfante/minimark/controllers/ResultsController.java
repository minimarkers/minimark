package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.AssessmentPdfBuilder;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.util.Utilities;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fop.apps.FopFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A controller for managing results.
 *
 * @author Lucio Benfante
 */
@Controller
@RequestMapping("/results/*.html")
public class ResultsController {
    private static final Logger log = new ConsoleLogger(ConsoleLogger.LEVEL_WARN);
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ResultsController.class);

    private FopFactory fopFactory = FopFactory.newInstance();

    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentPdfBuilder assessmentPdfBuilder;

    @RequestMapping
    public void list(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        List<AssessmentFilling> fillings = assessmentFillingDao.findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(assessment.getId());
        model.addAttribute("assessment", assessment);
        model.addAttribute("fillings", fillings);
    }

    @RequestMapping
    public void pdf(@RequestParam("id") Long id, HttpServletRequest req, HttpServletResponse res, Locale locale) {
        AssessmentFilling assessmentInfo = assessmentFillingDao.get(id);
        OutputStream out = null;
        try {
            byte[] pdfBytes =
                    assessmentPdfBuilder.buildPdf(assessmentInfo,
                    Utilities.getBaseUrl(req), locale);
            out = res.getOutputStream();
            res.setContentType("application/pdf");
            res.setContentLength(pdfBytes.length);
            res.setHeader("Content-Disposition", " attachment; filename=\"" + assessmentInfo.getLastName() + "_" + assessmentInfo.getFirstName() + ".pdf\"");
            res.setHeader("Expires", "0");
            res.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            res.setHeader("Pragma", "public");
            out.write(pdfBytes);
            out.flush();
        } catch (Exception ex) {
            logger.error("Can't build PDF file for " +
                    assessmentInfo.getIdentifier() + " " +
                    assessmentInfo.getFirstName() + " " + assessmentInfo.getLastName(), ex);
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
    public void pdfs(@RequestParam("id") Long id, HttpServletRequest req, HttpServletResponse res, Locale locale) {
        Assessment assessment = assessmentDao.get(id);
        List<AssessmentFilling> assessments = assessmentFillingDao.findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(id);
        OutputStream out = null;
        try {
            byte[] pdfBytes =
                    assessmentPdfBuilder.buildPdf(assessments,
                    Utilities.getBaseUrl(req), locale);
            out = res.getOutputStream();
            res.setContentType("application/pdf");
            res.setContentLength(pdfBytes.length);
            res.setHeader("Content-Disposition", " attachment; filename=\""+assessment.getTitle()+".pdf\"");
            res.setHeader("Expires", "0");
            res.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            res.setHeader("Pragma", "public");
            out.write(pdfBytes);
            out.flush();
        } catch (Exception ex) {
            logger.error("Can't build PDF file for " +
                    assessment.getTitle(), ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                }
            }
        }
    }
}
