package com.benfante.minimark.po;

import com.benfante.minimark.util.TextFilterUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
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
    private SortedSet<TagQuestionLink> tags = new TreeSet<TagQuestionLink>();

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

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Sort(type = SortType.NATURAL)
    public SortedSet<TagQuestionLink> getTags() {
        return tags;
    }

    public void setTags(SortedSet<TagQuestionLink> tags) {
        this.tags = tags;
    }

    private String tagList;

    @Transient
    public String getTagList() {
        if (this.tagList == null) {
            this.tagList = buildTagList();
        }
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }

    /**
     * Build the comma-separated list of tags.
     */
    public String buildTagList() {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (TagQuestionLink tag : getTags()) {
            if (!first) {
                result.append(", ");
            } else {
                first = false;
            }
            result.append(tag.getTag().getName());
        }
        return result.toString();
    }


    @Transient
    public String getTypeCode() {
        String result = null;
        if (this instanceof OpenQuestion) {
            result = "OpenQuestion";
        } else if (this instanceof ClosedQuestion) {
            result ="ClosedQuestion";
        } else {
            result = "UnknownQuestionType";
        }
        return result;
    }

    @Transient
    public String getFilteredContent() {
        return TextFilterUtils.formatText(this.content, this.contentFilter);
    }

}
