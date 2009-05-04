package com.benfante.minimark.po;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A teecher in a course
 *
 * @author lucio
 */
@Entity
public class CourseTeacher extends EntityBase {
    private Course course;
    private UserProfile userProfile;

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

}
