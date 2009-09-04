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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A question of an assessment.
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name="AssessmentQuestion.getNextOrdering", query="select max(aq.ordering)+1 from AssessmentQuestion aq where aq.assessment.id = ?"),
    @NamedQuery(name="AssessmentQuestion.findByAssessmentId", query="from AssessmentQuestion aq where aq.assessment.id = ? order by aq.ordering")
})
public class AssessmentQuestion extends EntityBase {
    protected Assessment assessment;
    protected Question question;
    protected Long ordering;

    @ManyToOne
    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    @ManyToOne
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }

}
