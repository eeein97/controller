<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입 페이지 입니다</h1>
	<form action="/register_member" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<p>이름 :   <input type="text" name="userName" /></p>
		<p>아이디 :  <input type="text" name="userid"  /></p>
		<p>비밀번호 : <input type="password" name="userpw"/></p>
		<p>회원등급 : <select name="auth">
				<option value="ROLE_USER" selected>일반회원</option>
				<option value="ROLE_MEMBER" >관리자회원</option>
				<option value="ROLE_ADMIN" >운영자회원</option>
			</select></p>
		<p><input type="submit" value="등록" /></p>
		<p><input type="reset" value="취소" /></p>
	</form>
</body>
</html>