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
package com.benfante.minimark.po;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;

/**
 * An closed question filling
 *
 * @author lucio
 */
@Entity
@DiscriminatorValue("ClosedQuestion")
public class ClosedQuestionFilling extends QuestionFilling {

    private List<FixedAnswerFilling> fixedAnswers = new LinkedList<FixedAnswerFilling>();

    @OneToMany(mappedBy = "question", cascade=CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OrderBy("ordering ASC")
    public List<FixedAnswerFilling> getFixedAnswers() {
        return fixedAnswers;
    }

    public void setFixedAnswers(List<FixedAnswerFilling> fixedAnswers) {
        this.fixedAnswers = fixedAnswers;
    }

    @Transient
    public boolean isMultipleAnswer() {
        return (countCorrectAnswers() > 1);
    }

    public int countCorrectAnswers() {
        int count = 0;
        for (FixedAnswerFilling fixedAnswer : getFixedAnswers()) {
            if (fixedAnswer.getCorrect() != null && fixedAnswer.getCorrect()) {
                count++;
            }
        }
        return count;
    }

    public int countSelectedCorrectAnswers() {
        int count = 0;
        for (FixedAnswerFilling fixedAnswer : getFixedAnswers()) {
            if ((fixedAnswer.getCorrect() != null && fixedAnswer.getCorrect())
                    && (fixedAnswer.getSelected() != null && fixedAnswer.getSelected())) {
                count++;
            }
        }
        return count;
    }

    public BigDecimal weightCorrectAnswers() {
        BigDecimal result = BigDecimal.ZERO;
        for (FixedAnswerFilling fixedAnswer : getFixedAnswers()) {
            if (fixedAnswer.getCorrect() != null && fixedAnswer.getCorrect()) {
                result = result.add(fixedAnswer.getWeight());
            }
        }
        return result;
    }

    public BigDecimal weightSelectedCorrectAnswers() {
        BigDecimal result = BigDecimal.ZERO;
        for (FixedAnswerFilling fixedAnswer : getFixedAnswers()) {
            if ((fixedAnswer.getCorrect() != null && fixedAnswer.getCorrect())
                    && (fixedAnswer.getSelected() != null && fixedAnswer.getSelected())) {
                result = result.add(fixedAnswer.getWeight());
            }
        }
        return result;
    }

    public BigDecimal weightWrongAnswers() {
        BigDecimal result = BigDecimal.ZERO;
        for (FixedAnswerFilling fixedAnswer : getFixedAnswers()) {
            if (fixedAnswer.getCorrect() == null || !fixedAnswer.getCorrect()) {
                result = result.add(fixedAnswer.getWeight());
            }
        }
        return result;
    }

    public BigDecimal weightSelectedWrongAnswers() {
        BigDecimal result = BigDecimal.ZERO;
        for (FixedAnswerFilling fixedAnswer : getFixedAnswers()) {
            if ((fixedAnswer.getCorrect() == null || !fixedAnswer.getCorrect())
                    && (fixedAnswer.getSelected() != null && fixedAnswer.getSelected())) {
                result = result.add(fixedAnswer.getWeight());
            }
        }
        return result;
    }

}
