/**
 * Copyright (C) 2009 Lucio Benfante <lucio.benfante@gmail.com>
 *
 * This file is part of minimark Web Application.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.benfante.minimark.po;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

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

    @Transient
    public Integer getCharsLeft() {
        Integer maxLength = ((OpenQuestion)this.getOriginalQuestion()).getAnswerMaxLength();
        if (maxLength != null) {
            int currLength = this.getAnswer()!=null?this.getAnswer().length():0;
            return maxLength.intValue()-currLength;
        }
        return null;
    }

}
