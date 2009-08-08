<%@ include file="common.jspf" %>

<h3><spring:message code="About" text="?About?"/></h3>

<p class="about">
    <c:choose>
        <c:when test="${requestScope.lang eq 'it'}">
            Minimark è un applicazione che permette ai docenti di gestire i propri compiti
            in forma elettronica e di farli svolgere ai propri studenti, producendo i
            risultati in forma automatica o semi-automatica.
        </c:when>
        <c:otherwise>
            Minimark is an application for teachers that need to manage their assessmets
            in electronic form. Students can submit assessments and the results are
            produced in an automatic or semi-automatic manner.
        </c:otherwise>
    </c:choose>
</p>

<h3><spring:message code="MainFeatures" text="?MainFeatures?"/></h3>

<c:choose>
    <c:when test="${requestScope.lang eq 'it'}">
        <ul>
            <li>Compilazione dei compiti con salvataggio immediato delle risposte</li>
            <li>Monitoraggio della compilazione dei compiti durante lo svolgimento</li>
            <li>Stampa dei compiti in PDF, sia singolarmente, sia complessivamente in ordine alfabetico</li>
            <li>Personalizzazione della valutazione dei compiti</li>
            <li>Produzione dei risultati in formato Excel</li>
            <li>Protezione dei compiti tramite password individuale, per permettere la compilazione di un compito ad un gruppo selezionato di studenti</li>
        </ul>
    </c:when>
    <c:otherwise>
        <ul>
            <li>Immediate saving of responses during the filling of an assessment</li>
            <li>Monitoring of the filling of assessments</li>
            <li>Print of the assessments in PDF, both of a single assessments, and of all assessments, alphabetically ordered</li>
            <li>Customization of the assessment evaluation</li>
            <li>Building of results in Excel format</li>
            <li>Assessment protection by a password, for allowing the filling of an assessment to a selected group of students</li>
        </ul>
    </c:otherwise>
</c:choose>
