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
