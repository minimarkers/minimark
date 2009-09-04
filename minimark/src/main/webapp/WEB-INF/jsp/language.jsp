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
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.parancoe.util.Utils" %>
<%
Map paramMap = request.getParameterMap();
Map tmp = new HashMap(paramMap);
tmp.remove("language");
tmp.put("null", "null");
List<String> paramKeyValuesList = Utils.convertToNameValueList(tmp, true);
String queryString = "?" + StringUtils.join(paramKeyValuesList.iterator(), "&amp;");
%>
<span id="language">
    <!-- lang: ${requestScope.lang} -->
    <!-- locale: ${requestScope.requestContext.locale} -->
    <c:forEach var="supportedLanguage" items="${conf.supportedLanguages}" varStatus="status">
        <a href="<%=queryString%>&amp;language=${supportedLanguage}" title="${supportedLanguage}">
            <c:choose>
                <c:when test="${requestScope.requestContext.locale eq supportedLanguage}">
                    <span class="selected"><spring:message code="${supportedLanguage}" text="?${supportedLanguage}?"/></span>
                </c:when>
                <c:otherwise>
                    <spring:message code="${supportedLanguage}" text="?${supportedLanguage}?"/>                
                </c:otherwise>
            </c:choose>
        </a>
        <c:if test="${status.count % 2 == 0}"><br/></c:if>
    </c:forEach>
</span>
