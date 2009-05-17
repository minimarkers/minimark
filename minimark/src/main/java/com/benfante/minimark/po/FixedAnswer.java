package com.benfante.minimark.po;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A fixed answer
 *
 * @author lucio
 */
@Entity
public class FixedAnswer extends EntityBase {
    protected String content;
    protected String contentFilter;
    protected BigDecimal weight;
    protected Boolean correct;
    protected ClosedQuestion question;

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
    public ClosedQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ClosedQuestion question) {
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

    @Transient
    public String getJavascriptEscapedContent() {
        return StringEscapeUtils.escapeJavaScript(this.content);
    }

}
