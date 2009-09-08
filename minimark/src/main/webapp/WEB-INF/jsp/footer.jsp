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
<%@ include file="common.jspf"%>
<div id="footer">
    <div id="validators">&nbsp;</div>
    <div id="copyright">&#169; 2007, ${url}<br/><a href="mailto:info@mycompany.com">info@mycompany.com</a></div>
    <div id="references"><a href="http://wwww.parancoe.org"><img src="${cp}/images/powered_parancoe.png" alt="Parancoe powered" style="border: 0"/></a></div>
</div>

<% if (!BaseConf.isProduction()) { %>
<jsp:include page="debug.jsp" />
<% } %>