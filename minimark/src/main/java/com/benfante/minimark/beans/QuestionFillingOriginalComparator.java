package com.benfante.minimark.beans;

import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.QuestionFilling;
import java.util.Comparator;
import java.util.List;

/**
 * A comparator for sorting assessement filling by the calculated current status.
 *
 * @author Lucio Benfante
 */
public class QuestionFillingOriginalComparator implements
        Comparator<QuestionFilling> {

    public int compare(QuestionFilling o1, QuestionFilling o2) {
        Long order1 = findOriginalOrder(o1);
        Long order2 = findOriginalOrder(o2);
        return (int) (order1.longValue() - order2.longValue());
    }

    private Long findOriginalOrder(QuestionFilling questionFilling) {
        Long result = null;
        List<AssessmentQuestion> originalQuestions =
                questionFilling.getAssessmentFilling().getAssessment().
                getQuestions();
        for (AssessmentQuestion assessmentQuestion : originalQuestions) {
            if (assessmentQuestion.getQuestion().equals(questionFilling.getOriginalQuestion())) {
                result = assessmentQuestion.getOrdering();
                break;
            }
        }
        if (result == null) {
            result = Long.valueOf(0);
        }
        return result;
    }
}
