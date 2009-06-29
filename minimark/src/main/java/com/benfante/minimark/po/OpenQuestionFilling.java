package com.benfante.minimark.po;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * An open question filling
 *
 * @author lucio
 */
@Entity
@DiscriminatorValue("OpenQuestion")
public class OpenQuestionFilling extends QuestionFilling {

    protected Integer answerMaxLength;
    protected String answer;

    public Integer getAnswerMaxLength() {
        return answerMaxLength;
    }

    public void setAnswerMaxLength(Integer answerMaxLength) {
        this.answerMaxLength = answerMaxLength;
    }

    @Lob
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
