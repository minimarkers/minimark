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
</div>