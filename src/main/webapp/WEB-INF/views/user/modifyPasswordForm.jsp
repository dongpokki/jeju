<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#password_form').submit(function(){
			
			if($('#id').val().trim() == ''){
				alert('아이디를 입력해주세요.');
				$('#id').val('').focus();
				return false;
			}
			
			if($('#origin_passwd').val().trim() == ''){
				alert('현재 비밀번호를 입력해주세요.');
				$('#origin_passwd').val('').focus();
				return false;
			}
			
			if($('#passwd').val().trim() == ''){
				alert('새 비밀번호를 입력해주세요.');
				$('#passwd').val('').focus();
				return false;
			}
			
			if($('#cpasswd').val().trim() == ''){
				alert('새 비밀번호 확인을 입력해주세요.');
				$('#cpasswd').val('').focus();
				return false;
			}
			
			if($('#passwd').val() != $('#cpasswd').val()){
				alert('현재 비밀번호와 새 비밀번호가 일치하지 않습니다.');
				$('#cpasswd').val('').focus();
				return false;
			} 
		}); // end of submit
		
		
		// 새 비밀번호 확인까지 명시 한 후 다시 새비밀번호를 수정하려고 하면 새 비밀번호 확인을 초기화
		$('#passwd').keyup(function(){
			$('#cpasswd').val('');
			$('#message_cpasswd').text('');
		});
		
		
		//새비밀번호와 새 비밀번호 확인 일치 여부 체크 
		$('#cpasswd').keyup(function(){
			if($('#passwd').val() == $('#cpasswd').val()){
				$('#message_cpasswd').text('새비밀번호 일치').css('color','blue');
			}else{
				$('#message_cpasswd').text('');
			}
		});
		
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<!-- 본문 영역 -->
	<div class="container">
		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12 mx-auto">
				<div class="card border-0 shadow rounded-3 my-5">
					<div class="card-body p-4 p-sm-5">
						<h5 class="card-title text-center mb-5 fw-light fs-5">비밀번호 수정</h5>
						<form id="password_form" action="modifyPassword.do" method="post">
							<div class="form-floating mb-3">
								<input type="text" name="id" id="id" class="form-control nonlabel_input" maxlength="12" placeholder="아이디 입력">
							</div>
							<div class="form-floating mb-3">
								<input type="password" name="origin_passwd" id="origin_passwd" class="form-control nonlabel_input" maxlength="12" placeholder="현재 비밀번호 입력">
							</div>
							<div class="form-floating mb-3">
								<input type="password" name="passwd" id="passwd" class="form-control nonlabel_input" maxlength="12" placeholder="새 비밀번호 입력">
							</div>
							<div class="form-floating mb-3">
								<input type="password" name="cpasswd" id="cpasswd" class="form-control nonlabel_input" maxlength="12" placeholder="새 비밀번호 확인">
								<span id="message_cpasswd"></span>
							</div>
							<div class="d-grid gap-2 col-6 mx-auto" style="text-align: center;">
								<input class="btn btn-primary btn-login text-uppercase fw-bold" type="submit" value="비밀번호 수정"> 
								<input class="btn btn-secondary btn-login text-uppercase fw-bold" type="button" value="마이페이지" onclick="location.href='${pageContext.request.contextPath}/user/myPage.do';">
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
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<!-- 푸터 끝 -->
</body>
</html>