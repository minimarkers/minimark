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
<%@ include file="common.jspf" %>
<div style="float: right; height: auto!important; min-height: 79px;">
    <div id="languages"><jsp:include page="language.jsp"/></div>
</div>
<h1 id="logo"><a href="${cp}/">MiniMark</a></h1>
<div style="clear: both; height: auto!important; min-height: 24px;">
    <h3 style="float: left;"><spring:message code="slogan" text="?slogan?"/></h3>
    <div id="profile">
        <authz:authorize ifNotGranted="ROLE_ADMIN,ROLE_PARANCOE">
        <a href="${cp}/login.secure"><spring:message code="Login" text="?Login?"/></a>
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_PARANCOE,ROLE_ADMIN">
            <span class="strong">${userProfile.name}</span><br/>
            <span class="smallText"><a href="${cp}/profile/edit.html"><spring:message code="YourProfile" text="?YourProfile?"/></a></span> |
            <span class="smallText"><a href="${cp}/logout.secure"><spring:message code="Logout" text="?Logout?"/></a></span>
        </authz:authorize>
    </div>
</div>
<div style="clear: both; line-height: 1px; font-size: 1px;">&nbsp;</div>