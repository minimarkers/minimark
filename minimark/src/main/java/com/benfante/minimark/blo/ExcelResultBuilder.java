package com.benfante.minimark.blo;

import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.QuestionFilling;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import jxl.CellReferenceHelper;
import jxl.write.WriteException;
import org.springframework.stereotype.Service;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import org.springframework.context.MessageSource;

/**
 * A builder for results in Excel.
 *
 * @author Lucio Benfante
 */
@Service
public class ExcelResultBuilder {
    private static String DEFAULT_NUMBER_FORMAT ="0.00";

    @Resource
    private MessageSource messageSource;

    /**
     * Creates a new instance of XMLResultBuilder
     */
    public ExcelResultBuilder() {
    }

    /**
     * Build the Excel result document for a list of filled assessments.
     *
     * @param assessments The assessments
     * @param locale The locale for producing the document. Id null, the default locale will be used.
     * @return The Excel document.
     */
    public byte[] buildXls(List<AssessmentFilling> assessments, Locale locale)
            throws IOException, WriteException {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        WritableCellFormat TEXT_CELL_FORMAT;
        ByteArrayOutputStream xlsStream = new ByteArrayOutputStream();
        WorkbookSettings wbs = new WorkbookSettings();
        wbs.setEncoding("UTF-8");
        WritableWorkbook workbook = Workbook.createWorkbook(xlsStream, wbs);
        WritableSheet sheet =
                workbook.createSheet(messageSource.getMessage("Results", null,
                "?Results?", locale), 0);
        int rowNumber = 0;
        if (assessments.size() > 0) {
            addHeader(sheet, rowNumber++, assessments.get(0), locale);
        }
        for (AssessmentFilling assessment : assessments) {
            addRow(sheet, rowNumber++, assessment);
        }
        workbook.write();
        workbook.close();
        return xlsStream.toByteArray();
    }


