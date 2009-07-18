package com.benfante.minimark.blo;

import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.FixedAnswerFilling;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.QuestionFilling;
import com.benfante.minimark.util.HTMLFOConverter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lucio Benfante (<A HREF="mailto:benfante@dei.unipd.it">benfante@dei.unipd.it</A>)
 * @version $Id: AssessmentXMLFOBuilder.java,v f2980690000e 2009/07/18 08:16:13 lucio $
 */
@Component
public class AssessmentXMLFOBuilder {
    @Resource
    private MessageSource messageSource;

    /** Creates a new instance of AssessmentXMLFOBuilder */
    private AssessmentXMLFOBuilder() {
    }

    public String makeXMLFO(AssessmentFilling assessment, Locale locale) {
        StringBuilder result = new StringBuilder();
        makeHeader(result, assessment, locale);
        makeLeader(result);
        startQuestionSection(result, assessment);
        makePersonalData(result, assessment, locale);
        makeQuestions(result, assessment.getQuestions(), locale);
        endQuestionSection(result, assessment);
        makeFooter(result, assessment);
        return result.toString();
    }

    private StringBuilder makeHeader(StringBuilder result,
            AssessmentFilling assessment, Locale locale) {
        FastDateFormat dateTimeFormat =
                FastDateFormat.getDateTimeInstance(FastDateFormat.SHORT,
                FastDateFormat.SHORT, locale);
        result.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>").append(
                '\n');
        result.append("<!DOCTYPE root [").append('\n');
        result.append("  <!ELEMENT root (#PCDATA) >").append('\n');
        result.append(
                "    <!ENTITY rchk '<fo:external-graphic src=\"url(&apos;images/interface/rchk.gif&apos;)\" content-height=\"9pt\" content-width=\"9pt\"/>' >").
                append('\n');
        result.append(
                "    <!ENTITY ruchk '<fo:external-graphic src=\"url(&apos;images/interface/ruchk.gif&apos;)\" content-height=\"9pt\" content-width=\"9pt\"/>' >").
                append('\n');
        result.append(
                "    <!ENTITY cchk '<fo:external-graphic src=\"url(&apos;images/interface/cchk.jpg&apos;)\" content-height=\"9pt\" content-width=\"9pt\"/>' >").
                append('\n');
        result.append(
                "    <!ENTITY cuchk '<fo:external-graphic src=\"url(&apos;images/interface/cuchk.jpg&apos;)\" content-height=\"9pt\" content-width=\"9pt\"/>' >").
                append('\n');
        result.append("    <!ENTITY agrave '&#xe0;' >").append('\n');
        result.append("    <!ENTITY egrave '&#xe8;' >").append('\n');
        result.append("    <!ENTITY igrave '&#xec;' >").append('\n');
        result.append("    <!ENTITY ograve '&#xf2;' >").append('\n');
        result.append("    <!ENTITY ugrave '&#xf9;' >").append('\n');
        result.append("    <!ENTITY aacute '&#xe1;' >").append('\n');
        result.append("    <!ENTITY eacute '&#xe9;' >").append('\n');
        result.append("    <!ENTITY iacute '&#xed;' >").append('\n');
        result.append("    <!ENTITY oacute '&#xf3;' >").append('\n');
        result.append("    <!ENTITY uacute '&#xfa;' >").append('\n');
        result.append("]>").append('\n');
        result.append("<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">").
                append('\n');
        result.append("  <fo:layout-master-set>").append('\n');
        result.append(
                "    <fo:simple-page-master master-name=\"all\" page-height=\"210mm\" page-width=\"297mm\" margin-top=\"1cm\" margin-bottom=\"0.5cm\" margin-left=\"1.5cm\" margin-right=\"1.5cm\">").
                append('\n');
        result.append(
                "      <fo:region-body margin-top=\"0cm\" margin-bottom=\"1.5cm\" column-count=\"2\" column-gap=\"0.25in\"/>").
                append('\n');
        result.append("      <fo:region-before extent=\"0cm\"/>").append('\n');
        result.append("      <fo:region-after extent=\"1cm\"/>").append('\n');
        result.append("    </fo:simple-page-master>").append('\n');
        result.append("  </fo:layout-master-set>").append('\n');
        result.append(
                "  <fo:page-sequence master-reference=\"all\" font-family=\"sans-serif\" font-size=\"9pt\" line-height=\"11pt\" space-after.optimum=\"12pt\">").
                append('\n');
        result.append("    <fo:static-content flow-name=\"xsl-region-after\">").
                append('\n');
        result.append("      <fo:block>").append('\n');
        result.append(
                "        <fo:table table-layout=\"fixed\" width=\"264mm\">").
                append('\n');
        result.append("          <fo:table-column column-width=\"88mm\"/>").
                append('\n');
        result.append("          <fo:table-column column-width=\"88mm\"/>").
                append('\n');
        result.append("          <fo:table-column column-width=\"88mm\"/>").
                append('\n');
        result.append("          <fo:table-body>").append('\n');
        result.append("            <fo:table-row>").append('\n');
        result.append("              <fo:table-cell text-align=\"start\">").
                append('\n');
        result.append("                <fo:block>").append('\n');
        result.append("                  <![CDATA[").append(assessment.
                getIdentifier()).append(' ').append(assessment.getFirstName()).
                append(' ').append(assessment.getLastName()).append("]]>").
                append('\n');
        result.append("                </fo:block>").append('\n');
        result.append("              </fo:table-cell>").append('\n');
        result.append("              <fo:table-cell text-align=\"center\">").
                append('\n');
        result.append("                <fo:block>").append('\n');
        result.append("                  ").append(assessment.getSubmittedDate()!=null?dateTimeFormat.format(assessment.getSubmittedDate()):messageSource.getMessage("NotSubmitted", null, "?Not submitted?", locale)).
                append('\n');
        result.append("                </fo:block>").append('\n');
        result.append("              </fo:table-cell>").append('\n');
        result.append("              <fo:table-cell text-align=\"end\">").append(
                '\n');
        result.append("                <fo:block>").append('\n');
        result.append(messageSource.getMessage("PageNOfM", new String[] {"<fo:page-number/>", "<fo:page-number-citation ref-id='last-page'/>"}, "?PageNOfM?", locale)).
                append('\n');
        result.append("                </fo:block>").append('\n');
        result.append("              </fo:table-cell>").append('\n');
        result.append("            </fo:table-row>").append('\n');
        result.append("          </fo:table-body>").append('\n');
        result.append("        </fo:table>").append('\n');
        result.append("      </fo:block>").append('\n');
        result.append("    </fo:static-content>").append('\n');
        result.append("    <fo:flow flow-name=\"xsl-region-body\">").append('\n');
        result.append(
                "      <fo:block font-size=\"11pt\" font-weight=\"bold\" text-align=\"center\">").
                append('\n');
        result.append("        <fo:block space-after=\"11pt\">").append(assessment.
                getAssessment().getTitle()).append("</fo:block>").append('\n');
        result.append("        <fo:block>").
                append(messageSource.getMessage("Incumbent", null, "?Incumbent?", locale)).append(": ").append(assessment.
                getAssessment().getCourse().getIncumbent()).append("</fo:block>").
                append('\n');
        result.append("        <fo:block>").append(dateTimeFormat.format(assessment.getStartDate())).
                append("</fo:block>").append('\n');
        result.append("      </fo:block>").append('\n');
        return result;
    }

