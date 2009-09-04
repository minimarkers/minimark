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
<%@include file="/WEB-INF/jsp/common.jspf" %>
<div class="summary">
    <p>${course.name}</p>
    <p>${course.description}</p>
</div>
<h3><spring:message code="Questions"  text="?Questions?"/></h3>

<form:form commandName="questionSearch" method="get" action="search.html">
    <form:hidden path="course.id"/>
    <form:errors path="type" cssClass="errorBox"/>
    <form:label path="type"><spring:message code="Type" text="?Type?"/>:</form:label>
    <spring:message code='OpenQuestion' text='?OpenQuestion?' var="OpenQuestionLabel"/>
    <spring:message code='ClosedQuestion' text='?ClosedQuestion?' var="ClosedQuestionLabel"/>
    <form:select path="type" onchange="showQuestionFields();">
        <form:option value="" label=""/>
        <form:option value="open" label="${OpenQuestionLabel}"/>
        <form:option value="closed" label="${ClosedQuestionLabel}"/>
    </form:select><br/>
    <form:errors path="title" cssClass="errorBox"/>
    <form:label path="title"><spring:message code="Title" text="?Title?"/>:</form:label>
    <form:input path="title" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div id="openFields">
        <form:errors path="visualization" cssClass="errorBox"/>
        <form:label path="visualization"><spring:message code="Visualization" text="?Visualization?"/>:</form:label>
        <form:select path="visualization">
            <form:option value="" label=""/>
            <spring:message code='ShortVisualization' text='?ShortVisualization?' var="ShortVisualizationLabel"/>
            <form:option value="short" label="${ShortVisualizationLabel}"/>
            <spring:message code='LongVisualization' text='?LongVisualization?' var="LongVisualizationLabel"/>
            <form:option value="long" label="${LongVisualizationLabel}"/>
        </form:select><br/>
    </div>
    <form:errors path="weight" cssClass="errorBox"/>
    <form:label path="weight"><spring:message code="Weight" text="?Weight?"/>:</form:label>
    <form:input path="weight" maxlength="5" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="tags" cssClass="errorBox"/>
    <form:label path="tags"><spring:message code="Tags" text="?Tags?"/>:</form:label>
    <form:input path="tags" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="form-aligned form-help-text"><span><spring:message code="help.tagListField" text="?tagListField?"/></span></div>
    <div class="formButtons">
        <a href="${cp}/course/list.html" class="action action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Search' text='?Search?'/>" class="submit-button"/>
    </div>
</form:form>

<div>
    <a class="action action-add" href="${cp}/question/create.html?courseId=${course.id}"><spring:message code="addQuestion" text="?addQuestion?"/></a>
</div>

<div class="displaytag">
    <display:table  id="curr"  name="questionSearchResult" pagesize="20" requestURI="${cp}/question/search.html">
        <display:column titleKey="Type">
            <c:choose>
                <c:when test="${curr.class.name == 'com.benfante.minimark.po.OpenQuestion'}">                    
                    <c:choose>
                        <c:when test="${curr.visualization == 'short'}">
                            <img src="${cp}/images/silk/icons/textfield.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" />
                        </c:when>
                        <c:when test="${curr.visualization == 'long'}">
                            <img src="${cp}/images/silk/icons/textfield_add.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" />
                        </c:when>
                        <c:otherwise><spring:message code="UnknownVisualization" text="?UnknownVisualization?"/></c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${curr.class.name == 'com.benfante.minimark.po.ClosedQuestion'}">
                    <c:choose>
                        <c:when test="${curr.multipleAnswer}">
                            <img src="${cp}/images/silk/icons/text_list_numbers.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" />
                        </c:when>
                        <c:otherwise>
                            <img src="${cp}/images/silk/icons/text_list_bullets.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise><spring:message code="UnknownQuestionType" text="?UnknownQuestionType?"/></c:otherwise>
            </c:choose>
        </display:column>
        <display:column property="title" titleKey="Title" class="left-aligned"/>
        <display:column property="filteredContent"  titleKey="Content" class="left-aligned"/>
        <display:column property="weight"  titleKey="Weight"/>
        <display:column property="tagList"  titleKey="Tags" class="left-aligned"/>
        <display:column>
            <a class="action action-edit" href="${cp}/question/edit.html?id=${curr.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteQuestion' text='?confirmDeleteQuestion?' var="confirmDeleteQuestionMessage"/>
            <a class="action action-delete" href="${cp}/question/delete.html?id=${curr.id}" onclick="return confirm('${confirmDeleteQuestionMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
        </display:column>
    </display:table>
</div>

<script type="text/javascript">

    function showQuestionFields() {
        hideVariableQuestionFields();
        if ($('type').value == 'open') {
            $('openFields').show();
        } else if ($('type').value == 'closed') {
            //            $('fixedAnswers').show();
        }
    }

    function hideVariableQuestionFields() {
        $('openFields').hide();
        //        $('fixedAnswers').hide();
    }

    showQuestionFields();

</script>