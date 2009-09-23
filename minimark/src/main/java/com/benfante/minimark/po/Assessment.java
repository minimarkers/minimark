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
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;
import org.springmodules.validation.bean.conf.loader.annotation.handler.MaxLength;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Min;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * An assessment
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Assessment.findByTeacherUsername",
    query =
    "select a from Course c join c.assessments a join c.courseTeachers ct where ct.userProfile.user.username = ? order by a.assessmentDate desc")
})
public class Assessment extends EntityBase {

    public static final String EVALUATION_SIMPLE_SUM = "simple_sum";
    public static final String EVALUATION_NORMALIZED_SUM = "normalized_sum";
    public static final String EVALUATION_CLOSED_SUM_CORRECT_ANSWERS =
            "sum_correct_answers";
    public static final String EVALUATION_CLOSED_SUM_CORRECT_MINUS_WRONG_ANSWERS =
            "sum_correct_minus_wrong_answers";
    private List<AssessmentFilling> assessmentFillings;
    protected List<AssessmentQuestion> questions =
            new LinkedList<AssessmentQuestion>();
    protected Course course;
    protected Boolean active = Boolean.FALSE;
    @NotBlank
    @MaxLength(255)
    protected String title;
    @NotBlank
    @MaxLength(1024)
    protected String description;
    @NotNull
    protected Date assessmentDate;
    protected String password;
    protected String evaluationType;
    protected String evaluationClosedType;
    @NotNull(applyIf = "evaluationType == 'normalized_sum'")
    protected BigDecimal evaluationMaxValue;
    protected BigDecimal evaluationClosedMinimumEvaluation = BigDecimal.ZERO;
    protected Boolean showInHomePage = Boolean.FALSE;
    @Min(value = 0.0)
    protected Long duration = Long.valueOf(0);
    protected Boolean allowStudentPrint = Boolean.FALSE;
    protected String exposedResult = "none";
    @NotNull(applyIf = "exposedResult == 'passed'")
    @Min(value = 0.0, applyIf = "exposedResult == 'passed'")
    protected BigDecimal minPassedValue;
    protected Boolean shuffleQuestions = Boolean.FALSE;
    protected String monitoringUsers;
    protected BigDecimal blankAnswerWeight;

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OrderBy("ordering ASC")
    public List<AssessmentQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AssessmentQuestion> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* Password verification */
    private String newPassword = "";
    @Expression("confirmPassword == newPassword")
    private String confirmPassword = "";

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Transient
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @OneToMany(mappedBy = "assessment")
    public List<AssessmentFilling> getAssessmentFillings() {
        return assessmentFillings;
    }