    private StringBuilder makeFooter(StringBuilder result,
            AssessmentFilling assessment) {
        result.append("      <fo:block id=\"last-page\"/>").append('\n');
        result.append("    </fo:flow>").append('\n');
        result.append("  </fo:page-sequence>").append('\n');
        result.append("</fo:root>").append('\n');
        return result;
    }

    private StringBuilder makeLeader(StringBuilder result) {
        result.append("<fo:block text-align=\"center\">").append('\n');
        result.append(
                "  <fo:leader leader-pattern=\"rule\" leader-length=\"100%\" alignment-baseline=\"middle\"/>").
                append('\n');
        result.append("</fo:block>").append('\n');
        return result;
    }

    private StringBuilder startQuestionSection(StringBuilder result,
            AssessmentFilling assessment) {
        result.append("<fo:block>").append('\n');
        result.append("  <fo:table table-layout=\"fixed\" width=\"100%\">").
                append('\n');
        result.append("    <fo:table-column column-width=\"2.5cm\"/>").append(
                '\n');
        result.append(
                "    <fo:table-column column-width=\"proportional-column-width(1)\"/>").
                append('\n');
        result.append("    <fo:table-body>").append('\n');
        return result;
    }

    private StringBuilder endQuestionSection(StringBuilder result,
            AssessmentFilling assessment) {
        result.append("    </fo:table-body>").append('\n');
        result.append("  </fo:table>").append('\n');
        result.append("</fo:block>").append('\n');
        return result;
    }

