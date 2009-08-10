package com.benfante.minimark.po;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.MaxLength;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * The course of a teacher
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Course.findByTeacherUsername", query="select ct.course from CourseTeacher ct where ct.userProfile.user.username = ?")
})
public class Course extends EntityBase {
    private List<Assessment> assessments;
    private List<Question> questions;
    @NotBlank
    @MaxLength(255)
    private String name;
    @NotBlank
    @MaxLength(1024)
    private String description;
    private String incumbent;
    private String mainGroup;
    private String secondaryGroup;
    private List<CourseTeacher> courseTeachers = new LinkedList<CourseTeacher>();

    @Column(length=1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "course", cascade=CascadeType.ALL)
    public List<CourseTeacher> getCourseTeachers() {
        return courseTeachers;
    }

    public void setCourseTeachers(List<CourseTeacher> courseTeachers) {
        this.courseTeachers = courseTeachers;
    }

    @OneToMany(mappedBy = "course")
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(String mainGroup) {
        this.mainGroup = mainGroup;
    }

    public String getSecondaryGroup() {
        return secondaryGroup;
    }

    public void setSecondaryGroup(String secondaryGroup) {
        this.secondaryGroup = secondaryGroup;
    }

    @OneToMany(mappedBy = "course")
    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public String getIncumbent() {
        return incumbent;
    }

    public void setIncumbent(String incumbent) {
        this.incumbent = incumbent;
    }

    /**
     * Add a teacher to this course.
     *
     * @param teacher The user profile of the teacher
     */
    public void addTeacher(UserProfile teacher) {
        CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setCourse(this);
        courseTeacher.setUserProfile(teacher);
        this.courseTeachers.add(courseTeacher);
    }
}
