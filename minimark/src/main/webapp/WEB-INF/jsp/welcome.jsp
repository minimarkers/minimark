<%@ include file="common.jspf" %>
<c:choose>
    <c:when test="${requestScope.lang eq 'it'}">
        Questa &egrave; l'applicazione template per il framework
        <a href="http://www.parancoe.org">Parancoe</a>.<br/>
        <br/>
        Da questo punto di partenza puoi iniziare a costruire la
        tua nuova applicazione, sfruttando tutti i benefici che
        derivano dall'uso di Parancoe.<br/>
        <br/>
        Per maggiori informazioni visita il sito di Parancoe:<br/>
        <br/>
        <a href="http://www.parancoe.org">http://www.parancoe.org</a>.<br/>
    </c:when>
    <c:otherwise>
        This is the template application of the
        <a href="http://www.parancoe.org">Parancoe</a> framework.<br/>
        <br/>
        From this starting point you can build your own application,
        with all benefits of the using of the Parancoe Framework.<br/>
        <br/>
        For more infos, visit the Parancoe framework Web site:<br/>
        <br/>
        <a href="http://www.parancoe.org">http://www.parancoe.org</a>.<br/>
    </c:otherwise>
</c:choose>
