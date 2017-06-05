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
	<title><fmt:message key="login"/></title>
	<link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="header_fragment.jsp"></jsp:include>
	<form class = "form" method="post" action = "/Airline/registration">
        <section class="container one">
          <p class = "center"><label for="first_name"><fmt:message key="name"/></label>
            <input type="text" name="first_name" pattern="^[а-яА-ЯёЁіІїЇa-zA-Z\s]+$" required 
            	value="${first_name}"><span></span>
          </p>
          
          <p class = "center"><label for="last_name"><fmt:message key="last_name"/></label>
            <input type="text" name="last_name" pattern="^[а-яА-ЯёЁіІїЇa-zA-Z\s]+$" required 
            	value="${last_name}"><span></span>
          </p>
          
          <p class = "center"><label for="email"><fmt:message key="email"/></label>
            <input type="email" name="email" pattern="^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$" required 
            	value="${email}"><span></span>
          </p>
          
          <p class = "center"><label for="password"><fmt:message key="password"/></label>
            <input type="password" name="password" pattern="^[а-яА-ЯёЁіІїЇa-zA-Z0-9]+$" required oninput="check()"><span></span>
          </p>
          
          <p class = "center"><label for="password_confirm"><fmt:message key="repeat"/></label>
            <input type="password" name="password_confirm" required oninput="check()"><span></span>
          </p>
          	<script type='text/javascript'>
   		 	function check() {
   		 		var input = document.getElementsByName("password_confirm")[0];
       			if (document.getElementsByName("password")[0].value != input.value) {
            		input.setCustomValidity('Password Must be Matching.');
        		} else {
            	// input is valid -- reset the error message
            	input.setCustomValidity('');
        		}
    		}
			</script>
			<p class = "red">${error_message}</p>
        </section>
        
        <section class="container two">
        <button type="submit"><fmt:message key="sign_up"/></button>
        </section>
      </form>
</body>
</html>