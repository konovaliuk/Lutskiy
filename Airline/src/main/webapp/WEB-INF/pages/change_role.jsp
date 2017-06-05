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
	<title><fmt:message key="user"/></title>
	<link href="<c:url value="/assets/css/style.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="header_fragment.jsp"></jsp:include>

<div class = "user-outer">
	<form method="post" action = "/Airline/admin/update_role">
		<input type = "hidden" name = "userId" value = "${user.id}"/>
		<p><span><fmt:message key="name"/></span> <span>${user.firstName}</span>
		<p><span><fmt:message key="last_name"/></span> <span>${user.lastName}</span>
		<p><span><fmt:message key="email"/></span> <span>${user.email}</span>
		<p><span><fmt:message key="role"/></span> <select size = "1" name="role">
				<option <c:if test ="${user.role =='user'}"> selected</c:if> >user</option>
				<option <c:if test ="${user.role =='dispatcher'}"> selected</c:if> >dispatcher</option>
				<option <c:if test ="${user.role =='pilot'}"> selected</c:if> >pilot</option>
				<option <c:if test ="${user.role =='navigator'}"> selected</c:if> >navigator</option>
				<option <c:if test ="${user.role =='radioman'}"> selected</c:if> >radioman</option>
				<option <c:if test ="${user.role =='stewardess'}"> selected</c:if> >stewardess</option>
			</select>
		<button type="submit"><fmt:message key="save"/></button>
	</form>
</div>

</body>
</html>