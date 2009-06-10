package com.benfante.minimark.po;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * An assessment
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Assessment.findByTeacherUsername", query="select a from Course c join c.assessments a join c.courseTeachers ct where ct.userProfile.user.username = ?")
})
public class Assessment extends EntityBase {
    private List<AssessmentFilling> assessmentFillings;
    protected List<AssessmentQuestion> questions;
    protected Course course;
    protected Boolean active;
    @NotBlank
    protected String title;
    @NotBlank
    protected String description;
    @NotNull
    protected Date assessmentDate;
    protected String password;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "assessment")
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
    private String confirmPassword ="";

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


}