    private StringBuilder makePersonalData(StringBuilder result,
            AssessmentFilling assessment, Locale locale) {
        result.append("<fo:table-row>").append('\n');
        result.append("  <fo:table-cell>").append('\n');
        result.append("    <fo:block font-weight=\"bold\">").
                append(messageSource.getMessage("PersonalData", null, "?PersonalData?", locale)).append("</fo:block>").
                append('\n');
        result.append("  </fo:table-cell>").append('\n');
        result.append("  <fo:table-cell>").append('\n');
        result.append("    <fo:table table-layout=\"fixed\" width=\"100%\">").
                append('\n');
        result.append(
                "      <fo:table-column column-width=\"proportional-column-width(1)\"/>").
                append('\n');
        result.append(
                "      <fo:table-column column-width=\"proportional-column-width(1)\"/>").
                append('\n');
        result.append("      <fo:table-body>").append('\n');
        result.append("        <fo:table-row>").append('\n');
        result.append("          <fo:table-cell text-align=\"end\">").append(
                '\n');
        result.append("            <fo:block>").append('\n');
        result.append(messageSource.getMessage("StudentIdentifier", null, "?StudentIdentifier?", locale)).append(": ").append('\n');
        result.append("            </fo:block>").append('\n');
        result.append("          </fo:table-cell>").append('\n');
        result.append("          <fo:table-cell font-weight=\"bold\">").append(
                '\n');
        result.append("            <fo:block>").append('\n');
        result.append("              <![CDATA[").append(
                assessment.getIdentifier()).append("]]>").append('\n');
        result.append("            </fo:block>").append('\n');
        result.append("          </fo:table-cell>").append('\n');
        result.append("        </fo:table-row>").append('\n');
        result.append("        <fo:table-row>").append('\n');
        result.append("          <fo:table-cell text-align=\"end\">").append(
                '\n');
        result.append("            <fo:block>").append('\n');
        result.append(messageSource.getMessage("FirstName", null, "?FirstName?", locale)).append(": ").append('\n');
        result.append("            </fo:block>").append('\n');
        result.append("          </fo:table-cell>").append('\n');
        result.append("          <fo:table-cell font-weight=\"bold\">").append(
                '\n');
        result.append("            <fo:block>").append('\n');
        result.append("              <![CDATA[").append(
                assessment.getFirstName()).append("]]>").append('\n');
        result.append("            </fo:block>").append('\n');
        result.append("          </fo:table-cell>").append('\n');
        result.append("        </fo:table-row>").append('\n');
        result.append("        <fo:table-row>").append('\n');
        result.append("          <fo:table-cell text-align=\"end\">").append(
                '\n');
        result.append("            <fo:block>").append('\n');
        result.append(messageSource.getMessage("LastName", null, "?LastName?", locale)).append(": ").append('\n');
        result.append("            </fo:block>").append('\n');
        result.append("          </fo:table-cell>").append('\n');
        result.append("          <fo:table-cell font-weight=\"bold\">").append(
                '\n');
        result.append("            <fo:block>").append('\n');
        result.append("              <![CDATA[").append(assessment.getLastName()).
                append("]]>").append('\n');
        result.append("            </fo:block>").append('\n');
        result.append("          </fo:table-cell>").append('\n');
        result.append("        </fo:table-row>").append('\n');
        result.append("      </fo:table-body>").append('\n');
        result.append("    </fo:table>").append('\n');
        result.append("  </fo:table-cell>").append('\n');
        result.append("</fo:table-row>").append('\n');
        result.append("<fo:table-row>").append('\n');
        result.append("  <fo:table-cell number-columns-spanned=\"2\">").append(
                '\n');
        makeLeader(result);
        result.append("  </fo:table-cell>").append('\n');
        result.append("</fo:table-row>").append('\n');
        return result;
    }

    private StringBuilder makeQuestions(StringBuilder result,
            List<QuestionFilling> questions, Locale locale) {
        Iterator<QuestionFilling> iquestions = questions.iterator();
        int i = 0;
        while (iquestions.hasNext()) {
            i++;
            QuestionFilling question = iquestions.next();
            if (question instanceof OpenQuestionFilling) {
                makeQuestion(result, (OpenQuestionFilling) question, i, locale);
            } else if (question instanceof ClosedQuestionFilling) {
                makeQuestion(result, (ClosedQuestionFilling) question, i, locale);
            }
            result.append("<fo:table-row>").append('\n');
            result.append("  <fo:table-cell number-columns-spanned=\"2\">").
                    append('\n');
            makeLeader(result);
            result.append("  </fo:table-cell>").append('\n');
            result.append("</fo:table-row>").append('\n');
        }
        return result;
    }

