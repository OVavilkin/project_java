<%@ include file="/WEB-INF/include/head.jspf"%>

<%-- set the locale --%>
<fmt:setLocale value="en" scope="session"/>

<%-- load the bundle (by locale) --%>
<fmt:setBundle basename="resources"/>

<%-- set current locale to session --%>
<c:set var="currentLocale" value="en" scope="session"/>

<%-- goto back to the settings--%>
<jsp:forward page="./tours"/>

