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
<h3><spring:message code="ImportQuestionInCourse" text="?ImportQuestionInCourse?"/></h3>
<p><spring:message code="ImportQuestionInCourseMessage" text="?ImportQuestionInCourseMessage?" arguments="${course.name}" argumentSeparator="|"/></p>
<form:form commandName="importQuestions" method="post" action="${cp}/course/doImportQuestions.html" enctype="multipart/form-data">
    <form:errors path="importFile" cssClass="errorBox"/>
    <form:label path="importFile"><spring:message code="File" text="?File?"/>:</form:label><input type="file" name="importFile" id="importFile" class="full-size"/>&nbsp;*<br/>
    <div class="form-aligned form-help-text"><span><spring:message code="help.importQuestionsExample" text="?help.importQuestionsExample?" arguments="${cp}/files/questionFileExample.txt"/></span></div>
    <div class="formButtons">
        <a href="list.html" class="action action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Import' text='?Import?'/>" class="submit-button"/>
    </div>
</form:form>
