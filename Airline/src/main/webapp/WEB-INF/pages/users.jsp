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
	<title><fmt:message key="users"/></title>
	<link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
	<script src="<c:url value="/assets/js/jquery-3.2.1.min.js"/>"></script>
	<script src="<c:url value="/assets/js/crew_functions.js"/>"></script>
</head>
<body>
	<jsp:include page="header_fragment.jsp"></jsp:include>
	<div class = "frame frame-single">
	<table border="1" id="usersTable" class = "table" >
	<thead>
		<tr class="head">
			<th onclick="sortTable(0)"><fmt:message key="name"/></th>
			<th onclick="sortTable(1)"><fmt:message key="last_name"/></th>
			<th onChange = "filterRole()">
				<select size = "1" id = "role">
					<option>Role</option>
					<option>user</option>
					<option>dispatcher</option>
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
			<tr onclick = "toLink('/Airline/admin/change_role?id=${user.id}')">
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.role}</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
</body>
</html>