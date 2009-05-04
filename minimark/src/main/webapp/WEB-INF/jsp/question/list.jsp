<%@include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="Questions"  text="?Questions?"/></h3>

<p>${course.name}</p>
<p>${course.description}</p>

<div>
    <a class="action-add" href="${cp}/question/create.html"><spring:message code="addQuestion" text="?addQuestion?"/></a>
</div>

<div class="displaytag">
    <display:table  id="curr"  name="questions" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" >
        <display:column property="title" titleKey="Title"/>
        <display:column property="content"  titleKey="Content"/>
        <display:column>
            <a class="action-edit" href="${cp}/question/edit.html?id=${curr.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteQuestion' text='?confirmDeleteQuestion?' var="confirmDeleteQuestionMessage"/>
            <a class="action-delete" href="${cp}/question/delete.html?id=${curr.id}" onclick="return confirm('${confirmDeleteQuestionMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
        </display:column>
    </display:table>
</div>
