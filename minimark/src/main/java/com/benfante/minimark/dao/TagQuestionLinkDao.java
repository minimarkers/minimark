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
package com.benfante.minimark.dao;

import com.benfante.minimark.beans.TagCloudItem;
import com.benfante.minimark.po.TagQuestionLink;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=TagQuestionLink.class)
public interface TagQuestionLinkDao extends GenericDao<TagQuestionLink, Long> {
    List<TagQuestionLink> findByTagName(String tag);
    TagQuestionLink findByTagNameAndQuestionId(String tag, Long questionId);
    List<TagCloudItem> retrieveTagCloud();
    List<TagQuestionLink> findByCourseId(Long courseId);
}
