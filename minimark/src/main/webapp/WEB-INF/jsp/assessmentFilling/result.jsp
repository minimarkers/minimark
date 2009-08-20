<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="Assessment" text="?Assessment?"/></h3>
<div class="summary">
    <p><spring:message code="Course" text="?Course?"/>: ${assessmentFilling.assessment.course.name}</p>
    <p><spring:message code="Title" text="?Title?"/>: ${assessmentFilling.assessment.title}</p>
    <p><spring:message code="Incumbent" text="?Incumbent?"/>: ${assessmentFilling.assessment.course.incumbent}</p>
    <p><spring:message code="Student" text="?Student?"/>: ${assessmentFilling.firstName} ${assessmentFilling.lastName} (${assessmentFilling.identifier})</p>
</div>
<h3><spring:message code="Result" text="?Result?"/></h3>
<div class="result">
    <p><spring:message code="AssessmentSentMessage" text="?AssessmentSentMessage?"/></p>
    <c:if test="${!empty exposedResult}">
        <spring:message code="ResultMessage" text="?ResultMessage?"/>: <strong>${exposedResult}</strong>
    </c:if>
    <c:if test="${assessmentFilling.assessment.allowStudentPrint}">
        <p><a href="pdf.html?id=${assessmentFilling.id}" class="action action-pdf-document"><spring:message code="PrintYourAssessment" text="?PrintYourAssessment?"/></a></p>
    </c:if>
</div>
