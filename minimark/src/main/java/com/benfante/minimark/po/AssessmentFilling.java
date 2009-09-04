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
import javax.persistence.Transient;
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
    @NamedQuery(name="AssessmentFilling.findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier", query="from AssessmentFilling af where af.assessment.id = ? order by af.lastName, af.firstName, af.identifier"),
    @NamedQuery(name="AssessmentFilling.findByAssessmentIdAndIdentifier", query="from AssessmentFilling af where af.assessment.id = ? and lower(af.identifier) = lower(?)")
})
public class AssessmentFilling extends EntityBase {
    public static final String CURRENT_STATE_NOT_STARTED = "not_started";
    public static final String CURRENT_STATE_STARTED = "started";
    public static final String CURRENT_STATE_COMPLETED = "completed";
    public static final String CURRENT_STATE_CONFIRMED = "confirmed";

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
    protected Boolean evaluated;
    protected BigDecimal evaluationResult;
    protected Date confirmedDate;

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

    public Boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Boolean evaluated) {
        this.evaluated = evaluated;
    }

    public BigDecimal getEvaluationResult() {
        return evaluationResult;
    }

    public void setEvaluationResult(BigDecimal evaluationResult) {
        this.evaluationResult = evaluationResult;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }


    @Transient
    public BigDecimal getTotalWeight() {
        BigDecimal result = BigDecimal.ZERO;
        for (QuestionFilling question : getQuestions()) {
            result = result.add(question.getWeight());
        }
        return result;
    }

    @Transient
    public String getCurrentState() {
        if (this.getStartDate() == null) {
            return CURRENT_STATE_NOT_STARTED;
        }
        if (this.getConfirmedDate() != null) {
            return CURRENT_STATE_CONFIRMED;
        }
        if (this.getSubmittedDate() == null) {
            return CURRENT_STATE_STARTED;
        } else {
            return CURRENT_STATE_COMPLETED;
        }
    }

    @Transient
    public int getCurrentStateOrdered() {
        if(CURRENT_STATE_COMPLETED.equals(getCurrentState())) {
            return 10;
        }
        if(CURRENT_STATE_STARTED.equals(getCurrentState())) {
            return 20;
        }
        if(CURRENT_STATE_NOT_STARTED.equals(getCurrentState())) {
            return 30;
        }
        if(CURRENT_STATE_CONFIRMED.equals(getCurrentState())) {
            return 40;
        }
        return 0;
    }
}