    private StringBuilder makeQuestion(StringBuilder result,
            OpenQuestionFilling question, int order, Locale locale) {
        final String answer = StringUtils.defaultString(question.getAnswer(),
                " ");
        if (OpenQuestion.VISUALIZATION_LONG.equals(question.getVisualization())) {
            result.append("<fo:table-row>").append('\n');
            result.append("  <fo:table-cell>").append('\n');
            result.append("    <fo:block font-weight=\"bold\">").append(messageSource.getMessage("Question", null, "?Question?", locale)).append(' ').append(
                    order).append("</fo:block>").append('\n');
            result.append("  </fo:table-cell>").append('\n');
            result.append("  <fo:table-cell>").append('\n');
            result.append("    <fo:block>").append('\n');
            result.append(new HTMLFOConverter().convert(question.
                    getFilteredContent())).append('\n');
            result.append("    </fo:block>").append('\n');
            result.append(
                    "    <fo:block border-color=\"black\" border-style=\"solid\" white-space-collapse=\"false\" linefeed-treatment=\"preserve\" white-space-treatment=\"preserve\"><![CDATA[").
                    append(answer).append("\n\n").append("]]></fo:block>").
                    append('\n');
            result.append("  </fo:table-cell>").append('\n');
            result.append("</fo:table-row>").append('\n');
        } else {
            result.append("<fo:table-row>").append('\n');
            result.append("  <fo:table-cell>").append('\n');
            result.append("    <fo:block font-weight=\"bold\">").append(messageSource.getMessage("Question", null, "?Question?", locale)).append(' ').append(
                    order).append("</fo:block>").append('\n');
            result.append("  </fo:table-cell>").append('\n');
            result.append("  <fo:table-cell>").append('\n');
            result.append("    <fo:block>").append('\n');
            result.append(new HTMLFOConverter().convert(question.
                    getFilteredContent())).append('\n');
            result.append("    </fo:block>").append('\n');
            result.append(
                    "    <fo:block border-color=\"black\" border-style=\"solid\" white-space-collapse=\"false\" linefeed-treatment=\"preserve\" white-space-treatment=\"preserve\"><![CDATA[").
                    append(answer).append("]]></fo:block>").append('\n');
            result.append("  </fo:table-cell>").append('\n');
            result.append("</fo:table-row>").append('\n');
        }
        return result;
    }

    private StringBuilder makeQuestion(StringBuilder result,
            ClosedQuestionFilling question, int order, Locale locale) {
        boolean multipleAnswer = question.isMultipleAnswer();
        HTMLFOConverter converter = new HTMLFOConverter();
        result.append("<fo:table-row>").append('\n');
        result.append("  <fo:table-cell>").append('\n');
        result.append("    <fo:block font-weight=\"bold\">").append(messageSource.getMessage("Question", null, "?Question?", locale)).append(' ').append(
                order).append("</fo:block>").append('\n');
        result.append("  </fo:table-cell>").append('\n');
        result.append("  <fo:table-cell>").append('\n');
        result.append("    <fo:block>").append('\n');
        result.append(converter.convert(question.getFilteredContent())).append(
                '\n');
        result.append("    </fo:block>").append('\n');
        List<FixedAnswerFilling> userAnswers = question.getFixedAnswers();
        Iterator<FixedAnswerFilling> ianswers = userAnswers.iterator();
        int i = 0;
        result.append(
                "    <fo:list-block  provisional-distance-between-starts='5mm' provisional-label-separation='5mm'>").
                append('\n');
        while (ianswers.hasNext()) {
            result.append("<fo:list-item>").append('\n');
            i++;
            FixedAnswerFilling sanswer = ianswers.next();
            result.append(
                    "<fo:list-item-label start-indent='5mm' end-indent='label-end()'>").
                    append('\n');
            result.append("<fo:block>");
            if (sanswer.getSelected() != null && sanswer.getSelected()) {
                if (multipleAnswer) {
                    result.append("  &cchk; ");
                } else {
                    result.append("  &rchk; ");
                }
            } else {
                if (multipleAnswer) {
                    result.append("  &cuchk; ");
                } else {
                    result.append("  &ruchk; ");
                }
            }
            result.append("</fo:block>").append('\n');
            result.append("</fo:list-item-label>").append('\n');
            result.append(
                    "<fo:list-item-body start-indent='10mm' end-indent='10mm'>").
                    append('\n');
            if (sanswer.getCorrect() != null && sanswer.getCorrect()) {
                result.append("<fo:block text-decoration='underline'>").append(
                        '\n');
            } else {
                if (sanswer.getSelected() != null && sanswer.getSelected()) {
                    result.append("<fo:block text-decoration='line-through'>").
                            append('\n');
                } else {
                    result.append("<fo:block>").append('\n');
                }
            }
            result.append(converter.convert(sanswer.getFilteredContent())).
                    append('\n');
            result.append("</fo:block>").append('\n');
            result.append("</fo:list-item-body>").append('\n');
            result.append("</fo:list-item>").append('\n');
        }
        result.append("    </fo:list-block>").append('\n');
        result.append("  </fo:table-cell>").append('\n');
        result.append("</fo:table-row>").append('\n');
        return result;
    }
}
