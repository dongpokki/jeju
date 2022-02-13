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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/write_form.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/lang/summernote-ko-KR.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-section">
		<div class="container">
			<div class="WritingEditor">
				<div class="WritingHeader">
					<h2 class="title">QnA 수정하기</h2>
				</div>
			</div>
			<form id="write_form" action="qnaUpdate.do"
				enctype="multipart/form-data" method="post">
				<input type="hidden" name="qna_num" value="${qna.qna_num}">
				<div class="WritingContent">
					<div class="FlexableTextArea">
						<p>
							<input type="radio" name="viewable_check" value="0"
								id="viewable_check0"
								<c:if test="${qna.viewable_check ==0}">checked</c:if>><label for="viewable_check0">공개</label>
							<input type="radio" name="viewable_check" value="1"
								id="viewable_check1"
								<c:if test="${qna.viewable_check ==1}">checked</c:if>><label for="viewable_check1">비공개</label>
						</p>
						<div>
							<div class="FlexableTextArea">
								<input type="text" name="title" id="title"
									class="textarea_input" placeholder="제목을 입력해 주세요."
									value="${qna.title }">
							</div>
							<label for="content" class="col-lg-2">내용</label>
							${qna.content }
							<div class="form-group">
								<textarea id="summernote" name="content">${qna.content }</textarea>
							</div>
							<input type="file" name="filename" accept="image/gif,image/png,image/jpeg">
						</div>
						<div>
						<c:if test="${!empty qna.filename }">
							<span id="file_detail"> <input type="button" class="btn btn-tertiary btn-login fw-bold" value="파일삭제"
								id="file_del"> 현재 <b>(${qna.filename })</b> 파일이 등록되었습니다.<br>
								다시 파일을 업로드하면 기존 파일은 삭제됩니다. 
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
					<p>
					<div style="text-align:center">
						<input class="btn btn-primary btn-login fw-bold" type="submit"
							value="수정" id="update_submit"> <input
							class="btn btn-secondary btn-login fw-bold" type="button"
							value="취소"
							onclick="location.href='qnaDetail.do?qna_num=${qna.qna_num}';">
					</div>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>