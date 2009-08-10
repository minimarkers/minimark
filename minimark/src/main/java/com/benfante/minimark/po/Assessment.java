package com.benfante.minimark.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;
import org.springmodules.validation.bean.conf.loader.annotation.handler.MaxLength;
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
    "select a from Course c join c.assessments a join c.courseTeachers ct where ct.userProfile.user.username = ?")
})
public class Assessment extends EntityBase {

    public static final String EVALUATION_SIMPLE_SUM = "simple_sum";
    public static final String EVALUATION_NORMALIZED_SUM = "normalized_sum";
    public static final String EVALUATION_CLOSED_SUM_CORRECT_ANSWERS =
            "sum_correct_answers";
    public static final String EVALUATION_CLOSED_SUM_CORRECT_MINUS_WRONG_ANSWERS =
            "sum_correct_minus_wrong_answers";
    private List<AssessmentFilling> assessmentFillings;
    protected List<AssessmentQuestion> questions;
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

    @Column(length=1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "assessment")
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
}
