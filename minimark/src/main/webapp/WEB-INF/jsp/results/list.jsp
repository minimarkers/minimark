<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="Assessment" text="?Assessment?"/></h3>
<div class="summary">
    <p><spring:message code="Course" text="?Course?"/>: ${assessment.course.name}</p>
    <p><spring:message code="Title" text="?Title?"/>: ${assessment.title}</p>
    <p><spring:message code="Incumbent" text="?Incumbent?"/>: ${assessment.course.incumbent}</p>
</div>
<h3><spring:message code="Results" text="?Results?"/></h3>
<a class="action-pdf-document" href="pdfs.html?id=${assessment.id}" title="PDF"><spring:message code="AllAssessments" text="?AllAssessments?"/></a>
<a class="action-xls-document" href="xls.html?id=${assessment.id}" title="Excel"><spring:message code="ExcelResults" text="?ExcelResults?"/></a>
<a class="action-run" href="evaluateAll.html?id=${assessment.id}" title="Evaluation"><spring:message code="EvaluateAll" text="?EvaluateAll?"/></a>
<spring:message code="LastName" text="?LastName?" var="LastNameTitle"/>
<spring:message code="FirstName" text="?FirstName?" var="FirstNameTitle"/>
<spring:message code="StudentIdentifier" text="?StudentIdentifier?" var="IdentifierTitle"/>
<spring:message code="Submitted" text="?Submitted?" var="SubmittedTitle"/>
<spring:message code="Result" text="?Result?" var="ResultTitle"/>
<display:table id="curr"  name="fillings">
    <display:column property="lastName" title="${LastNameTitle}" class="left-aligned"/>
    <display:column property="firstName" title="${FirstNameTitle}" class="left-aligned"/>
    <display:column property="identifier"  title="${IdentifierTitle}" class="left-aligned"/>
    <display:column title="${SubmittedTitle}">
        <fmt:formatDate value="${curr.submittedDate}" type="both" dateStyle="short"/>
    </display:column>
    <display:column property="evaluationResult"  title="${ResultTitle}"/>
    <display:column>
        <a class="action-pdf-document" href="pdf.html?id=${curr.id}" title="PDF"></a>
        <a class="action-run" href="evaluate.html?id=${curr.id}" title="<spring:message code='Evaluate' text='?Evaluate?'/>"></a>
        <spring:message code='confirmDeleteAssessment' text='?confirmDeleteAssessment?' var="confirmDeleteAssessmentMessage"/>
        <a class="action-delete" href="delete.html?id=${curr.id}" title="<spring:message code='Delete' text='?Delete?'/>" onclick="return confirm('${confirmDeleteAssessmentMessage}')"></a>
    </display:column>
</display:table>
