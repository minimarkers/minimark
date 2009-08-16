<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3>
    <c:choose>
        <c:when test="${empty question.id}"><spring:message code="CreateQuestion" text="?CreateQuestion?"/></c:when>
        <c:otherwise><spring:message code="EditQuestion" text="?EditQuestion?"/></c:otherwise>
    </c:choose>
</h3>
<c:set value="${!empty question.id}" var="disabledTypeSelect"/>
<form:form commandName="question" method="POST" action="${cp}/question/save.html">
    <form:hidden path="id"/>
    <form:errors path="type" cssClass="errorBox"/>
    <form:label path="type"><spring:message code="Type" text="?Type?"/>:</form:label>
    <spring:message code='OpenQuestion' text='?OpenQuestion?' var="OpenQuestionLabel"/>
    <spring:message code='ClosedQuestion' text='?ClosedQuestion?' var="ClosedQuestionLabel"/>
    <form:select path="type" disabled="${disabledTypeSelect}" onchange="showQuestionFields();">
        <form:option value="open" label="${OpenQuestionLabel}"/>
        <form:option value="closed" label="${ClosedQuestionLabel}"/>
    </form:select><br/>
    <form:errors path="title" cssClass="errorBox"/>
    <form:label path="title"><spring:message code="Title" text="?Title?"/>:</form:label>
    <form:input path="title" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="contentFilter" cssClass="errorBox"/>
    <form:label path="contentFilter"><spring:message code="ContentFilter" text="?ContentFilter?"/>:</form:label>
    <spring:message code='Textile' text='?Textile?' var="TextileLabel"/>
    <spring:message code='Html' text='?Html?' var="HtmlLabel"/>
    <form:select path="contentFilter">
        <form:option value="textile" label="${TextileLabel}"/>
        <form:option value="html" label="${HtmlLabel}"/>
    </form:select><br/>
    <form:errors path="content" cssClass="errorBox"/>
    <form:label path="content"><spring:message code="Content" text="?Content?"/>:</form:label><form:textarea path="content" id="questionContent" cols="25" rows="5" cssErrorClass="fieldInError"/>&nbsp;*<br/>
    <div id="openFields">
        <form:errors path="visualization" cssClass="errorBox"/>
        <form:label path="visualization"><spring:message code="Visualization" text="?Visualization?"/>:</form:label>
        <form:select path="visualization">
            <spring:message code='ShortVisualization' text='?ShortVisualization?' var="ShortVisualizationLabel"/>
            <form:option value="short" label="${ShortVisualizationLabel}"/>
            <spring:message code='LongVisualization' text='?LongVisualization?' var="LongVisualizationLabel"/>
            <form:option value="long" label="${LongVisualizationLabel}"/>
        </form:select><br/>
        <form:errors path="answerMaxLength" cssClass="errorBox"/>
        <form:label path="answerMaxLength"><spring:message code="MaxLength" text="?MaxLength?"/>:</form:label>
        <form:input path="answerMaxLength" maxlength="5" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    </div>
    <form:errors path="weight" cssClass="errorBox"/>
    <form:label path="weight"><spring:message code="Weight" text="?Weight?"/>:</form:label>
    <form:input path="weight" maxlength="5" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="tags" cssClass="errorBox"/>
    <form:label path="tags"><spring:message code="Tags" text="?Tags?"/>:</form:label>
    <form:input path="tags" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <div class="form-aligned form-help-text"><span><spring:message code="help.tagListField" text="?tagListField?"/></span></div>
    <div id="fixedAnswers">
        <%@include file="/WEB-INF/jsp/question/fixedAnswers.jsp" %>
    </div>
    <div class="formButtons">
        <a href="list.html?course.id=${question.course.id}" class="action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>
    </div>
</form:form>

<script type="text/javascript">
    function showQuestionFields() {
        hideVariableQuestionFields();
        if ($('type').value == 'open') {
            $('openFields').show();
        } else if ($('type').value == 'closed') {
            $('fixedAnswers').show();
        }
    }

    function hideVariableQuestionFields() {
        $('openFields').hide();
        $('fixedAnswers').hide();
    }

    dwr.util.setEscapeHtml(false);

    showQuestionFields();
</script>
