<%--

    Copyright (C) 2009 Lucio Benfante <lucio.benfante@gmail.com>

    This file is part of minimark Web Application.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="Assessment" text="?Assessment?"/></h3>
<div class="summary">
    <p><spring:message code="Course" text="?Course?"/>: ${assessmentFilling.assessment.course.name}</p>
    <p><spring:message code="Title" text="?Title?"/>: ${assessmentFilling.assessment.title}</p>
    <p><spring:message code="Incumbent" text="?Incumbent?"/>: ${assessmentFilling.assessment.course.incumbent}</p>
    <p><spring:message code="Student" text="?Student?"/>: ${assessmentFilling.firstName} ${assessmentFilling.lastName} (${assessmentFilling.identifier})</p>
</div>
<h3><spring:message code="Questions" text="?Questions?"/></h3>
<spring:message code='confirmSubmitAssessmentFilling' text='?confirmSubmitAssessmentFilling?' var="confirmSubmitAssessmentFillingMessage"/>
<form action="${cp}/assessmentFilling/store.html" method="post" onsubmit="return confirm('${confirmSubmitAssessmentFillingMessage}')">
    <c:forEach var="question" items="${assessmentFilling.questions}">
        <div class="fillingQuestion">
            <div class="questionTitle">
                <c:choose>
                    <c:when test="${question.class.name == 'com.benfante.minimark.po.OpenQuestionFilling'}">
                        <c:choose>
                            <c:when test="${question.visualization == 'short'}">
                                <img src="${cp}/images/silk/icons/textfield.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" />
                            </c:when>
                            <c:when test="${question.visualization == 'long'}">
                                <img src="${cp}/images/silk/icons/textfield_add.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" />
                            </c:when>
                            <c:otherwise><spring:message code="UnknownVisualization" text="?UnknownVisualization?"/></c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${question.class.name == 'com.benfante.minimark.po.ClosedQuestionFilling'}">
                        <c:choose>
                            <c:when test="${question.multipleAnswer}">
                                <img src="${cp}/images/silk/icons/text_list_numbers.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" />
                            </c:when>
                            <c:otherwise>
                                <img src="${cp}/images/silk/icons/text_list_bullets.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" />
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise><spring:message code="UnknownQuestionType" text="?UnknownQuestionType?"/></c:otherwise>
                </c:choose>
                <div class="questionTitleText">${question.title}</div>
            </div>
            <div class="questionText filteredContent">${question.filteredContent}</div>

            <c:choose>
                <c:when test="${question.class.name == 'com.benfante.minimark.po.OpenQuestionFilling'}">
                    <div class="openAnswer">
                        <c:choose>
                            <c:when test="${question.visualization == 'short'}">
                                <input id="q_${question.id}" type="text" size="80" <c:if test="${!empty question.originalQuestion.answerMaxLength}">maxlength="${question.originalQuestion.answerMaxLength}"</c:if> value="<c:if test="${!empty question.answer}"><c:out value="${question.answer}" escapeXml="true"/></c:if>"/>
                            </c:when>
                            <c:when test="${question.visualization == 'long'}">
                                <textarea id="q_${question.id}" cols="80" rows="5" <c:if test="${!empty question.originalQuestion.answerMaxLength}">maxlength="${question.originalQuestion.answerMaxLength}"</c:if>><c:if test="${!empty question.answer}"><c:out value="${question.answer}" escapeXml="true"/></c:if></textarea>
                            </c:when>
                            <c:otherwise><spring:message code="UnknownVisualization" text="?UnknownVisualization?"/></c:otherwise>
                        </c:choose>
                        <c:if test="${!empty question.charsLeft}">
                            <span class="smallText"><span id="q_${question.id}_charsLeft">${question.charsLeft}</span> <spring:message code="charsLeft" text="?charsLeft?"/></span>
                        </c:if>
                        <br/>
                        <script type="text/javascript">
                            new Form.Element.Observer('q_${question.id}', 5, function(el, value) {
                                questionFillingABo.updateOpenQuestionAnswer(${question.id}, value);
                            });
                        </script>
                    </div>
                </c:when>
                <c:when test="${question.class.name == 'com.benfante.minimark.po.ClosedQuestionFilling'}">
                    <div id="fa_div_${question.id}" class="fixedAnswers">
                        <c:choose>
                            <c:when test="${question.multipleAnswer}">
                                <c:forEach var="fixedAnswer" items="${question.fixedAnswers}">
                                    <div class="fixedAnswer">
                                        <div>
                                            <input type="checkbox" id="fa_${fixedAnswer.id}" name="q_${question.id}_${fixedAnswer.id}" value="${fixedAnswer.id}" <c:if test="${fixedAnswer.selected}">checked="checked"</c:if> />
                                        </div>
                                        <div class="fixedAnswerText filteredContent">${fixedAnswer.filteredContent}</div>
                                        <br/>
                                    </div>
                                    <script type="text/javascript">
                                        new Form.Element.Observer('fa_${fixedAnswer.id}', 2, function(el, value) {
                                            questionFillingABo.updateFixedAnswer(${fixedAnswer.id}, value);
                                        });
                                    </script>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="fixedAnswer" items="${question.fixedAnswers}">
                                    <div class="fixedAnswer">
                                        <input type="radio" id="fa_${fixedAnswer.id}" name="q_${question.id}" value="${fixedAnswer.id}" <c:if test="${fixedAnswer.selected}">checked="checked"</c:if>/>
                                        <div class="fixedAnswerText filteredContent">${fixedAnswer.filteredContent}</div>
                                        <br/>
                                    </div>
                                    <script type="text/javascript">
                                        new Form.Element.Observer('fa_${fixedAnswer.id}', 2, function(el, value) {
                                            questionFillingABo.updateFixedAnswer(${fixedAnswer.id}, value);
                                        });
                                    </script>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:otherwise><spring:message code="UnknownQuestionType" text="?UnknownQuestionType?"/></c:otherwise>
            </c:choose>
            <div class="fillingQuestionActions">
                <c:choose>
                    <c:when test="${question.class.name == 'com.benfante.minimark.po.OpenQuestionFilling'}">
                        <a class="action action-clear" href="#" onclick="clearTextAnswer(${question.id}); return false;"><spring:message code='Clear' text='?Clear?'/></a>
                    </c:when>
                    <c:when test="${question.class.name == 'com.benfante.minimark.po.ClosedQuestionFilling'}">
                        <c:choose>
                            <c:when test="${question.multipleAnswer}">
                                <a class="action action-clear" href="#" onclick="clearCheckBoxes(${question.id}); return false;"><spring:message code='Clear' text='?Clear?'/></a>
                            </c:when>
                            <c:otherwise>
                                <a class="action action-clear" href="#" onclick="clearRadioButtons(${question.id}); return false;"><spring:message code='Clear' text='?Clear?'/></a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
            </div>
            <br/>
        </div>
    </c:forEach>
    <div class="formButtons">
        <input type="submit" value="<spring:message code='SubmitAssessment' text='?SubmitAssessment?'/>" class="submit-button"/>
    </div>
