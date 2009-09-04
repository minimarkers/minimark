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

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A link between a tag and a question
 *
 * @author lucio
 */
@Entity
@DiscriminatorValue("question")
@NamedQueries({
    @NamedQuery(name = "TagQuestionLink.findByTagName", query =
    "from TagQuestionLink tql where tql.tag.name = ? order by tql.question.title asc"),
    @NamedQuery(name = "TagQuestionLink.findByTagNameAndQuestionId", query =
    "from TagQuestionLink tql where tql.tag.name = ? and tql.question.id = ? order by tql.question.title asc"),
    @NamedQuery(name = "TagQuestionLink.retrieveTagCloud", query="select new com.benfante.minimark.beans.TagCloudItem(tql.tag.name, count(tql)) from TagQuestionLink tql group by tql.tag.name order by tql.tag.name"),
    @NamedQuery(name = "TagQuestionLink.findByCourseId", query="from TagQuestionLink tql where tql.question.course.id = ? order by tql.tag.name")
})
public class TagQuestionLink extends TagLink {

    private Question question;

    public TagQuestionLink() {
    }

    public TagQuestionLink(Tag tag, Question question) {
        this.tag = tag;
        this.question = question;
    }

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


}
