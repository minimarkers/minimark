<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3>Users</h3>

<div>
    <a class="action-add" href="create.html"><spring:message code="AddUser" text="?AddUser?"/></a>
</div>

<div class="displaytag">
    <display:table  id="user"  name="users" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" >
        <display:column property="user.username" titleKey="Username" class="left-aligned"/>
        <display:column property="name" titleKey="Name" class="left-aligned"/>
        <display:column property="email" titleKey="Email" class="left-aligned" autolink="true"/>
        <display:column property="site" titleKey="Site" class="left-aligned" autolink="true"/>
        <display:column>
            <a class="action-edit" href="edit.form?id=${user.user.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteUser' text='?confirmDeleteUser?' var="confirmDeleteUserMessage" arguments="${user.user.username}" javaScriptEscape="true"/>
            <a class="action-delete" href="delete.html?id=${user.user.id}" onclick="return confirm('${confirmDeleteUserMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
        </display:column>
    </display:table>
</div>