</form>

<div id="statusBar">
    <div id="statusBar_content">
        <div id="statusBar_left">
            <div id="statusBar_student">${assessmentFilling.firstName} ${assessmentFilling.lastName} (${assessmentFilling.identifier})</div>
            <div id="statusBar_assessment">${assessmentFilling.assessment.course.name} - ${assessmentFilling.assessment.title}</div>
        </div>
        <div id="statusBar_right">
            <c:if test="${(!empty assessmentFilling.assessment.duration) && (assessmentFilling.assessment.duration != 0)}">
                <div id="statusBar_time"><spring:message code="TimeLeft" text="?TimeLeft?"/>: <span id="timeLeft"></span></div>
            </c:if>
        </div>
    </div>
</div>

<script type="text/javascript">
    function clearRadioButtons(questionId) {
        $$('#fa_div_'+questionId+" input[type=radio]").each(function(rb) {
            rb.checked = false;
        })
    }

    function clearCheckBoxes(questionId) {
        $$('#fa_div_'+questionId+" input[type=checkbox]").each(function(cb) {
            cb.checked = false;
        })
    }

    function clearTextAnswer(questionId) {
        $('q_'+questionId).value = "";
    }

    function initTextareasForMaxlength() {
        $$('textarea').each(function(ta) {
            if (ta.getAttribute('maxlength')) {
                ta.observe('keyup', checkChars);
                ta.observe('keydown', checkChars);
            }
        });
    }

    function checkChars(event) {
        var maxLength = parseInt(this.getAttribute('maxlength'));
        if (this.value.length >= maxLength) {
            if (![Event.KEY_BACKSPACE, Event.KEY_DELETE, Event.KEY_DOWN, Event.KEY_END, Event.KEY_ESC, Event.KEY_HOME, Event.KEY_INSERT, Event.KEY_LEFT, Event.KEY_PAGEDOWN, Event.KEY_PAGEUP, Event.KEY_RIGHT, Event.KEY_TAB, Event.KEY_UP].include(event.keyCode)) {
                event.stop();
                return false;
            }
        }
        return true;
    }

    document.observe('dom:loaded' , function() {
        questionFillingABo.updateTimeLeft();

        initTextareasForMaxlength();

        new PeriodicalExecuter(function(pe) {
            questionFillingABo.updateTimeLeft();
        }, 30);

    });
    
</script>