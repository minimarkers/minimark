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
import com.benfante.minimark.dao.ClosedQuestionFillingDao;
import com.benfante.minimark.po.Assessment;
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
        List<AssessmentFilling> assessments = assessmentFillingDao.
                findByIdentifierOrderBySubmittedDate("12345");
        assertSize(2, assessments);
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
        List<AssessmentFilling> assessments = assessmentFillingDao.
                findByIdentifierOrderBySubmittedDate("12345");
        assertSize(2, assessments);
        AssessmentFilling assessment = assessments.get(0);
        final BigDecimal maxValue = BigDecimal.valueOf(10);
        resultCalculationBo.evaluateAllQuestions(assessment, null);
        BigDecimal result = resultCalculationBo.calculateNormalizedSum(
                assessment, maxValue);
        assertNotNull(result);
        assertTrue("Result is over the maximum", result.compareTo(maxValue) <= 0);
        assertEquals(5.0, result.doubleValue());
    }

    public void testEvaluateClosedQuestion1() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers =
                new LinkedList<FixedAnswerFilling>();
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
        resultCalculationBo.evaluateClosedQuestion(question,
                Assessment.EVALUATION_CLOSED_SUM_CORRECT_ANSWERS, null);
        assertNotNull(question.getMark());
        assertEquals(new BigDecimal("1.00"), question.getMark());
    }

    public void testEvaluateClosedQuestion2() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers =
                new LinkedList<FixedAnswerFilling>();
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
        resultCalculationBo.evaluateClosedQuestion(question,
                Assessment.EVALUATION_CLOSED_SUM_CORRECT_ANSWERS, null);
        assertNotNull(question.getMark());
        assertEquals(new BigDecimal("1.00"), question.getMark());
    }

    public void testEvaluateClosedQuestion3() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers =
                new LinkedList<FixedAnswerFilling>();
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
        resultCalculationBo.evaluateClosedQuestion(question,
                Assessment.EVALUATION_CLOSED_SUM_CORRECT_ANSWERS, null);
        assertNotNull(question.getMark());
        assertEquals(new BigDecimal("0.50"), question.getMark());
    }

    public void testEvaluateClosedQuestion4() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers =
                new LinkedList<FixedAnswerFilling>();
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
        resultCalculationBo.evaluateClosedQuestion(question,
                Assessment.EVALUATION_CLOSED_SUM_CORRECT_MINUS_WRONG_ANSWERS, null);
        assertNotNull(question.getMark());
        assertEquals(BigDecimal.ONE, question.getMark());
    }

    public void testEvaluateClosedQuestion5() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers =
                new LinkedList<FixedAnswerFilling>();
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
        resultCalculationBo.evaluateClosedQuestion(question,
                Assessment.EVALUATION_CLOSED_SUM_CORRECT_MINUS_WRONG_ANSWERS, null);
        assertNotNull(question.getMark());
        assertEquals(BigDecimal.ZERO, question.getMark());
    }

    public void testEvaluateClosedQuestion6() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers =
                new LinkedList<FixedAnswerFilling>();
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
        resultCalculationBo.evaluateClosedQuestion(question,
                Assessment.EVALUATION_CLOSED_SUM_CORRECT_MINUS_WRONG_ANSWERS, null);
        assertNotNull(question.getMark());
        assertEquals(BigDecimal.valueOf(-0.5), question.getMark());
    }

    public void testEvaluateClosedQuestion7() {
        ClosedQuestionFilling question = new ClosedQuestionFilling();
        question.setWeight(BigDecimal.ONE);
        List<FixedAnswerFilling> fixedAnswers =
                new LinkedList<FixedAnswerFilling>();
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
        resultCalculationBo.evaluateClosedQuestion(question,
                Assessment.EVALUATION_CLOSED_SUM_CORRECT_MINUS_WRONG_ANSWERS, BigDecimal.valueOf(-0.25));
        assertNotNull(question.getMark());
        assertEquals(BigDecimal.valueOf(-0.25), question.getMark());
    }

}
