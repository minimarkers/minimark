package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import javax.annotation.Resource;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.lang.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;

/**
 * Tests on the PDF generation.
 *
 * @author Lucio Benfante
 */
public class AssessmentXMLFOBuilderTest extends MinimarkBaseTest {
    private static final Logger logger = Logger.getLogger(AssessmentXMLFOBuilderTest.class);

    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentXMLFOBuilder assessmentXMLFOBuilder;
    
    /**
     * Test of makeXMLFO method, of class AssessmentXMLFOBuilder.
     */
    public void testMakeXMLFO() {
        List<AssessmentFilling> assessments = assessmentFillingDao.findAll();
        assertNotEmpty(assessments);
        final AssessmentFilling assessment = assessments.get(0);
        assertSize(4, assessment.getQuestions());
        String xmlfo = assessmentXMLFOBuilder.makeXMLFO(assessment);
        assertTrue("XMLFO is empty", StringUtils.isNotEmpty(xmlfo));
        writePdf(xmlfo);
    }

    private void writePdf(String xmlfo) {
        FopFactory fopFactory = FopFactory.newInstance();
        ByteArrayOutputStream pdfos = null;
        Reader foreader = null;
        OutputStream out = null;
        try {
            foreader = new StringReader(xmlfo);
            pdfos = new ByteArrayOutputStream();
            FOUserAgent foua = fopFactory.newFOUserAgent();
            final String baseDir = new File("").getAbsolutePath();
            logger.debug("basedir="+baseDir);
            foua.setBaseURL("file://"+baseDir+"/src/main/webapp/");
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foua, pdfos);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer
            Source src = new StreamSource(foreader);
            Result result = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, result);
            final File tempFile = File.createTempFile("minimarkTest", ".pdf");
            out = new FileOutputStream(tempFile);
            out.write(pdfos.toByteArray());
            out.flush();
            logger.info("Wrote PDF to file "+tempFile.getAbsolutePath());
        } catch (Exception ex) {
            logger.error("Can't build PDF file", ex);
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
