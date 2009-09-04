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
<h3>Users</h3>

<div>
    <a class="action action-add" href="create.html"><spring:message code="AddUser" text="?AddUser?"/></a>
</div>

<div class="displaytag">
    <display:table  id="user"  name="users" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" >
        <display:column property="user.username" titleKey="Username" class="left-aligned"/>
        <display:column property="name" titleKey="Name" class="left-aligned"/>
        <display:column property="email" titleKey="Email" class="left-aligned" autolink="true"/>
        <display:column property="site" titleKey="Site" class="left-aligned" autolink="true"/>
        <display:column>
            <a class="action action-edit" href="edit.form?id=${user.user.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteUser' text='?confirmDeleteUser?' var="confirmDeleteUserMessage" arguments="${user.user.username}" javaScriptEscape="true"/>
            <a class="action action-delete" href="delete.html?id=${user.user.id}" onclick="return confirm('${confirmDeleteUserMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
        </display:column>
    </display:table>
</div>
