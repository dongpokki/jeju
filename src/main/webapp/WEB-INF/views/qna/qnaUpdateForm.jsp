<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Qna수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#update_form').submit(function(){
			if($('#title').val().trim()==''){
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="container">
	<div class="WritingEditor">
		<div class="WritingHeader">
			<h2 class="title">QnA 수정하기</h2>
		</div>
	</div>
	<form id="update_form" action="qnaUpdate.do"  enctype="multipart/form-data" method="post">
		<input type="hidden" name="qna_num" value="${qna.qna_num}"> 
		<div class="WritingContent" style="text-align:center;padding-top:75px">
			<div class="FlexableTextArea">
			<p>
				<input type="radio" name="viewable_check" value="0" id="viewable_check0" <c:if test="${qna.viewable_check ==0}">checked</c:if>>공개
				<input type="radio" name="viewable_check" value="1" id="viewable_check1" <c:if test="${qna.viewable_check ==1}">checked</c:if>>비공개
			</p>
				<label for="title" class="col-lg-2">제목</label>
				<div class="FlexableTextArea">
					<input type="text" name="title" id="title" class="textarea_title col-lg-offset-2 col-lg-10" value="${qna.title }">
				</div>
				<label for="content" class="col-lg-2">내용</label>
				<div class="FlexableTextArea">
					<textarea class="textarea_input col-lg-offset-2 col-lg-10" name="content" id="content">${qna.content }</textarea>
				</div>
				<div class="FlexableTextArea">
					<input type="file" name="filename" accept="image/gif,image/png,image/jpeg">
				</div>
				<c:if test="${!empty qna.filename }">
					<span id="file_detail">
						(${qna.filename })파일이 등록되었습니다.<br>
						다시 파일을 업로드하면 기존 파일은 삭제됩니다.
						<input type="button" class="btn btn-tertiary btn-login fw-bold"
							value="파일삭제" id="file_del">
					</span>
<script type="text/javascript">
	$(function(){
		$('#file_del').click(function(){
			let choice = confirm('삭제하시겠습니까?');
			if(choice){
				$('#file_detail').hide();
			}
		});
		$('#update_submit').click(function(){
			$.ajax({
				url:'qnaDeleteFile.do',
				type:'post',
				data:{qna_num : ${qna.qna_num}},
				dataType:'json',
				cache:false,
				timeout:3000,
				success:function(param){
					if(param.result=='logout'){
						alert('로그인 후 사용하세요.');
					}else if(param.result=='success'){
						$('#file_detail').hide();
					}else if(param.result=='wrongAccess'){
						alert('잘못된 접근입니다.');
					}else{
						alert('파일 삭제 오류');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
		});
	});
</script>
				</c:if>
			</div>
			<div class="d-grid gap-2">
					<input class="btn btn-primary btn-login fw-bold" type="submit" value="수정" id="update_submit"> 
					<input class="btn btn-secondary btn-login fw-bold" type="button" value="취소" onclick="location.href='qnaDetail.do?qna_num=${qna.qna_num}';">
				</div>
		</div>
	</form>
	</div>
<p>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>