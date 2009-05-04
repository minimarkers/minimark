package com.benfante.minimark.po;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * An closed question
 *
 * @author lucio
 */
@Entity
@DiscriminatorValue("ClosedQuestion")
public class ClosedQuestion extends Question {

    private List<FixedAnswer> fixedAnswers;

    @OneToMany(mappedBy = "question")
    public List<FixedAnswer> getFixedAnswers() {
        return fixedAnswers;
    }

    public void setFixedAnswers(List<FixedAnswer> fixedAnswers) {
        this.fixedAnswers = fixedAnswers;
    }
}
