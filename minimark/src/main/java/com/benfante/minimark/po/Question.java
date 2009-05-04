package com.benfante.minimark.po;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A link of a tag to another entity. Links are managed by subclasses.
 *
 * @author lucio
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "questionType", discriminatorType =
DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(name="Question.findByCourseId", query="from Question q where q.course.id = ?")
})
public class Question extends EntityBase {

    protected String title;
    protected String content;
    protected String contentFilter;
    protected BigDecimal weight;
    protected Course course;
    protected String visualization;
    private List<AssessmentQuestion> assessments;

    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentFilter() {
        return contentFilter;
    }

    public void setContentFilter(String contentFilter) {
        this.contentFilter = contentFilter;
    }

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getVisualization() {
        return visualization;
    }

    public void setVisualization(String visualization) {
        this.visualization = visualization;
    }

    @OneToMany(mappedBy = "question")
    public List<AssessmentQuestion> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<AssessmentQuestion> assessments) {
        this.assessments = assessments;
    }
}
