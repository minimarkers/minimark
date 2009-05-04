<%@ page import="org.parancoe.util.MemoryAppender" %>
<%@ page import="org.apache.commons.configuration.Configuration" %>
<%@ page import="java.util.Iterator" %>
<%@ include file="../common.jspf" %>
<h1>Spring Beans</h1>

<ul>
    <%  String[] names = ctx.getBeanDefinitionNames();
for (int i = 0; i < names.length; i++) {
    String name = names[i];
    %> <li><%= name%></li>
    <% }%>
</ul>
