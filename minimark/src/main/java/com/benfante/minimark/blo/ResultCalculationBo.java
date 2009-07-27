package com.benfante.minimark.blo;

import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.QuestionFilling;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

/**
 * Methods for calculating assessment results.
 *
 * @author Lucio Benfante
 */
@Service
public class ResultCalculationBo {

    /**
     * Calculate the evaluation of an assessment. Moreover it stores the result
     * in the assessment filling and sets the evaluated flag.
     *
     * @param assessment The assessment to evaluate.
     * @return The evaluation
     */
    public BigDecimal calculate(AssessmentFilling assessment) {
        BigDecimal result = BigDecimal.ZERO;
        evaluateAllQuestions(assessment);
        String evaluationType = assessment.getAssessment().getEvaluationType();
        if (evaluationType == null || Assessment.EVALUATION_SIMPLE_SUM.equals(
                evaluationType)) {
            result = calculateSimpleSum(assessment);
        }
        assessment.setEvaluationResult(result);
        assessment.setEvaluated(Boolean.TRUE);
        return result;
    }

    /**
     * Evaluate an assessment simply summing the marks of each question.
     *
     * @param assessment The assessment to evaluate.
     * @return The evaluation
     */
    public BigDecimal calculateSimpleSum(AssessmentFilling assessment) {
        BigDecimal result = BigDecimal.ZERO;
        for (QuestionFilling questionFilling : assessment.getQuestions()) {
            if (questionFilling.getMark() != null) {
                // result = result + (mark*weight)
                result = result.add(questionFilling.getMark().multiply(questionFilling.
                        getWeight()));
            }
        }
        return result;
    }

    /**
     * Evaluate all questions of an assessment.
     *
     * @param assessment The assessment to evaluate.
     */
    public void evaluateAllQuestions(AssessmentFilling assessment) {
        for (QuestionFilling questionFilling : assessment.getQuestions()) {
            evaluateQuestion(questionFilling);
        }
    }

    /**
     * Evaluate a question.
     *
     * @param questionFilling The question to evaluate.
     */
    public void evaluateQuestion(QuestionFilling questionFilling) {
        if (questionFilling instanceof OpenQuestionFilling) {
            evaluateOpenQuestion((OpenQuestionFilling) questionFilling);
        } else if (questionFilling instanceof ClosedQuestionFilling) {
            evaluateClosedQuestion((ClosedQuestionFilling) questionFilling,
                    questionFilling.getAssessmentFilling().getAssessment().
                    getEvaluationClosedType());
        }
    }

    public void evaluateOpenQuestion(OpenQuestionFilling openQuestionFilling) {
        openQuestionFilling.setMark(BigDecimal.ZERO);
    }

    public void evaluateClosedQuestion(
            ClosedQuestionFilling closedQuestionFilling, String evaluationType) {
        BigDecimal result = BigDecimal.ZERO;
        if (evaluationType == null || Assessment.EVALUATION_CLOSED_SUM_CORRECT_ANSWERS.
                equals(evaluationType)) {
            final BigDecimal weightCorrect =
                    closedQuestionFilling.weightCorrectAnswers();
            if (!BigDecimal.ZERO.equals(weightCorrect)) {
                // r = #selectedCorrect/#correct
                result = closedQuestionFilling.weightSelectedCorrectAnswers().
                        divide(weightCorrect);
            }
        }
        closedQuestionFilling.setMark(result);
    }
}
