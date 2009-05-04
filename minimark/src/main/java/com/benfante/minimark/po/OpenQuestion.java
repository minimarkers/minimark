package com.benfante.minimark.po;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * An open question
 *
 * @author lucio
 */
@Entity
@DiscriminatorValue("OpenQuestion")
public class OpenQuestion extends Question {
    public static final String VISUALIZATION_LONG = "long";
    public static final String VISUALIZATION_SHORT = "short";

    protected Integer answerMaxLength;

    public Integer getAnswerMaxLength() {
        return answerMaxLength;
    }

    public void setAnswerMaxLength(Integer answerMaxLength) {
        this.answerMaxLength = answerMaxLength;
    }
}
