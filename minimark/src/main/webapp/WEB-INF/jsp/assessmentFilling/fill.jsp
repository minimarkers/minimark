<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="Assessment" text="?Assessment?"/></h3>
<div class="summary">
    <p><spring:message code="Course" text="?Course?"/>: ${assessmentFilling.assessment.course.name}</p>
    <p><spring:message code="Title" text="?Title?"/>: ${assessmentFilling.assessment.title}</p>
    <p><spring:message code="Incumbent" text="?Incumbent?"/>: ${assessmentFilling.assessment.course.incumbent}</p>
    <p><spring:message code="Student" text="?Student?"/>: ${assessmentFilling.firstName} ${assessmentFilling.lastName} (${assessmentFilling.identifier})</p>
</div>
<h3><spring:message code="Questions" text="?Questions?"/></h3>
<c:forEach var="question" items="${assessmentFilling.assessment.questions}">
    <h4>
        <c:choose>
            <c:when test="${question.question.class.name == 'com.benfante.minimark.po.OpenQuestion'}">
                <c:choose>
                    <c:when test="${question.question.visualization == 'short'}">
                        <img src="${cp}/images/silk/icons/textfield.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" />
                    </c:when>
                    <c:when test="${question.question.visualization == 'long'}">
                        <img src="${cp}/images/silk/icons/textfield_add.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" />
                    </c:when>
                    <c:otherwise><spring:message code="UnknownVisualization" text="?UnknownVisualization?"/></c:otherwise>
                </c:choose>
            </c:when>
            <c:when test="${question.question.class.name == 'com.benfante.minimark.po.ClosedQuestion'}">
                <c:choose>
                    <c:when test="${question.question.multipleAnswer}">
                        <img src="${cp}/images/silk/icons/text_list_numbers.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" />
                    </c:when>
                    <c:otherwise>
                        <img src="${cp}/images/silk/icons/text_list_bullets.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" />
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise><spring:message code="UnknownQuestionType" text="?UnknownQuestionType?"/></c:otherwise>
        </c:choose>
        ${question.question.title}
    </h4>
    ${question.question.filteredContent}

    <c:choose>
        <c:when test="${question.question.class.name == 'com.benfante.minimark.po.OpenQuestion'}">
            <c:choose>
                <c:when test="${question.question.visualization == 'short'}">
                    <input id="q_${question.question.id}" type="text"/>
                </c:when>
                <c:when test="${question.question.visualization == 'long'}">
                    <textarea id="q_${question.question.id}"></textarea>
                </c:when>
                <c:otherwise><spring:message code="UnknownVisualization" text="?UnknownVisualization?"/></c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${question.question.class.name == 'com.benfante.minimark.po.ClosedQuestion'}">
            <c:choose>
                <c:when test="${question.question.multipleAnswer}">
                    <c:forEach var="fixedAnswer" items="${question.question.fixedAnswers}">
                        <div><input type="checkbox" id="fa_${fixedAnswer.id}" value="ON" style="float: left;" /></div><div>&nbsp;${fixedAnswer.filteredContent}</div><br style="clear: both;"/>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="fixedAnswer" items="${question.question.fixedAnswers}">
                        <div><input type="radio" id="fa_${fixedAnswer.id}" value="ON" style="float: left;" /></div><div>&nbsp;${fixedAnswer.filteredContent}</div><br style="clear: both;"/>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise><spring:message code="UnknownQuestionType" text="?UnknownQuestionType?"/></c:otherwise>
        </c:choose>

</c:forEach>
<form action="${cp}/assessmentFilling/store.html" method="GET">
    <div class="formButtons">
        <input type="submit" value="<spring:message code='Start' text='?Start?'/>" class="submit-button"/>&nbsp;&nbsp;<a href="${cp}" class="action-back"><spring:message code="Back" text="?Back?"/></a>
    </div>
</form>
