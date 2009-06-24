<%@include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="YourCourses"  text="?YourCourses?"/></h3>

<div>
    <a class="action-add" href="${cp}/course/create.html"><spring:message code="addCourse" text="?addCourse?"/></a>
</div>

<div class="displaytag">
    <display:table  id="cc"  name="courses" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" >
        <display:column property="name" titleKey="Name"/>
        <display:column property="description"  titleKey="Description"/>
        <display:column property="mainGroup"  titleKey="MainGroup"/>
        <display:column property="secondaryGroup"  titleKey="SecondaryGroup"/>
        <display:column>
            <a class="action-edit" href="${cp}/course/edit.html?id=${cc.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteCourse' text='?confirmDeleteCourse?' var="confirmDeleteCourseMessage"/>
            <a class="action-delete" href="${cp}/course/delete.html?id=${cc.id}" onclick="return confirm('${confirmDeleteCourseMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
            <a class="action-detail-go" href="${cp}/question/list.html?courseId=${cc.id}"><spring:message code="Questions" text="?Questions?"/></a>
            <a class="action-detail-go" href="${cp}/course/importQuestions.html?courseId=${cc.id}"><spring:message code="ImportQuestions" text="?ImportQuestions?"/></a>
        </display:column>
    </display:table>
</div>
