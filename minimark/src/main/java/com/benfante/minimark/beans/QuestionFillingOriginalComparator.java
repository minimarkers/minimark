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
