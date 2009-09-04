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

import com.benfante.minimark.controllers.AssessmentFillingController;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.dao.FixedAnswerFillingDao;
import com.benfante.minimark.dao.OpenQuestionFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.FixedAnswerFilling;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.QuestionFilling;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.ui.dwr.Util;
import org.springframework.stereotype.Service;

/**
 * Ajax methods for managing the question filling.
 *
 * @author Lucio Benfante
 */
@Service
@RemoteProxy(name = "questionFillingABo")
public class QuestionFillingABo {

    @Resource
    private FixedAnswerFillingDao fixedAnswerFillingDao;
    @Resource
    private OpenQuestionFillingDao openQuestionFillingDao;
    @Resource
    private AssessmentFillingDao assessmentFillingDao;

    @RemoteMethod
    public void updateOpenQuestionAnswer(Long id, String value) {
        final WebContext wctx = WebContextFactory.get();
        AssessmentFilling filling = retrieveFilling(wctx);
        final OpenQuestionFilling oqf = retrieveOpenQuestionFilling(filling, id);
        oqf.setAnswer(value);
        openQuestionFillingDao.store(oqf);
        if (oqf.getCharsLeft() != null) {
            ScriptSession session = wctx.getScriptSession();
            Browser.withSession(session.getId(), new Runnable() {

                public void run() {
                    Util.setValue("q_" + oqf.getId() + "_charsLeft", oqf.
                            getCharsLeft().toString());
                }
            });
        }
    }

    @RemoteMethod
    public void updateFixedAnswer(Long id, String value) {
        final WebContext wctx = WebContextFactory.get();
        AssessmentFilling filling = retrieveFilling(wctx);
        FixedAnswerFilling faf = retrieveFixedAnswerFilling(filling, id);
        if (value != null) {
            faf.setSelected(Boolean.TRUE);
        } else {
            faf.setSelected(Boolean.FALSE);
        }
        fixedAnswerFillingDao.store(faf);
    }

    @RemoteMethod
    public void updateTimeLeft() {
        final WebContext wctx = WebContextFactory.get();
        AssessmentFilling filling = retrieveFilling(wctx);
        Long duration = filling.getAssessment().getDuration();
        if (duration != null && duration.longValue() != 0) {
            long timeLeft = duration;
            long timePassed = 0;
            final Date startDate = filling.getStartDate();
            if (startDate != null) {
                timePassed =
                        ((new Date().getTime()) - startDate.getTime()) / 60000;
                timeLeft = Math.max(0, duration - timePassed);
                final long exposedTimeLeft = timeLeft;
                ScriptSession session = wctx.getScriptSession();
                Browser.withSession(session.getId(), new Runnable() {

                    public void run() {
                        Util.setValue("timeLeft", exposedTimeLeft + "m");
                    }
                });

            }
        }
    }

    private AssessmentFilling retrieveFilling(WebContext wctx) {
        HttpSession session = wctx.getSession();
        return (AssessmentFilling) session.getAttribute(
                AssessmentFillingController.ASSESSMENT_ATTR_NAME);
    }

    private OpenQuestionFilling retrieveOpenQuestionFilling(
            AssessmentFilling filling, Long id) {
        OpenQuestionFilling result = null;
        for (QuestionFilling questionFilling : filling.getQuestions()) {
            if (questionFilling.getId().equals(id) &&
                    questionFilling instanceof OpenQuestionFilling) {
                result = (OpenQuestionFilling) questionFilling;
                break;
            }
        }
        return result;
    }

    private FixedAnswerFilling retrieveFixedAnswerFilling(
            AssessmentFilling filling, Long id) {
        for (QuestionFilling questionFilling : filling.getQuestions()) {
            if (questionFilling instanceof ClosedQuestionFilling) {
                for (FixedAnswerFilling fixedAnswerFilling :
                        ((ClosedQuestionFilling) questionFilling).
                        getFixedAnswers()) {
                    if (fixedAnswerFilling.getId().equals(id)) {
                        return fixedAnswerFilling;
                    }
                }
            }
        }
        return null;
    }
}
