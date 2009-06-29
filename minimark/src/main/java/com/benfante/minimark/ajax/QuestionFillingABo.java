/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfante.minimark.ajax;

import com.benfante.minimark.dao.FixedAnswerFillingDao;
import com.benfante.minimark.dao.OpenQuestionFillingDao;
import com.benfante.minimark.po.FixedAnswerFilling;
import com.benfante.minimark.po.OpenQuestionFilling;
import javax.annotation.Resource;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
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

}
