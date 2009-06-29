package com.benfante.minimark.po;

import com.benfante.minimark.util.TextFilterUtils;
import java.math.BigDecimal;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A filling of a question.
 *
 * @author lucio
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "questionType", discriminatorType = DiscriminatorType.STRING)
public class QuestionFilling extends EntityBase {

    protected String title;
    protected String content;
    protected String contentFilter;
    protected BigDecimal weight;
    protected String visualization;
    private AssessmentFilling assessmentFilling;
    private BigDecimal mark;
    private Question originalQuestion;
    private Long ordering;

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
    public AssessmentFilling getAssessmentFilling() {
        return assessmentFilling;
    }

    public void setAssessmentFilling(AssessmentFilling assessmentFilling) {
        this.assessmentFilling = assessmentFilling;
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

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    @ManyToOne
    public Question getOriginalQuestion() {
        return originalQuestion;
    }

    public void setOriginalQuestion(Question originalQuestion) {
        this.originalQuestion = originalQuestion;
    }

    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }

    @Transient
    public String getTypeCode() {
        String result = null;
        if (this instanceof OpenQuestionFilling) {
            result = "OpenQuestion";
        } else if (this instanceof ClosedQuestionFilling) {
            result = "ClosedQuestion";
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
