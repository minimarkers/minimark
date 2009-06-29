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
                                <input id="q_${question.id}" type="text" size="80" value="<c:if test="${!empty question.answer}"><c:out value="${question.answer}" escapeXml="true"/></c:if>"/>
                            </c:when>
                            <c:when test="${question.visualization == 'long'}">
                                <textarea id="q_${question.id}" cols="80" rows="5"><c:if test="${!empty question.answer}"><c:out value="${question.answer}" escapeXml="true"/></c:if></textarea>
                            </c:when>
                            <c:otherwise><spring:message code="UnknownVisualization" text="?UnknownVisualization?"/></c:otherwise>
                        </c:choose>
                        <br/>
                        <script type="text/javascript">
                            new Form.Element.Observer('q_${question.id}', 5, function(el, value) {
                                questionFillingABo.updateOpenQuestionAnswer(${question.id}, value);
                            });
                        </script>
                    </div>
                </c:when>
                <c:when test="${question.class.name == 'com.benfante.minimark.po.ClosedQuestionFilling'}">
                    <div class="fixedAnswers">
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
                                        <input type="radio" id="fa_${fixedAnswer.id}" name="q_${question.id}" value="${fixedAnswer.id}" <c:if test="${fixedAnswer.selected}">checked="checked"</c:if> />
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
            <br/>
        </div>
    </c:forEach>
    <div class="formButtons">
        <input type="submit" value="<spring:message code='SubmitAssessment' text='?SubmitAssessment?'/>" class="submit-button"/>
    </div>
</form>

<script type="text/javascript">

</script>