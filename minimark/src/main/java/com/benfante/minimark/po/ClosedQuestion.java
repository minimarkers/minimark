package com.benfante.minimark.po;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;

/**
 * An closed question
 *
 * @author lucio
 */
@Entity
@DiscriminatorValue("ClosedQuestion")
public class ClosedQuestion extends Question {

    private List<FixedAnswer> fixedAnswers;

    @OneToMany(mappedBy = "question", cascade=CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    public List<FixedAnswer> getFixedAnswers() {
        return fixedAnswers;
    }

    public void setFixedAnswers(List<FixedAnswer> fixedAnswers) {
        this.fixedAnswers = fixedAnswers;
    }

    @Transient
    public boolean isMultipleAnswer() {
        int count = 0;
        for (FixedAnswer fixedAnswer : getFixedAnswers()) {
            if (fixedAnswer.getCorrect()) {
                count++;
            }
        }
        return (count > 1);
    }
}
