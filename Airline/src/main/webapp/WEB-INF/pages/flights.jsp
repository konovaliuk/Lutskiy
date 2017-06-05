<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/currentDate.tld' prefix = 'tag'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="element/html; charset=UTF-8">
<title><fmt:message key="flights"/></title>
<link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="header_fragment.jsp"></jsp:include>
	<div class = "flight_menu menu">
		<div>
			
			<c:if test = "${user.role == 'admin'}">
				<a href="/Airline/admin/new_flight" class = "right-float"><fmt:message key="new_flight"/></a>
			</c:if>
			
			<form method="post" class = "form-string" action = "/Airline/flights_interval">
				<button type="submit" class = "btn-link"><fmt:message key="apply_interval"/></button>
				<input type="date" name="date_start" value = "${start}" required><span> - </span>
				<input type="date" name="date_end" value = "${end}" required>
			</form>
		</div>
	</div>
	<tag:currentDate var="currentDate"/>
	<c:forEach items="${flights}" var="flight">
		<div class = "flight">
			<div>
				<span>${flight.departure}</span>
			</div>
			<div>
				<div class = "btn_block">
					<c:if test = "${user.role == 'admin' && flight.date >= currentDate}">
						<a href="/Airline/admin/edit_flight?id=${flight.id}" class = "button"><fmt:message key="edit"/></a>
					</c:if>
					<c:if test = "${user.role == 'dispatcher'}">
						<c:if test = "${flight.date >= currentDate}">
							<a href="/Airline/dispatcher/edit_crew?id=${flight.id}" class = "button"><fmt:message key="edit"/></a>
						</c:if>
						<c:if test = "${flight.date < currentDate}">
							<a href="/Airline/dispatcher/view_crew?id=${flight.id}" class = "button"><fmt:message key="view"/></a>
						</c:if>
					</c:if>
				</div>
				<div class = "date">
					${flight.date}
				</div>
			</div>
			<div>
				<span>${flight.destination}</span>
			</div>
		</div>
	</c:forEach>
</body>
</html>