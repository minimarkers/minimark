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

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.Tag;
import com.benfante.minimark.po.TagQuestionLink;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests for the TagDao.
 *
 * @author lucio
 */
public class TagDaoTest extends MinimarkBaseTest {
    @Resource
    private TagDao tagDao;

    public void testFindAll() {
        List<Tag> tags = tagDao.findAll();
        assertNotNull(tags);
        assertSize(3, tags);
    }

    public void testFindByName() {
        Tag tag = tagDao.findByName("test1");
        assertNotNull(tag);
        assertEquals("test1", tag.getName());
        assertNotEmpty(tag.getTagLinks());
        assertSize(1, tag.getTagLinks());
        assertEquals("OpenQuestion 001", ((TagQuestionLink)tag.getTagLinks().iterator().next()).getQuestion().getTitle());
    }

}
