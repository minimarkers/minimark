package com.benfante.minimark.po;

import com.benfante.minimark.beans.QuestionRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.MaxLength;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Min;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * An assessment
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "AssessmentTemplate.findByCourseId",
    query = "from AssessmentTemplate at where at.course.id = ?")
})
public class AssessmentTemplate extends EntityBase {

    protected Course course;
    @NotBlank
    @MaxLength(255)
    protected String title;
    @NotBlank
    @MaxLength(1024)
    protected String description;
    protected String evaluationType;
    protected String evaluationClosedType;
    @NotNull(applyIf = "evaluationType == 'normalized_sum'")
    protected BigDecimal evaluationMaxValue;
    protected BigDecimal evaluationClosedMinimumEvaluation = BigDecimal.ZERO;
    @Min(value = 0.0)
    protected Long duration = Long.valueOf(0);
    protected Boolean allowStudentPrint = Boolean.FALSE;
    protected String exposedResult = "none";
    @NotNull(applyIf = "exposedResult == 'passed'")
    @Min(value = 0.0, applyIf = "exposedResult == 'passed'")
    protected BigDecimal minPassedValue;
    protected String tagSelectors;
    protected Boolean shuffleQuestions = Boolean.FALSE;

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Column(length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Boolean getAllowStudentPrint() {
        return allowStudentPrint;
    }

    public void setAllowStudentPrint(Boolean allowStudentPrint) {
        this.allowStudentPrint = allowStudentPrint;
    }

    public String getExposedResult() {
        return exposedResult;
    }

    public void setExposedResult(String exposedResult) {
        this.exposedResult = exposedResult;
    }

    public BigDecimal getMinPassedValue() {
        return minPassedValue;
    }

    public void setMinPassedValue(BigDecimal minPassedValue) {
        this.minPassedValue = minPassedValue;
    }

    @Lob
    public String getTagSelectors() {
        return tagSelectors;
    }

    public void setTagSelectors(String tagSelectors) {
        this.tagSelectors = tagSelectors;
    }

    public Boolean getShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(Boolean shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }
    protected List<QuestionRequest> questionRequests =
            new ArrayList<QuestionRequest>(10);

    @Transient
    public List<QuestionRequest> getQuestionRequests() {
        return questionRequests;
    }

    public void setQuestionRequests(List<QuestionRequest> questionRequests) {
        this.questionRequests = questionRequests;
    }

    public void buildQuestionRequests() {
        int dim = Math.max(10, questionRequests.size());
        questionRequests.clear();
        for (int i = 0; i < dim; i++) {
            questionRequests.add(new QuestionRequest());
        }
        if (StringUtils.isNotBlank(tagSelectors)) {
            String[] tsa = tagSelectors.split(",");
            int i = 0;
            for (String ts : tsa) {
                QuestionRequest qr = questionRequests.get(i);
                int howManyStart = ts.indexOf('[');
                int howManyEnd = ts.indexOf(']');
                qr.setTag(ts.substring(0, howManyStart));
                qr.setHowMany(Integer.parseInt(ts.substring(howManyStart + 1,
                        howManyEnd)));
                i++;
            }
        }
    }

    public void buildTagSelectors() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (QuestionRequest questionRequest : questionRequests) {
            if (StringUtils.isNotBlank(questionRequest.getTag())) {
                if (!first) {
                    sb.append(',');
                } else {
                    first = false;
                }
                sb.append(questionRequest.toString());
            }
        }
        tagSelectors = sb.toString();
    }
}
