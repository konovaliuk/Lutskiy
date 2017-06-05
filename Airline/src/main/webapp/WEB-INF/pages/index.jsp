<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Airline</title>
	<link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="header_fragment.jsp"></jsp:include>
	<div class = "inline-center">
		<h1><fmt:message key="welcome"/></h1>
	</div>
</body>
</html>