    public void setAssessmentFillings(List<AssessmentFilling> assessmentFillings) {
        this.assessmentFillings = assessmentFillings;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getEvaluationClosedType() {
        return evaluationClosedType;
    }

    public void setEvaluationClosedType(String evaluationClosedType) {
        this.evaluationClosedType = evaluationClosedType;
    }

    public BigDecimal getEvaluationMaxValue() {
        return evaluationMaxValue;
    }

    public void setEvaluationMaxValue(BigDecimal evaluationMaxValue) {
        this.evaluationMaxValue = evaluationMaxValue;
    }

    public BigDecimal getEvaluationClosedMinimumEvaluation() {
        return evaluationClosedMinimumEvaluation;
    }

    public void setEvaluationClosedMinimumEvaluation(
            BigDecimal evaluationClosedMinimumEvaluation) {
        this.evaluationClosedMinimumEvaluation =
                evaluationClosedMinimumEvaluation;
    }

    public Boolean getShowInHomePage() {
        return showInHomePage;
    }

    public void setShowInHomePage(Boolean showInHomePage) {
        this.showInHomePage = showInHomePage;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Boolean getAllowStudentPrint() {
        return allowStudentPrint;
    }

    public void setAllowStudentPrint(Boolean allowStudentPrint) {
        this.allowStudentPrint = allowStudentPrint;
    }

    public String getExposedResult() {
        return exposedResult;
    }

    public void setExposedResult(String exposedResult) {
        this.exposedResult = exposedResult;
    }

    public BigDecimal getMinPassedValue() {
        return minPassedValue;
    }

    public void setMinPassedValue(BigDecimal minPassedValue) {
        this.minPassedValue = minPassedValue;
    }

    public Boolean getShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(Boolean shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }

    public String getMonitoringUsers() {
        return monitoringUsers;
    }

    public void setMonitoringUsers(String monitoringUsers) {
        this.monitoringUsers = monitoringUsers;
    }

    public BigDecimal getBlankAnswerWeight() {
        return blankAnswerWeight;
    }

    public void setBlankAnswerWeight(BigDecimal blankAnswerWeight) {
        this.blankAnswerWeight = blankAnswerWeight;
    }

    @Transient
    public String[] getMonitoringUsersAsArray() {
        if (StringUtils.isNotBlank(getMonitoringUsers())) {
            return getMonitoringUsers().split(",");
        } else {
            return new String[0];
        }
    }

    @Transient
    public int getCountAllQuestions() {
        return getQuestions().size();
    }

    @Transient
    public int getCountOpenShortQuestions() {
        int result = 0;
        for (AssessmentQuestion assessmentQuestion : getQuestions()) {
            final Question question = assessmentQuestion.getQuestion();
            if (question instanceof OpenQuestion) {
                final OpenQuestion openQuestion = (OpenQuestion) question;
                if (OpenQuestion.VISUALIZATION_SHORT.equals(openQuestion.
                        getVisualization())) {
                    result++;
                }
            }
        }
        return result;
    }

    @Transient
    public int getCountOpenLongQuestions() {
        int result = 0;
        for (AssessmentQuestion assessmentQuestion : getQuestions()) {
            final Question question = assessmentQuestion.getQuestion();
            if (question instanceof OpenQuestion) {
                final OpenQuestion openQuestion = (OpenQuestion) question;
                if (OpenQuestion.VISUALIZATION_LONG.equals(openQuestion.
                        getVisualization())) {
                    result++;
                }
            }
        }
        return result;
    }

    @Transient
    public int getCountClosedSingleQuestions() {
        int result = 0;
        for (AssessmentQuestion assessmentQuestion : getQuestions()) {
            final Question question = assessmentQuestion.getQuestion();
            if (question instanceof ClosedQuestion) {
                final ClosedQuestion closedQuestion = (ClosedQuestion) question;
                if (!closedQuestion.isMultipleAnswer()) {
                    result++;
                }
            }
        }
        return result;
    }

    @Transient
    public int getCountClosedMultiQuestions() {
        int result = 0;
        for (AssessmentQuestion assessmentQuestion : getQuestions()) {
            final Question question = assessmentQuestion.getQuestion();
            if (question instanceof ClosedQuestion) {
                final ClosedQuestion closedQuestion = (ClosedQuestion) question;
                if (closedQuestion.isMultipleAnswer()) {
                    result++;
                }
            }
        }
        return result;
    }

    @Transient
    public BigDecimal getQuestionsTotalWeight() {
        double result = 0;
        for (AssessmentQuestion assessmentQuestion : getQuestions()) {
            final Question question = assessmentQuestion.getQuestion();
            if (question.getWeight() != null) {
                result += question.getWeight().doubleValue();
            }
        }
        final BigDecimal bdResult = BigDecimal.valueOf(result);
        bdResult.setScale(2, RoundingMode.HALF_EVEN);
        return bdResult;
    }

    @Transient
    public boolean getContainsDuplicatedQuestions() {
        boolean result = false;
        for (int i = 0; i < getQuestions().size(); i++) {
            Question q1 = getQuestions().get(i).getQuestion();
            for (int j = i + 1; j < getQuestions().size(); j++) {
                Question q2 = getQuestions().get(j).getQuestion();
                if (q1.equals(q2)) {
                    return true;
                }
            }
        }
        return result;
    }
}
