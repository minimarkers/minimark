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
<spring:message code="LastName" text="?LastName?" var="LastNameTitle"/>
<spring:message code="FirstName" text="?FirstName?" var="FirstNameTitle"/>
<spring:message code="StudentIdentifier" text="?StudentIdentifier?" var="IdentifierTitle"/>
<spring:message code="Submitted" text="?Submitted?" var="SubmittedTitle"/>
<display:table id="curr"  name="fillings">
    <display:column property="lastName" title="${LastNameTitle}"/>
    <display:column property="firstName" title="${FirstNameTitle}"/>
    <display:column property="identifier"  title="${IdentifierTitle}"/>
    <display:column title="${SubmittedTitle}">
        <fmt:formatDate value="${curr.submittedDate}" type="both" dateStyle="short"/>
    </display:column>
    <display:column>
        <a class="action-pdf-document" href="pdf.html?id=${curr.id}" title="PDF"></a>
    </display:column>
</display:table>