    private WritableCellFormat buildHeaderCellFormat() {
        WritableFont arial10bold = new WritableFont(WritableFont.ARIAL, 10,
                WritableFont.BOLD);
        WritableCellFormat headerCellFormat = new WritableCellFormat(arial10bold);
        try {
            headerCellFormat.setAlignment(Alignment.CENTRE);
            headerCellFormat.setVerticalAlignment(VerticalAlignment.TOP);
            headerCellFormat.setShrinkToFit(true);
            headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            headerCellFormat.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);
        } catch (WriteException e) {
        }
        return headerCellFormat;
    }

    private  WritableCellFormat buildDoublevalCellFormat() {
        WritableFont arial10 = new WritableFont(WritableFont.ARIAL, 10,
                WritableFont.NO_BOLD);
        NumberFormat dec2nf = new NumberFormat(DEFAULT_NUMBER_FORMAT);
        WritableCellFormat doublevalCellFormat =
                new WritableCellFormat(arial10, dec2nf);
        try {
            doublevalCellFormat.setVerticalAlignment(VerticalAlignment.TOP);
            doublevalCellFormat.setShrinkToFit(true);
            doublevalCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        } catch (WriteException e) {
        }
        return doublevalCellFormat;
    }

    private WritableCellFormat buildDataCellFormat() {
        WritableFont arial10 = new WritableFont(WritableFont.ARIAL, 10,
                WritableFont.NO_BOLD);
        WritableCellFormat DATA_CELL_FORMAT = new WritableCellFormat(arial10);
        try {
            DATA_CELL_FORMAT.setVerticalAlignment(VerticalAlignment.TOP);
            DATA_CELL_FORMAT.setShrinkToFit(true);
            DATA_CELL_FORMAT.setBorder(Border.ALL, BorderLineStyle.THIN);
        } catch (WriteException e) {
        }
        return DATA_CELL_FORMAT;
    }

    private WritableCellFormat buildTextCellFormat() {
        WritableFont arial10 = new WritableFont(WritableFont.ARIAL, 10,
                WritableFont.NO_BOLD);
        WritableCellFormat textCellFormat = new WritableCellFormat(arial10);
        try {
            textCellFormat.setVerticalAlignment(VerticalAlignment.TOP);
            textCellFormat.setWrap(true);
            textCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        } catch (WriteException e) {
        }
        return textCellFormat;
    }
    /**
     * Add the sheet header
     */
    private void addHeader(WritableSheet sheet, int rowNumber,
            AssessmentFilling assessment,
            Locale locale) throws IOException,
            WriteException {
        WritableCellFormat headerCellFormat = buildHeaderCellFormat();
        Label labelCell = null;
        int columnNumber = 0;
        sheet.addCell(new Label(columnNumber, rowNumber,
                messageSource.getMessage("StudentIdentifier", null,
                "?StudentIdentifier?", locale), headerCellFormat));
        columnNumber++;
        sheet.addCell(new Label(columnNumber, rowNumber,
                messageSource.getMessage("FirstName", null, "?FirstName?",
                locale),
                headerCellFormat));
        columnNumber++;
        sheet.addCell(new Label(columnNumber, rowNumber,
                messageSource.getMessage("LastName", null, "?LastName?", locale),
                headerCellFormat));
        columnNumber++;
        int questionNumber = 0;
        for (QuestionFilling question : assessment.getQuestions()) {
            if (question instanceof ClosedQuestionFilling) {
                labelCell = new Label(columnNumber, rowNumber,
                        messageSource.getMessage("Question", null, "?Question?",
                        locale) + (questionNumber + 1), headerCellFormat);
                sheet.addCell(labelCell);
                columnNumber++;
            } else if (question instanceof OpenQuestionFilling) {
                labelCell = new Label(columnNumber, rowNumber,
                        messageSource.getMessage("Question", null, "?Question?",
                        locale) + (questionNumber + 1), headerCellFormat);
                sheet.addCell(labelCell);
                sheet.setColumnView(columnNumber, 60);
                sheet.mergeCells(columnNumber, rowNumber,
                        columnNumber + 1, rowNumber);
                columnNumber += 2;
            }
            questionNumber++;
        }
        labelCell = new Label(columnNumber, rowNumber,
                messageSource.getMessage("Sum", null, "?Sum?", locale),
                headerCellFormat);
        sheet.addCell(labelCell);
        columnNumber++;
    }

    /**
     * Add a row getting the data from a csv record.
     */
    private void addRow(WritableSheet sheet, int rowNumber,
            AssessmentFilling assessment) throws IOException, WriteException {
        WritableCellFormat doublevalCellFormat = buildDoublevalCellFormat();
        WritableCellFormat dataCellFormat = buildDataCellFormat();
        WritableCellFormat textCellFormat = buildTextCellFormat();
        Number numberCell = null;
        Label labelCell = null;
        int columnNumber = 0;
        sheet.addCell(new Label(columnNumber, rowNumber,
                assessment.getIdentifier(), dataCellFormat));
        columnNumber++;
        sheet.addCell(new Label(columnNumber, rowNumber,
                assessment.getFirstName(), dataCellFormat));
        columnNumber++;
        sheet.addCell(new Label(columnNumber, rowNumber,
                assessment.getLastName(), dataCellFormat));
        columnNumber++;
        for (QuestionFilling question : assessment.getQuestions()) {
            double mark = question.getMark() != null ? question.getMark().
                    doubleValue() : 0.0;
            if (question instanceof ClosedQuestionFilling) {
                numberCell = new Number(columnNumber, rowNumber, mark,
                        doublevalCellFormat);
                sheet.addCell(numberCell);
                columnNumber++;
            } else if (question instanceof OpenQuestionFilling) {
                labelCell = new Label(columnNumber, rowNumber,
                        ((OpenQuestionFilling) question).getAnswer(),
                        textCellFormat);
                sheet.addCell(labelCell);
                columnNumber++;
                numberCell = new Number(columnNumber, rowNumber, mark,
                        doublevalCellFormat);
                sheet.addCell(numberCell);
                columnNumber++;
            }
        }
        sheet.addCell(new Formula(columnNumber, rowNumber,
                "SUM(" + CellReferenceHelper.getCellReference(3,
                rowNumber) + ":" + CellReferenceHelper.getCellReference(
                columnNumber - 1, rowNumber) + ")",
                doublevalCellFormat));
        columnNumber++;
    }
}
