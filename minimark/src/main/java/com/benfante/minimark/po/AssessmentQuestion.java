package com.benfante.minimark.po;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A question of an assessment.
 *
 * @author lucio
 */
@Entity
public class AssessmentQuestion extends EntityBase {
    protected Assessment assessment;
    protected Question question;

    @ManyToOne
    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    @ManyToOne
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
