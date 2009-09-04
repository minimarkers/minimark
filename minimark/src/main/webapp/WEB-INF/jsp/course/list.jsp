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
<h3><spring:message code="YourCourses"  text="?YourCourses?"/></h3>

<div>
    <a class="action action-add" href="${cp}/course/create.html"><spring:message code="addCourse" text="?addCourse?"/></a>
</div>

<div class="displaytag">
    <display:table  id="cc"  name="courses" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" >
        <display:column property="name" titleKey="Name" class="left-aligned"/>
        <display:column property="description"  titleKey="Description" class="left-aligned"/>
        <display:column property="mainGroup"  titleKey="MainGroup" class="left-aligned"/>
        <display:column property="secondaryGroup"  titleKey="SecondaryGroup" class="left-aligned"/>
        <display:column>
            <a class="action action-edit" href="${cp}/course/edit.html?id=${cc.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteCourse' text='?confirmDeleteCourse?' var="confirmDeleteCourseMessage"/>
            <a class="action action-delete" href="${cp}/course/delete.html?id=${cc.id}" onclick="return confirm('${confirmDeleteCourseMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
            <a class="action action-detail-go" href="${cp}/question/list.html?course.id=${cc.id}"><spring:message code="Questions" text="?Questions?"/></a>
            <a class="action action-detail-go" href="${cp}/course/importQuestions.html?courseId=${cc.id}"><spring:message code="ImportQuestions" text="?ImportQuestions?"/></a>
            <a class="action action-add" href="${cp}/assessment/create.html?courseId=${cc.id}"><spring:message code="addAssessment" text="?addAssessment?"/></a>
            <a class="action action-add" href="${cp}/assessment/template/create.html?course.id=${cc.id}"><spring:message code="addAssessmentFromTemplate" text="?addAssessmentFromTemplate?"/></a>
        </display:column>
    </display:table>
</div>
