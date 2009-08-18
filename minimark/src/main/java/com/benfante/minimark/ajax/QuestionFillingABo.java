/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfante.minimark.ajax;

import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.dao.FixedAnswerFillingDao;
import com.benfante.minimark.dao.OpenQuestionFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.FixedAnswerFilling;
import com.benfante.minimark.po.OpenQuestionFilling;
import java.util.Date;
import javax.annotation.Resource;
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
        OpenQuestionFilling oqf = openQuestionFillingDao.get(id);
        oqf.setAnswer(value);
    }

    @RemoteMethod
    public void updateFixedAnswer(Long id, String value) {
        FixedAnswerFilling faf = fixedAnswerFillingDao.get(id);
        if (value != null) {
            faf.setSelected(Boolean.TRUE);
        } else {
            faf.setSelected(Boolean.FALSE);
        }
    }

    @RemoteMethod
    public void updateTimeLeft(Long id) {
        AssessmentFilling filling = assessmentFillingDao.get(id);
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
                final WebContext wctx = WebContextFactory.get();
                ScriptSession session = wctx.getScriptSession();
                Browser.withSession(session.getId(), new Runnable() {

                    public void run() {
                        Util.setValue("timeLeft", exposedTimeLeft+"m");
                    }
                });

            }
        }
    }
}
