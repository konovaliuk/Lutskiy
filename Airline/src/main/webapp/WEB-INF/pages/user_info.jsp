<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="element/html; charset=UTF-8">
	<title><fmt:message key="my_account"/></title>
	<link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="header_fragment.jsp"></jsp:include>
	
	<div class = "user-outer user-info left-float">
		<p><span><fmt:message key="name"/></span> <span>${user.firstName}</span>
		<p><span><fmt:message key="last_name"/></span> <span>${user.lastName}</span>
		<p><span><fmt:message key="email"/></span> <span>${user.email}</span>
		<p><span><fmt:message key="role"/></span> <span>${user.role}</span>
	</div>
	
	<c:if test="${flights != null}">
		<div class = "user-outer flight-outer right-float">
			<span><fmt:message key="my_flights"/></span>
			<c:forEach items="${flights}" var="flight">
				<p><span>${flight.departure} - ${flight.date} - ${flight.destination}</span>
			</c:forEach>
			<p><span></span>
		</div>
	</c:if>
</body>
</html>