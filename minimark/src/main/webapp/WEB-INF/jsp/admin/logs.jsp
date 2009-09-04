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
<%@ include file="../common.jspf" %>
<h1>Logs</h1>
<input type="button" value="Clean Logs" onclick="location.href='logs.html?clean=true'"/>
<input type="button" value="Refresh Page" onclick="location.href='logs.html'"/>

or print a sample message:
<a href="logs.html?test=error">error</a>|
<a href="logs.html?test=warn">warn</a>

<div id="log">
    <code><pre>${log}</pre></code>
</div>
