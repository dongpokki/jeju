<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#login_form').submit(function(){
			if($('#id').val().trim() == ''){
				alert('아이디를 입력하세요');
				$('#id').val('').focus();
				return false;
			}
			if($('#passwd').val().trim() ==''){
				alert('비밀번호를 입력하세요');
				$('#passwd').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
	<!-- 헤더 시작 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<!-- 헤더 끝 -->

	<!-- 본문 영역 -->
	<div class="container">
		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12 mx-auto">
				<div class="card border-0 shadow rounded-3 my-5">
					<div class="card-body p-4 p-sm-5">
						<h5 class="card-title text-center mb-5 fw-light fs-5">Login</h5>
						<form id="login_form" action="login.do" method="post">
							<div class="form-floating mb-3">
								<input type="text" name="id" id="id" maxlength="12" class="form-control" placeholder="아이디 입력">
							</div>
							<div class="form-floating mb-3">
								<input type="password" name="passwd" id="passwd" class="form-control" maxlength="12" placeholder="비밀번호 입력">
							</div>
							<div class="d-grid gap-2 col-6 mx-auto" style="text-align: center;">
								<input class="btn btn-primary btn-login text-uppercase fw-bold" type="submit" value="로그인"> 
								<input class="btn btn-secondary btn-login text-uppercase fw-bold" type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do';">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 본문 영역 -->

	<!-- 푸터 시작 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<!-- 푸터 끝 -->
	
</body>
</html>