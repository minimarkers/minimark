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
 * Tests on the Excel document generation.
 *
 * @author Lucio Benfante
 */
public class ExcelResultBuilderTest extends MinimarkBaseTest {
    private static final Logger logger = Logger.getLogger(
            ExcelResultBuilderTest.class);
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private ExcelResultBuilder excelResultBuilder;

    /**
     * Test of buildXsl method, of class ExcelResultBuilder.
     */
    public void testBuildXls() throws Exception {
        List<AssessmentFilling> assessments = assessmentFillingDao.findAll();
        assertNotEmpty(assessments);
        byte[] xlsBytes =
                excelResultBuilder.buildXls(assessments, null);
        assertTrue("Empty XLS", xlsBytes.length > 0);
        writeTestFile("testBuildXls", ".xls", xlsBytes);
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
