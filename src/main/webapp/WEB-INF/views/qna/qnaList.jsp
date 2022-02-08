<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
</head>
<body>
<div>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="container mt-5">
			<div class="page-banner" style="height:50px;background-color:#fff">
				<h1>QnA</h1>
				<input class="btn btn-outline" type="button" value="글쓰기" onclick="location.href='qnaWriteForm.do'">
			</div>
		</div>
		
	</div>
</div>
</body>
</html>