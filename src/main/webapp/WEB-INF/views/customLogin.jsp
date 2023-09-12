<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인 페이지</h1>
	<p><c:out value="${error}" /></p>
	<p><c:out value="${logout}" /></p>
	<form action="/login" method="post">
		<p><input type="text" name="username" /></p>
		<p><input type="password" name="password" /></p>
		<p><input type="checkbox" name="remember-me" />Remember me</p>
		<p><input type="submit" value="로그인" /></p>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</body>
</html>