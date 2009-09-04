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
package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;

/**
 * Tests for the assessment pdf builder.
 *
 * @author Lucio Benfante
 */
public class AssessmentPdfBuilderTest extends MinimarkBaseTest {

    private static final Logger logger = Logger.getLogger(
            AssessmentPdfBuilderTest.class);
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentPdfBuilder assessmentPdfBuilder;

    /**
     * Test of buildPdf method, of class AssessmentPdfBuilder.
     */
    public void testBuildPdf() throws Exception {
        List<AssessmentFilling> assessments = assessmentFillingDao.findAll();
        assertNotEmpty(assessments);
        final AssessmentFilling assessment = assessments.get(0);
        assertNotNull(assessment.getStartDate());
        assertSize(4, assessment.getQuestions());
        byte[] pdfBytes =
                assessmentPdfBuilder.buildPdf(assessment, getBaseUrl(), null);
        assertTrue("Empty PDF", pdfBytes.length > 0);
        writeTestFile("testBuildPdf", ".pdf", pdfBytes);
    }

    /**
     * Test of buildPdf method, of class AssessmentPdfBuilder.
     */
    public void testBuildConcatenatedPdf() throws Exception {
        List<AssessmentFilling> assessments = assessmentFillingDao.findAll();
        assertNotEmpty(assessments);
        byte[] pdfBytes =
                assessmentPdfBuilder.buildPdf(assessments, getBaseUrl(), null);
        assertTrue("Empty PDF", pdfBytes.length > 0);
        writeTestFile("testBuildConcatenatedPdf", ".pdf", pdfBytes);
    }

    private String getBaseUrl() {
        return "file://" + (new File("").getAbsolutePath()) + "/src/main/webapp/";
    }

    private void writeTestFile(String prefix, String suffix, byte[] bytes) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        OutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
            out.write(bytes);
            out.flush();
            logger.info("Wrote test file " + tempFile.getAbsolutePath());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException iOException) {
                }
            }
        }
    }

}
