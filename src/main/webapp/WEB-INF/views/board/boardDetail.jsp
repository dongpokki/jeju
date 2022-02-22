<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board.title}</title>
<link rel="icon" href="${pageContext.request.contextPath}/images/jeju.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board_cmt.js"></script>
</head>
<script>
$(function(){
	let user_num = ${user_num };
	let checked = ${checked};
	$('#good').click(function() {
		 if (user_num == 0) { // 로그인 안 한 상태에서 좋아요 눌렀을 경우
			alert('좋아요는 로그인 한 사용자만 가능합니다.');
			return;
		} 
		if (user_num != 0) {
			$.ajax({
				url : 'boardGood.do',
				type: 'post',
				dataType: 'json',
				data : {board_num: ${board.board_num}, checked :checked},
				success:function(param){
					if (param.result == 'success') { // 좋아요
						$('#good').css('color','#FE9A2E');
						$('#good_result').text(param.good_result);
					}else if (param.result == 'cancel') { // 좋아요 취소
						$('#good').css('color','#000000');
						$('#good_result').text(param.good_result);
					}
				}
			});
		}
	});	
});
</script>
<script type="text/javascript" charset="utf-8">
/* 댓글에 띄울 프로필 사진의 상대경로 값을 js로 넘기기 위한 작업 */
sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="container">
			<h2 class="text-primary" style="margin: 20px 0px 20px 0px; font-weight: bolder;">${board.title}</h2>
			<p>
				<img src="${pageContext.request.contextPath }/images/people.png" style="width: 25px; height: 25px;"> ${board.id} | <img src="${pageContext.request.contextPath }/images/eyes.png" style="width: 25px; height: 25px;"> ${board.hit}
			</p>
			<c:if test="${!empty board.modify_date}">
				<img src="${pageContext.request.contextPath }/images/refresh.png" style="width: 20px; height: 20px;"> ${board.modify_date}
	</c:if>
			<img src="${pageContext.request.contextPath }/images/writing.png" style="width: 25px; height: 25px;"> ${board.reg_date}
			<hr size="1" noshade="noshade" width="100%">
			<c:if test="${!empty board.filename}">
				<div class="align-center">
					<img src="${pageContext.request.contextPath}/upload/${board.filename}" class="detail-img" style="display: block; margin: auto; max-width: 800px">
				</div>
			</c:if>
			<c:if test="${!empty board.course}">
				<div class="align-center">
					<img src="${board.course}" class="detail-img" style="margin: 10px 0 0 55px;">
					<!-- 소진님 -->
				</div>
			</c:if>
			<p>${board.content}</p>
			<hr size="1" noshade="noshade" width="100%">

			<div class="detail-button" style="float: left;">
				<input type="button" value="♡" id="good" <c:if test="${checked==1}">style="color:#FE9A2E;"</c:if>>
				<div id="good_result" style="display: inline;">${good }</div>
			</div>
			<div class="any-button" style="float: right;">
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<c:if test="${session_user_num == board.user_num}">
					<input class="btn btn-primary" type="button" value="수정" style="margin-bottom: 20px" onclick="location.href='boardUpdateForm.do?board_num=${board.board_num}'">
				</c:if>
				<c:if test="${session_user_num==board.user_num || session_user_auth==3 }">	
					<input class="btn btn-secondary" type="button" value="삭제" style="margin-bottom: 20px" id="delete_btn">
					<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('boardDelete.do?board_num=${board.board_num}');
				}
			};
		</script>
				</c:if>

				<input class="btn btn-secondary" type="button" value="목록" style="margin-bottom: 20px" onclick="location.href='boardList.do'">
			</div>
			<!-- 댓글 시작 -->
			<div class="board-cmt" style="background: transparent; padding: 5px 0; margin-top: 20px;">
				<div class="comments-area">
					<h4>댓글</h4>
					<!-- 댓글 목록 출력 시작 -->
					<div class="comment-list" id="output"></div>
					<!-- 댓글 목록 출력 끝 -->
					<div <c:if test="${cmt_count > 0 }">class="comment-form"</c:if>>
						<form class="form-contact comment_form" id="cmt_form2" style="">
							<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
							<div class="row">
								<div class="col-12">
									<textarea class="form-control w-100" name="cmt_content" id="cmt_content" cols="10" rows="2" placeholder="내용을 입력해주세요." <c:if test="${empty session_user_num}">disabled="disabled"</c:if>><c:if test="${empty session_user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
								</div>
							</div>
							<c:if test="${!empty session_user_num}">
								<div id="cmt_first" style="display: flex; justify-content: space-between;">
									<div class="form-group">
										<input type="submit" value="덧글 등록" class="btn btn-primary" style="padding: 0.370rem 0.55rem; font-size: 0.9rem;">
									</div>
									<span class="letter-count">100/100</span>
								</div>
							</c:if>
						</form>
					</div>
				</div>
			</div>
			<!-- 댓글 끝 -->
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>




