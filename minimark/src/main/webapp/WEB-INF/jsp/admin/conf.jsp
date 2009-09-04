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
<%@ page import="org.parancoe.util.MemoryAppender" %>
<%@ page import="org.apache.commons.configuration.Configuration" %>
<%@ page import="java.util.Iterator" %>
<%@ include file="../common.jspf" %>
<h1>Configuration: <%= BaseConf.getEnv()%></h1>

<table>
    <tr>
        <th>key</th>
        <th>value</th>
    </tr>

    <% Configuration c = conf.getConfiguration();
    Iterator i = c.getKeys();
    while (i.hasNext()) {
        String key = (String) i.next();
        Object value = conf.getConfiguration().getProperty(key);
    %>
    <tr>
        <td><%=key%></td>
        <td><%=value%></td>
    </tr>
    <% }%>
</table>
