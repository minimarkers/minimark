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
<%@ page import="java.io.PrintWriter" %>
<h2>
    <spring:message code="attention"/>
</h2>

<div id="errore">
    <h3>
        <spring:message code="error"  text="?error?"/>
    </h3>

    <p>
        <spring:message code="${requestScope.messageCode}" text="${requestScope.messageCode}"/>
    </p>

    <!--
    <%
        Exception e = (Exception) request.getAttribute("exception");
        if (e != null) {
            e.printStackTrace(new PrintWriter(out));
        }
    %>
    -->
</div>
