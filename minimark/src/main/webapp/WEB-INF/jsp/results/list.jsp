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
    <p><spring:message code="Course" text="?Course?"/>: ${assessment.course.name}</p>
    <p><spring:message code="Title" text="?Title?"/>: ${assessment.title}</p>
    <p><spring:message code="Incumbent" text="?Incumbent?"/>: ${assessment.course.incumbent}</p>
</div>
<h3><spring:message code="Results" text="?Results?"/></h3>
<a class="action action-pdf-document" href="pdfs.html?id=${assessment.id}" title="PDF"><spring:message code="AllAssessments" text="?AllAssessments?"/></a>
<a class="action action-xls-document" href="xls.html?id=${assessment.id}" title="Excel"><spring:message code="ExcelResults" text="?ExcelResults?"/></a>
<a class="action action-run" href="evaluateAll.html?id=${assessment.id}" title="Evaluation"><spring:message code="EvaluateAll" text="?EvaluateAll?"/></a>
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
    <display:column style="white-space: nowrap;">
        <a class="action action-no-text action-pdf-document" href="pdf.html?id=${curr.id}" title="PDF"></a>
        <a class="action action-no-text action-run" href="evaluate.html?id=${curr.id}" title="<spring:message code='Evaluate' text='?Evaluate?'/>"></a>
        <spring:message code='confirmDeleteAssessment' text='?confirmDeleteAssessment?' var="confirmDeleteAssessmentMessage"/>
        <a class="action action-no-text action-delete" href="delete.html?id=${curr.id}" title="<spring:message code='Delete' text='?Delete?'/>" onclick="return confirm('${confirmDeleteAssessmentMessage}')"></a>
    </display:column>
</display:table>
