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
<ul id="nav">
    <li><a href="${cp}/"><spring:message code="menu_home"/></a></li>
        <authz:authorize ifAnyGranted="ROLE_ADMIN">
        <li><a href="${cp}/admin/index.html"><spring:message code="menu_administration" text="?menu_administration?"/></a></li>
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_PARANCOE">
        <li><a href="${cp}/course/list.html"><spring:message code="menu_courses" text="?menu_courses?"/></a></li>
        <li><a href="${cp}/assessment/list.html"><spring:message code="menu_assessments" text="?menu_assessments?"/></a></li>
        <li><a href="${cp}/monitoring/fillings.html"><spring:message code="menu_monitoring" text="?menu_monitoring?"/></a></li>
        </authz:authorize>
</ul>
