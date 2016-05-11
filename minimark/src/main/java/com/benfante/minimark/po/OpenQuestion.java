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
    
    @Override
    public String toExportedForm() {
        StringBuilder result = new StringBuilder(super.toExportedForm());
        result.append("d|").append(this.getId()).append('|');
        if (VISUALIZATION_LONG.equals(this.getVisualization())) {
            result.append("A|");
        } else {
            result.append("T|");
        }
        result.append("u|");
        result.append(this.getFilteredContent()).append('\n');
        return result.toString();
    }
    
}
