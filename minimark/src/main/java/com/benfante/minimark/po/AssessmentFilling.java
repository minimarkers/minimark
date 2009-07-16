package com.benfante.minimark.po;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import org.hibernate.annotations.Cascade;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * A filled assessment
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name="AssessmentFilling.findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier", query="from AssessmentFilling af where af.assessment.id = ? order by af.lastName, af.firstName, af.identifier")
})
public class AssessmentFilling extends EntityBase {
    protected Assessment assessment;
    protected Date startDate;
    @NotBlank
    protected String firstName;
    @NotBlank
    protected String lastName;
    @NotBlank
    protected String identifier;
    @Expression("password == assessment.password")
    protected String password;
    protected Boolean loggedIn;
    protected Date submittedDate;
    protected List<QuestionFilling> questions = new LinkedList<QuestionFilling>();

    @ManyToOne
    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    @OneToMany(mappedBy = "assessmentFilling", cascade=CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OrderBy("ordering ASC")
    public List<QuestionFilling> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionFilling> questions) {
        this.questions = questions;
    }

}
