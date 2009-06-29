package com.benfante.minimark.po;

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
        int count = 0;
        for (FixedAnswerFilling fixedAnswer : getFixedAnswers()) {
            if (fixedAnswer.getCorrect()) {
                count++;
            }
        }
        return (count > 1);
    }
}
