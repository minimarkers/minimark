package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.dao.ClosedQuestionFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.FixedAnswerFilling;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests on result calculation.
 *
 * @author Lucio Benfante
 */
public class ResultCalculationBoTest extends MinimarkBaseTest {
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private ClosedQuestionFillingDao closedQuestionFillingDao;
    @Resource
    private ResultCalculationBo resultCalculationBo;
    
    /**
     * Test of calculate method, of class ResultCalculationBo.
     */
    public void testCalculate() {
        List<AssessmentFilling> assessments = assessmentFillingDao.findByIdentifierOrderBySubmittedDate("12345");
        assertSize(1, assessments);
        AssessmentFilling assessment = assessments.get(0);
        BigDecimal result = resultCalculationBo.calculate(assessment);
        assertNotNull(result);
        assertEquals(31.5, result.doubleValue());
        assertEquals(result, assessment.getEvaluationResult());
        assertEquals(Boolean.TRUE, assessment.isEvaluated());
    }

    /**
     * Test of calculateNormalizedSum method, of class ResultCalculationBo.
     */
    public void testCalculateNormalizedSum() {
        List<AssessmentFilling> assessments = assessmentFillingDao.findByIdentifierOrderBySubmittedDate("12345");
        assertSize(1, assessments);
        AssessmentFilling assessment = assessments.get(0);
        final BigDecimal maxValue = BigDecimal.valueOf(10);
        resultCalculationBo.evaluateAllQuestions(assessment);
        BigDecimal result = resultCalculationBo.calculateNormalizedSum(assessment,maxValue);
        assertNotNull(result);
        assertTrue("Result is over the maximum", result.compareTo(maxValue) <= 0);
        assertEquals(5.0, result.doubleValue());
    }

    public void testEvaluateClosedQuestion1() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers = new LinkedList<FixedAnswerFilling>();
        FixedAnswerFilling fixedAnswer = new FixedAnswerFilling();
        fixedAnswer.setWeight(BigDecimal.ONE);
        fixedAnswer.setCorrect(Boolean.TRUE);
        fixedAnswer.setSelected(Boolean.TRUE);
        fixedAnswers.add(fixedAnswer);
        fixedAnswer = new FixedAnswerFilling();
        fixedAnswer.setWeight(BigDecimal.ONE);
        fixedAnswer.setCorrect(Boolean.FALSE);
        fixedAnswer.setSelected(Boolean.FALSE);
        fixedAnswers.add(fixedAnswer);
        question.setFixedAnswers(fixedAnswers);
        resultCalculationBo.evaluateClosedQuestion(question, null);
        assertNotNull(question.getMark());
        assertEquals(BigDecimal.ONE, question.getMark());
    }

    public void testEvaluateClosedQuestion2() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers = new LinkedList<FixedAnswerFilling>();
        FixedAnswerFilling fixedAnswer = new FixedAnswerFilling();
        fixedAnswer.setWeight(BigDecimal.ONE);
        fixedAnswer.setCorrect(Boolean.TRUE);
        fixedAnswer.setSelected(Boolean.TRUE);
        fixedAnswers.add(fixedAnswer);
        fixedAnswer = new FixedAnswerFilling();
        fixedAnswer.setWeight(BigDecimal.ONE);
        fixedAnswer.setCorrect(Boolean.FALSE);
        fixedAnswer.setSelected(Boolean.TRUE);
        fixedAnswers.add(fixedAnswer);
        question.setFixedAnswers(fixedAnswers);
        resultCalculationBo.evaluateClosedQuestion(question, null);
        assertNotNull(question.getMark());
        assertEquals(BigDecimal.ONE, question.getMark());
    }

    public void testEvaluateClosedQuestion3() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers = new LinkedList<FixedAnswerFilling>();
        FixedAnswerFilling fixedAnswer = new FixedAnswerFilling();
        fixedAnswer.setWeight(BigDecimal.ONE);
        fixedAnswer.setCorrect(Boolean.TRUE);
        fixedAnswer.setSelected(Boolean.TRUE);
        fixedAnswers.add(fixedAnswer);
        fixedAnswer = new FixedAnswerFilling();
        fixedAnswer.setWeight(BigDecimal.ONE);
        fixedAnswer.setCorrect(Boolean.TRUE);
        fixedAnswer.setSelected(Boolean.FALSE);
        fixedAnswers.add(fixedAnswer);
        fixedAnswer = new FixedAnswerFilling();
        fixedAnswer.setWeight(BigDecimal.ONE);
        fixedAnswer.setCorrect(Boolean.FALSE);
        fixedAnswer.setSelected(Boolean.TRUE);
        fixedAnswers.add(fixedAnswer);
        question.setFixedAnswers(fixedAnswers);
        resultCalculationBo.evaluateClosedQuestion(question, null);
        assertNotNull(question.getMark());
        assertEquals(new BigDecimal(0.5), question.getMark());
    }

}