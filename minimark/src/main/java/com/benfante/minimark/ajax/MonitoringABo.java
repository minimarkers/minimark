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
package com.benfante.minimark.ajax;

import com.benfante.minimark.blo.AssessmentFillingBo;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.Course;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.ui.dwr.Util;
import org.springframework.stereotype.Service;

/**
 * Ajax methods for managin the fixed answers.
 *
 * @author Lucio Benfante
 */
@Service
@RemoteProxy(name = "monitoringABo")
public class MonitoringABo {

    private static final Logger logger = Logger.getLogger(MonitoringABo.class);
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentFillingBo assessmentFillingBo;

    @RemoteMethod
    public void confirmAssessmentFilling(Long assessmentFillingId,
            Long courseId) throws IOException, ServletException {
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        AssessmentFilling filling =
                assessmentFillingDao.get(assessmentFillingId);
        filling.setConfirmedDate(new Date());
        assessmentFillingDao.store(filling);
        Browser.withSession(session.getId(), new BlockFillingsRefresher(wctx,
                courseId));
    }

    @RemoteMethod
    public void unconfirmAssessmentFilling(Long assessmentFillingId,
            Long courseId) throws IOException, ServletException {
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        AssessmentFilling filling =
                assessmentFillingDao.get(assessmentFillingId);
        filling.setConfirmedDate(null);
        assessmentFillingDao.store(filling);
        Browser.withSession(session.getId(), new BlockFillingsRefresher(wctx,
                courseId));
    }

    @RemoteMethod
    public void refreshFillingsBlock(Long courseId) throws IOException,
            ServletException {
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        Browser.withSession(session.getId(), new BlockFillingsRefresher(wctx,
                courseId));
    }

    @RemoteMethod
    public void refreshAllFillingsBlocks() throws IOException, ServletException {
        final Map<Course, List<AssessmentFilling>> courseFillings =
                assessmentFillingBo.mapFillingsOnCourse();
        final WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        Browser.withSession(session.getId(), new Runnable() {

            public void run() {
                HttpServletRequest req = wctx.getHttpServletRequest();
                for (Course course : courseFillings.keySet()) {
                    try {
                        req.setAttribute("blockFillings", courseFillings.get(
                                course));
                        Util.setValue("course_" + course.getId() + "_fillings", wctx.forwardToString(
                                "/WEB-INF/jsp/monitoring/fillingBlock.jsp"),
                                false);
                    } catch (Exception ex) {
                        logger.error(
                                "Error refreshing the block fillings for course " + course.getId(),
                                ex);
                        Util.setValue("course_" + course.getId() + "_fillings",
                                ex.getMessage());
                    }
                }
            }
        });
    }

    private class BlockFillingsRefresher implements Runnable {

        private WebContext wctx;
        private Long courseId;

        public BlockFillingsRefresher(WebContext wctx, Long courseId) {
            this.wctx = wctx;
            this.courseId = courseId;
        }

        public void run() {
            try {
                HttpServletRequest req = wctx.getHttpServletRequest();
                req.setAttribute("blockFillings", assessmentFillingBo.
                        searchActiveFillingsByCourse(courseId));
                Util.setValue("course_" + courseId + "_fillings", wctx.
                        forwardToString(
                        "/WEB-INF/jsp/monitoring/fillingBlock.jsp"), false);
            } catch (Exception ex) {
                logger.error(
                        "Error refreshing the block fillings for course " + this.courseId,
                        ex);
                Util.setValue("course_" + courseId + "_fillings",
                        ex.getMessage());
            }
        }
    }
}
