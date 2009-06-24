package com.benfante.minimark.po;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A question of an assessment.
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name="AssessmentQuestion.getNextOrdering", query="select max(aq.ordering)+1 from AssessmentQuestion aq where aq.assessment.id = ?")
})
public class AssessmentQuestion extends EntityBase {
    protected Assessment assessment;
    protected Question question;
    protected Long ordering;

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

    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }

}
