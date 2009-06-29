package com.benfante.minimark.po;

import com.benfante.minimark.util.TextFilterUtils;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.apache.commons.lang.StringEscapeUtils;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A fixed answer
 *
 * @author lucio
 */
@Entity
public class FixedAnswerFilling extends EntityBase {
    protected String content;
    protected String contentFilter;
    protected BigDecimal weight;
    protected Boolean correct;
    protected ClosedQuestionFilling question;
    protected Boolean selected;
    protected Long ordering;

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
    public ClosedQuestionFilling getQuestion() {
        return question;
    }

    public void setQuestion(ClosedQuestionFilling question) {
        this.question = question;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }

    @Transient
    public String getJavascriptEscapedContent() {
        return StringEscapeUtils.escapeJavaScript(this.content);
    }

    @Transient
    public String getFilteredContent() {
        return TextFilterUtils.formatText(this.content, this.contentFilter);
    }

}
