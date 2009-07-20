package com.benfante.minimark.blo;

import com.benfante.minimark.po.AssessmentFilling;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;

/**
 * Pdf builder for assessments.
 *
 * @author Lucio Benfante
 */
@Service
public class AssessmentPdfBuilder {

    private FopFactory fopFactory = FopFactory.newInstance();
    @Resource
    private AssessmentXMLFOBuilder assessmentXMLFOBuilder;

    /**
     * Build the PDF for an assessment.
     *
     * @param assessment The assessment
     * @param baseUrl The base URL for retrieving images and resource. If null it will not be set.
     * @param locale The locale for producing the document. Id null, the default locale will be used.
     * @return The PDF document.
     */
    public byte[] buildPdf(AssessmentFilling assessment, String baseUrl,
            Locale locale) throws Exception {
        String xmlfo = assessmentXMLFOBuilder.makeXMLFO(assessment,
                locale != null ? locale : Locale.getDefault());
        ByteArrayOutputStream pdfos = new ByteArrayOutputStream();
        Reader foreader = null;
        try {
            foreader = new StringReader(xmlfo);
            FOUserAgent foua = fopFactory.newFOUserAgent();
            if (baseUrl != null) {
                foua.setBaseURL(baseUrl);
            }
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foua, pdfos);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer
            Source src = new StreamSource(foreader);
            Result result = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, result);
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
        }
        return pdfos.toByteArray();
    }

    /**
     * Build the contatenated PDF for a list of assessments.
     *
     * @param assessments The assessments
     * @param baseUrl The base URL for retrieving images and resource. If null it will not be set.
     * @param locale The locale for producing the document. Id null, the default locale will be used.
     * @return The PDF document.
     */
    public byte[] buildPdf(List<AssessmentFilling> assessments, String baseUrl,
            Locale locale) throws Exception {
        ByteArrayOutputStream pdfos = new ByteArrayOutputStream();
        List master = new ArrayList();
        Document document = null;
        PdfCopy writer = null;
        PdfOutline rootOutline = null;
        try {
            int f = 0;
            int pageOffset = 0;
            for (AssessmentFilling assessment : assessments) {
                // we create a reader for a certain document
                PdfReader reader = new PdfReader(buildPdf(assessment,
                        baseUrl, locale));
                reader.consolidateNamedDestinations();
                // we retrieve the total number of pages
                int n = reader.getNumberOfPages();
                List bookmarks = SimpleBookmark.getBookmark(reader);
                if (bookmarks != null) {
                    if (pageOffset != 0) {
                        SimpleBookmark.shiftPageNumbers(bookmarks,
                                pageOffset, null);
                    }
                    master.addAll(bookmarks);
                }
                pageOffset += n;
                if (f == 0) {
                    // step 1: creation of a document-object
                    document = new Document(
                            reader.getPageSizeWithRotation(1));
                    // step 2: we create a writer that listens to the document
                    writer = new PdfCopy(document, pdfos);
                    writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
                    // step 3: we open the document
                    document.open();
                    // initialize rootOutline for assessment bookmarks creation
                    PdfContentByte cb = writer.getDirectContent();
                    rootOutline = cb.getRootOutline();
                }
                // step 4: we add content
                PdfImportedPage page;
                for (int i = 0; i < n;) {
                    ++i;
                    page = writer.getImportedPage(reader, i);
                    writer.addPage(page);
                }
                PRAcroForm form = reader.getAcroForm();
                if (form != null) {
                    writer.copyAcroForm(reader);
                }
                f++;
            }
        } finally {
            if (pdfos != null) {
                try {
                    pdfos.close();
                } catch (IOException ioe) {
                }
            }
            if (!master.isEmpty()) {
                writer.setOutlines(master);
            }
            // step 5: we close the document
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
        return pdfos.toByteArray();
    }
}
