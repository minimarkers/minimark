package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.AssessmentXMLFOBuilder;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.util.Utilities;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A controller for managinr results.
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
    @Resource AssessmentXMLFOBuilder assessmentXMLFOBuilder;

    @RequestMapping
    public void list(@RequestParam("id") Long id, Model model) {
        Assessment assessment = assessmentDao.get(id);
        List<AssessmentFilling> fillings = assessmentFillingDao.findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(assessment.getId());
        model.addAttribute("assessment", assessment);
        model.addAttribute("fillings", fillings);
    }

    @RequestMapping
    public void pdf(@RequestParam("id") Long id, HttpServletRequest req, HttpServletResponse res) {
        AssessmentFilling assessmentInfo = assessmentFillingDao.get(id);
        String xmlfo = assessmentXMLFOBuilder.makeXMLFO(assessmentInfo);
        ByteArrayOutputStream pdfos = null;
        Reader foreader = null;
        OutputStream out = null;
        try {
            foreader = new StringReader(xmlfo);
            pdfos = new ByteArrayOutputStream();
            FOUserAgent foua = fopFactory.newFOUserAgent();
            foua.setBaseURL(Utilities.getBaseUrl(req));
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foua, pdfos);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer
            Source src = new StreamSource(foreader);
            Result result = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, result);

//            Driver driver = new Driver(new InputSource(foreader), pdfos);
//            driver.setLogger(log);
//            driver.setRenderer(Driver.RENDER_PDF);
//            driver.run();
            out = res.getOutputStream();
            res.setContentType("application/pdf");
            res.setContentLength(pdfos.size());
            res.setHeader("Content-Disposition", "filename=" + assessmentInfo.getLastName() + "_" + assessmentInfo.getFirstName() + ".pdf");
            out.write(pdfos.toByteArray());
            out.flush();
        } catch (IOException ioe) {
            logger.error("Can't build PDF file for " +
                    assessmentInfo.getIdentifier() + " " +
                    assessmentInfo.getFirstName() + " " + assessmentInfo.getLastName(), ioe);
        } catch (FOPException fe) {
            logger.error("Can't build PDF file for " +
                    assessmentInfo.getIdentifier() + " " +
                    assessmentInfo.getFirstName() + " " + assessmentInfo.getLastName(), fe);
        } catch (TransformerConfigurationException tce) {
            logger.error("Can't build PDF file for " +
                    assessmentInfo.getIdentifier() + " " +
                    assessmentInfo.getFirstName() + " " + assessmentInfo.getLastName(), tce);
        } catch (TransformerException te) {
            logger.error("Can't build PDF file for " +
                    assessmentInfo.getIdentifier() + " " +
                    assessmentInfo.getFirstName() + " " + assessmentInfo.getLastName(), te);
        } finally {
            if (foreader != null) {
                try {
                    foreader.close();
                } catch (IOException ioe) {
                }
            }
            if (pdfos != null) {
                try {
                    pdfos.close();
                } catch (IOException ioe) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                }
            }
        }
    }
}
