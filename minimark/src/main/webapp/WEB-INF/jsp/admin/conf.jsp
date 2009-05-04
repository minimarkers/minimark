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
