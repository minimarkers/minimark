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
package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Course;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests of the class QuestionBo.
 *
 * @author Lucio Benfante
 */
public class QuestionBoTest extends MinimarkBaseTest {
    @Resource
    private QuestionBo questionBo;
    @Resource
    private CourseDao courseDao;
    
    /**
     * Test of search method, of class QuestionBo.
     */
    public void testSearchAll() {
        List result = questionBo.search(new QuestionBean());
        assertSize(5, result);
    }

    public void testSearchByCourse() {
        final QuestionBean questionBean = new QuestionBean();
        List<Course> courses = courseDao.findByNameAndMainGroupAndSecondaryGroup("Informatica Generale", "Facoltà di Lettere e Filosofia", "MLC");
        assertNotEmpty(courses);
        assertSize(1, courses);
        questionBean.setCourse(courses.get(0));
        List result = questionBo.search(questionBean);
        assertSize(1, result);
    }

    public void testSearchByTag() {
        final QuestionBean questionBean = new QuestionBean();
        questionBean.setTags("test1");
        List result = questionBo.search(questionBean);
        assertNotEmpty(result);
        assertSize(1, result);
    }

    public void testSearchByTagList() {
        final QuestionBean questionBean = new QuestionBean();
        questionBean.setTags("test1, TEST3");
        List result = questionBo.search(questionBean);
        assertNotEmpty(result);
        assertSize(2, result);
    }

    public void testSearchByAllTags() {
        final QuestionBean questionBean = new QuestionBean();
        questionBean.setTags("test1, TEST2,test3");
        List result = questionBo.search(questionBean);
        assertNotEmpty(result);
        assertSize(2, result);
    }

}
