<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${auth == 1}">
		<script type="text/javascript">
			alert('회원님의 계정이 정지되었습니다.');
			location.href='loginForm.do';
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			alert('아이디 또는 비밀번호가 불일치 합니다.');
			history.go(-1);
		</script>
	</c:otherwise>
</c:choose>