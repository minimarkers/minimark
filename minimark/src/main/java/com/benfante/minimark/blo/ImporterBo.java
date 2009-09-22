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

import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.FixedAnswer;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.Question;
import com.benfante.minimark.util.TextFilterUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Methods for importing old minimark data.
 *
 * @author Lucio Benfante
 */
@Service
public class ImporterBo {

    @Resource
    private QuestionDao questionDao;

    public List<Question> readQuestionSet(Reader r) throws ParseException, IOException {
        List<Question> result = new LinkedList<Question>();
        int lineCount = 0;
        String line = null;
        BufferedReader br = null;
        if (r instanceof BufferedReader) {
            br = (BufferedReader) r;
        } else {
            br = new BufferedReader(r);
        }
        endstream:
        while ((line = br.readLine()) != null) {
            lineCount++;
            // skip empty and comment lines
            if (StringUtils.isBlank(line) || line.charAt(0) == 'c') {
                continue;
            }
            char questionType = 0;
            int questionId = 0;
            StringBuffer questionText = new StringBuffer();
            List<FixedAnswer> answers = new LinkedList<FixedAnswer>();
            String tmp;
            if (line.charAt(0) != 'd') {
                throw new ParseException("Start of question not found", lineCount);
            }
            StringTokenizer tk = new StringTokenizer(line, "|");
            tk.nextToken(); // skip the initial 'd'
            // question id
            try {
                tmp = tk.nextToken();
                if (tmp != null) {
                    questionId = Integer.parseInt(tmp);
                } else {
                    throw new ParseException("Question id not found", lineCount);
                }
            } catch (NumberFormatException e) {
                throw new ParseException("The question id must be an integer", lineCount);
            }
            // question type
            tmp = tk.nextToken();
            if ((tmp != null) && (tmp.length() > 0)) {
                questionType = Character.toUpperCase(tmp.charAt(0));
                if ((questionType != 'A') &&
                        (questionType != 'R') &&
                        (questionType != 'C') &&
                        (questionType != 'T')) {
                    throw new ParseException("Question type unknown", lineCount);
                }
            } else {
                throw new ParseException("Question type not found", lineCount);
            }
            // answer scrambling flag
            tmp = tk.nextToken();
            if ((tmp != null) && (tmp.length() > 0)) {
//                char acf = tmp.charAt(0);
//                switch (acf) {
//                    case 'u':
//                        answerScrambling = false;
//                        break;
//                    case 's':
//                        answerScrambling = true;
//                        break;
//                    default:
//                        throw new ParseException("Answer scrambling flag unknown", lineCount);
//                }
            } else {
                throw new ParseException("Answer scrambling flag not found", lineCount);
            }
            // question text
            tmp = tk.nextToken();
            if ((tmp != null) && (tmp.length() > 0)) {
                questionText.append(tmp);
                if ((questionType == 'A') || (questionType == 'T')) { // multiline questions
                    while ((line = br.readLine()) != null) {
                        lineCount++;
                        if (StringUtils.isBlank(line)) {
                            break;
                        }
                        questionText.append('\n').append(line);
                    }
                }
            } else {
                throw new ParseException("Question text not found", lineCount);
            }
            // suggested answers
            if ((questionType == 'R') || (questionType == 'C')) {
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if (StringUtils.isBlank(line)) {
                        break;
                    }
                    tk = new StringTokenizer(line, "|");
                    // correct char
                    tmp = tk.nextToken();
                    boolean correct = false;
                    if ((tmp != null) && (tmp.length() > 0)) {
                        char correctChar = line.charAt(0);
                        if (correctChar == 'y') {
                            correct = true;
                        } else if (correctChar == 'n') {
                            correct = false;
                        } else {
                            throw new ParseException("Correct char unknown", lineCount);
                        }
                    } else {
                        throw new ParseException("Correct char not found (" + tmp + ")", lineCount);
                    }
                    tmp = tk.nextToken();
                    String answerText = null;
                    if ((tmp != null) && (tmp.length() > 0)) {
                        answerText = tmp;
                    } else {
                        throw new ParseException("Answer text not found", lineCount);
                    }
                    FixedAnswer fixedAnswer = new FixedAnswer();
                    fixedAnswer.setContent(answerText);
                    fixedAnswer.setContentFilter(TextFilterUtils.HTML_FILTER_CODE);
                    fixedAnswer.setCorrect(correct);
                    fixedAnswer.setWeight(BigDecimal.ONE);
                    answers.add(fixedAnswer);
                }
            }
            Question newQuestion = makeQuestion(questionType, questionId, questionText.toString(), answers);
            result.add(newQuestion);
        }
        return result;
    }

    private Question makeQuestion(char type,
            int id,
            String text,
            List<FixedAnswer> answers) {
        Question result = null;
        switch (type) {
            case 'A':
                OpenQuestion openLongQuestion = new OpenQuestion();
                openLongQuestion.setTitle(Integer.toString(id));
                openLongQuestion.setContent(text);
                openLongQuestion.setContentFilter(TextFilterUtils.HTML_FILTER_CODE);
                openLongQuestion.setVisualization(OpenQuestion.VISUALIZATION_LONG);
                openLongQuestion.setAnswerMaxLength(1024);
                openLongQuestion.setWeight(new BigDecimal("3.0"));
                result = openLongQuestion;
                break;
            case 'T':
                OpenQuestion openShortQuestion = new OpenQuestion();
                openShortQuestion.setTitle(Integer.toString(id));
                openShortQuestion.setContent(text);
                openShortQuestion.setContentFilter(TextFilterUtils.HTML_FILTER_CODE);
                openShortQuestion.setVisualization(OpenQuestion.VISUALIZATION_SHORT);
                openShortQuestion.setAnswerMaxLength(256);
                openShortQuestion.setWeight(new BigDecimal("1.0"));
                result = openShortQuestion;
                break;
            case 'R':
                ClosedQuestion closedSingleQuestion = new ClosedQuestion();
                closedSingleQuestion.setTitle(Integer.toString(id));
                closedSingleQuestion.setContent(text);
                closedSingleQuestion.setContentFilter(TextFilterUtils.HTML_FILTER_CODE);
                closedSingleQuestion.setFixedAnswers(answers);
                closedSingleQuestion.setWeight(new BigDecimal("1.0"));
                result = closedSingleQuestion;
                break;
            case 'C':
                ClosedQuestion closedMultiQuestion = new ClosedQuestion();
                closedMultiQuestion.setTitle(Integer.toString(id));
                closedMultiQuestion.setContent(text);
                closedMultiQuestion.setContentFilter(TextFilterUtils.HTML_FILTER_CODE);
                closedMultiQuestion.setFixedAnswers(answers);
                closedMultiQuestion.setWeight(new BigDecimal("1.0"));
                result = closedMultiQuestion;
                break;
        }
        for (FixedAnswer fixedAnswer : answers) {
            if (result instanceof ClosedQuestion) {
                fixedAnswer.setQuestion((ClosedQuestion) result);
            }
        }
        return result;
    }

    public void addQuestionsToCourse(Course course, List<Question> result) {
        for (Question question : result) {
            question.setCourse(course);
            questionDao.store(question);
        }
    }
}
