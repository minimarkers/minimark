package com.benfante.minimark.beans;

import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.FixedAnswer;
import com.benfante.minimark.util.TextFilterUtils;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * A bean for editing a question
 *
 * @author lucio
 */
public class QuestionBean {

    private Course course;
    private Long id;
    private String type;
    private String title;
    @NotBlank
    private String content;
    private String contentFilter;
    private BigDecimal weight;
    private String visualization;
    private Integer answerMaxLength;
    private String tags;
    private List<FixedAnswer> fixedAnswers = new LinkedList<FixedAnswer>();

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnswerMaxLength() {
        return answerMaxLength;
    }

    public void setAnswerMaxLength(Integer answerMaxLength) {
        this.answerMaxLength = answerMaxLength;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisualization() {
        return visualization;
    }

    public void setVisualization(String visualization) {
        this.visualization = visualization;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getTags() {
        return tags;
    }

    public List<String> getTagList() {
        List<String> result = new LinkedList<String>();
        String[] tagArray = getTags().split(",");
        for (String tag : tagArray) {
            result.add(tag.trim().toLowerCase());
        }
        return result;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<FixedAnswer> getFixedAnswers() {
        return fixedAnswers;
    }

    public void setFixedAnswers(List<FixedAnswer> fixedAnswers) {
        this.fixedAnswers = fixedAnswers;
    }

    public String getFilteredContent() {
        return TextFilterUtils.formatText(this.content, this.contentFilter);
    }
    
}
