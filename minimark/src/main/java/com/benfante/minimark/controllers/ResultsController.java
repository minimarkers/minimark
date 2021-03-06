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

import com.benfante.minimark.blo.AssessmentPdfBuilder;
import com.benfante.minimark.blo.ExcelResultBuilder;
import com.benfante.minimark.blo.ResultCalculationBo;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.util.Utilities;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.parancoe.web.util.FlashHelper;
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

    private static final Logger logger = Logger.getLogger(
            ResultsController.class);
    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentPdfBuilder assessmentPdfBuilder;
    @Resource
    private ExcelResultBuilder excelResultBuilder;
    @Resource
    private ResultCalculationBo resultCalculationBo;
    @Resource
    private UserProfileBo userProfileBo;

    @RequestMapping
    public void list(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        userProfileBo.checkEditAuthorization(assessment);
        List<AssessmentFilling> fillings = assessmentFillingDao.
                findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(
                assessment.getId());
        model.addAttribute("assessment", assessment);
        model.addAttribute("fillings", fillings);
    }

    @RequestMapping
    public String evaluateAll(@RequestParam("id") Long id,
            HttpServletRequest req, Model model) {
        Assessment assessment = assessmentDao.get(id);
        userProfileBo.checkEditAuthorization(assessment);
        List<AssessmentFilling> fillings = assessmentFillingDao.
                findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(
                assessment.getId());
        for (AssessmentFilling assessmentFilling : fillings) {
            resultCalculationBo.calculate(assessmentFilling);
        }
        FlashHelper.setRedirectNotice(req, "Flash.AllAssessmentEvaluated");
        return "redirect:list.html?id=" + id;
    }

    @RequestMapping
    public String evaluate(@RequestParam("id") Long id, HttpServletRequest req,
            Model model) {
        AssessmentFilling assessmentFilling = assessmentFillingDao.get(id);
        userProfileBo.checkEditAuthorization(assessmentFilling.getAssessment());
        resultCalculationBo.calculate(assessmentFilling);
        FlashHelper.setRedirectNotice(req, "Flash.AssessmentEvaluated");
        return "redirect:list.html?id=" + assessmentFilling.getAssessment().getId();
    }

    @RequestMapping
    public String delete(@RequestParam("id") Long id, HttpServletRequest req,
            Model model) {
        AssessmentFilling assessmentFilling = assessmentFillingDao.get(id);
        userProfileBo.checkEditAuthorization(assessmentFilling.getAssessment());
        final Long assessmentId = assessmentFilling.getAssessment().getId();
        assessmentFillingDao.delete(assessmentFilling);
        FlashHelper.setRedirectNotice(req, "Flash.AssessmentDeleted");
        return "redirect:list.html?id=" + assessmentId;
    }

    @RequestMapping
    public void pdf(@RequestParam("id") Long id, HttpServletRequest req,
            HttpServletResponse res, Locale locale) {
        AssessmentFilling assessmentInfo = assessmentFillingDao.get(id);
        userProfileBo.checkEditAuthorization(assessmentInfo.getAssessment());
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
    }

    @RequestMapping
    public void pdfs(@RequestParam("id") Long id, HttpServletRequest req,
            HttpServletResponse res, Locale locale) {
        Assessment assessment = assessmentDao.get(id);
        userProfileBo.checkEditAuthorization(assessment);
        List<AssessmentFilling> assessments = assessmentFillingDao.
                findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(id);
        OutputStream out = null;
        try {
            byte[] pdfBytes =
                    assessmentPdfBuilder.buildPdf(assessments,
                    Utilities.getBaseUrl(req), locale);
            out = res.getOutputStream();
            res.setContentType("application/pdf");
            res.setContentLength(pdfBytes.length);
            res.setHeader("Content-Disposition",
                    " attachment; filename=\"" + assessment.getTitle() + ".pdf\"");
            res.setHeader("Expires", "0");
            res.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
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

    @RequestMapping
    public void xls(@RequestParam("id") Long id, HttpServletRequest req,
            HttpServletResponse res, Locale locale) {
        Assessment assessment = assessmentDao.get(id);
        userProfileBo.checkEditAuthorization(assessment);
        List<AssessmentFilling> assessments = assessmentFillingDao.
                findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(id);
        OutputStream out = null;
        try {
            byte[] xlsBytes =
                    excelResultBuilder.buildXls(assessments, locale);
            out = res.getOutputStream();
            res.setContentType("application/vnd.ms-excel");
            res.setContentLength(xlsBytes.length);
            res.setHeader("Content-Disposition",
                    " attachment; filename=\"" + assessment.getTitle() + ".xls\"");
            res.setHeader("Expires", "0");
            res.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            res.setHeader("Pragma", "public");
            out.write(xlsBytes);
            out.flush();
        } catch (Exception ex) {
            logger.error("Can't build XLS file for " +
                    assessment.getTitle(), ex);
            ex.printStackTrace();
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
