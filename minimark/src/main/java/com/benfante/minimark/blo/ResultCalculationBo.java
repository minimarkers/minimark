package com.benfante.minimark.blo;

import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.QuestionFilling;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        BigDecimal minimumEvaluation = assessment.getAssessment().
                getEvaluationClosedMinimumEvaluation();
        evaluateAllQuestions(assessment, minimumEvaluation);
        String evaluationType = assessment.getAssessment().getEvaluationType();
        if (evaluationType == null || Assessment.EVALUATION_SIMPLE_SUM.equals(
                evaluationType)) {
            result = calculateSimpleSum(assessment);
        } else if (Assessment.EVALUATION_NORMALIZED_SUM.equals(
                evaluationType)) {
            result = calculateNormalizedSum(assessment, assessment.getAssessment().
                    getEvaluationMaxValue());
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
                result = result.add(questionFilling.getMark().multiply(
                        questionFilling.getWeight()));
            }
        }
        return result;
    }

    /**
     * Evaluate an assessment normalizing the sum of the marks of each question
     * to a maximum value.
     *
     * @param assessment The assessment to evaluate.
     * @param maxValue The maximum value to wich normalize. If it's null, the default is 100.
     * @return The evaluation
     */
    public BigDecimal calculateNormalizedSum(AssessmentFilling assessment,
            BigDecimal maxValue) {
        if (maxValue == null) {
            maxValue = BigDecimal.valueOf(100.0);
        }
        BigDecimal result = BigDecimal.ZERO;
        for (QuestionFilling questionFilling : assessment.getQuestions()) {
            if (questionFilling.getMark() != null) {
                // result = result + (mark*weight)
                result = result.add(questionFilling.getMark().multiply(
                        questionFilling.getWeight()));
            }
        }
        final BigDecimal totalWeight = assessment.getTotalWeight();
        if (!BigDecimal.ZERO.equals(totalWeight)) {
            result = result.multiply(maxValue).divide(totalWeight,
                    RoundingMode.HALF_EVEN);
        }
        return result;
    }

    /**
     * Evaluate all questions of an assessment.
     *
     * @param assessment The assessment to evaluate.
     * @param minimumEvaluation The minimum value of the evaluation. If null, there will be no minimum.
     */
    public void evaluateAllQuestions(AssessmentFilling assessment,
            BigDecimal minimumEvaluation) {
        for (QuestionFilling questionFilling : assessment.getQuestions()) {
            evaluateQuestion(questionFilling, minimumEvaluation);
        }
    }

    /**
     * Evaluate a question.
     *
     * @param questionFilling The question to evaluate.
     * @param minimumEvaluation The minimum value of the evaluation. If null, there will be no minimum.
     */
    public void evaluateQuestion(QuestionFilling questionFilling,
            BigDecimal minimumEvaluation) {
        if (questionFilling instanceof OpenQuestionFilling) {
            evaluateOpenQuestion((OpenQuestionFilling) questionFilling);
        } else if (questionFilling instanceof ClosedQuestionFilling) {
            evaluateClosedQuestion((ClosedQuestionFilling) questionFilling,
                    questionFilling.getAssessmentFilling().getAssessment().
                    getEvaluationClosedType(), minimumEvaluation);
        }
    }

    public void evaluateOpenQuestion(OpenQuestionFilling openQuestionFilling) {
        if (openQuestionFilling.getMark() == null) {
            openQuestionFilling.setMark(BigDecimal.ZERO);
        }
    }

    public void evaluateClosedQuestion(
            ClosedQuestionFilling closedQuestionFilling, String evaluationType,
            BigDecimal minimumEvaluation) {
        BigDecimal result = BigDecimal.ZERO;
        if (evaluationType == null || Assessment.EVALUATION_CLOSED_SUM_CORRECT_MINUS_WRONG_ANSWERS.
                equals(evaluationType)) {
            final BigDecimal weightCorrect =
                    closedQuestionFilling.weightCorrectAnswers();
            final BigDecimal weightSelectedCorrect =
                    closedQuestionFilling.weightSelectedCorrectAnswers();
            final BigDecimal weightWrong = closedQuestionFilling.
                    weightWrongAnswers();
            final BigDecimal weightSelectedWrong = closedQuestionFilling.
                    weightSelectedWrongAnswers();
            BigDecimal normalizedCorrect = BigDecimal.ONE;
            if (!BigDecimal.ZERO.equals(weightCorrect)) {
                // nc = #selectedCorrect/#correct
                normalizedCorrect = weightSelectedCorrect.divide(weightCorrect);
            }
            BigDecimal normalizedWrong = BigDecimal.ONE;
            if (!BigDecimal.ZERO.equals(weightWrong)) {
                // nw = #selectedWrong/#wrong
                normalizedWrong = weightSelectedWrong.divide(weightWrong);
            }
            // r = nc-nw
            result = normalizedCorrect.subtract(normalizedWrong);
        } else if (Assessment.EVALUATION_CLOSED_SUM_CORRECT_ANSWERS.equals(
                evaluationType)) {
            final BigDecimal weightCorrect =
                    closedQuestionFilling.weightCorrectAnswers();
            final BigDecimal weightSelectedCorrect =
                    closedQuestionFilling.weightSelectedCorrectAnswers();
            result = BigDecimal.ONE;
            if (!BigDecimal.ZERO.equals(weightCorrect)) {
                // r = #selectedCorrect/#correct
                result = weightSelectedCorrect.divide(weightCorrect, 2, RoundingMode.HALF_EVEN);
            }
        }
        // r = max(r, minimumEvaluation)
        if (minimumEvaluation != null) {
            result = result.max(minimumEvaluation);
        }
        closedQuestionFilling.setMark(result);
    }
}
