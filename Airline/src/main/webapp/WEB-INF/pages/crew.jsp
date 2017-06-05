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
	<title><fmt:message key="flight"/></title>
	<link href="<c:url value="/assets/css/style.css"/>" rel="stylesheet">
	<script src="<c:url value="/assets/js/jquery-3.2.1.min.js"/>"></script>
	<script src="<c:url value="/assets/js/crew_functions.js"/>"></script>
</head>
<body>
<jsp:include page="header_fragment.jsp"></jsp:include>

<div class = "outer-div">

<c:if test="${users != null}">
	<div class = "frame users-frame right-float">
	<span><fmt:message key="users"/></span>
	<table border="1" id="usersTable" class = "table" >
	<thead>
		<tr class="head">
			<th onclick="sortTable(0)"><fmt:message key="name"/></th>
			<th onclick="sortTable(1)"><fmt:message key="last_name"/></th>
			<th onChange = "filterRole()">
				<select size = "1" id = "role">
					<option>Role</option>
					<option>pilot</option>
					<option>navigator</option>
					<option>radioman</option>
					<option>stewardess</option>
				</select>
			</th>
		</tr>
	</thead>
	<tbody id = "usersTableBody">
		<c:forEach items="${users}" var="user">
			<tr onclick = "move(this)">
				<td>${user.firstName} <input type = "hidden" name = "userId" value = "${user.id}"/></td>
				<td>${user.lastName}</td>
				<td>${user.role}</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
	</c:if>
	
	<div class = "frame crew-frame">
		<div class = "flight-box">
			<p><span><fmt:message key="departure"/></span> <span>${flight.departure}</span>
			<p><span><fmt:message key="destination"/></span> <span>${flight.destination}</span>
			<p><span><fmt:message key="date"/></span> <span>${flight.date}</span>
		</div>
		<form method="post" class = "crew-form" action = "/Airline/dispatcher/save_crew">
		<input type = "hidden" name = "flightId" value = "${flight.id}"/>
			<span><fmt:message key="crew"/></span>
			<table border="1" id="crewTable" class = "table">
				<thead>
					<tr class="head">
						<th><fmt:message key="name"/></th>
						<th><fmt:message key="last_name"/></th>
						<th><fmt:message key="role"/></th>
					</tr>
				</thead>
				<tbody id = "crewTableBody">
					<c:forEach items="${crew}" var="user">
						<tr onclick = "move(this)">
							<td>${user.firstName} <input type = "hidden" name = "userId" value = "${user.id}"/></td>
							<td>${user.lastName}</td>
							<td>${user.role}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${users != null}">
				<button type="submit" class = "crew-frame-button"><fmt:message key="save"/></button>
			</c:if>
		</form>
	</div>
	
</div>
</body>
</html>