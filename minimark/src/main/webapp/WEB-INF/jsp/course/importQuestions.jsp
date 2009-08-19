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
