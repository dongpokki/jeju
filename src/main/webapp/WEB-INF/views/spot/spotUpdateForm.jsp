<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>추천 장소 상세 페이지</title>

<!-- css  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">

<!-- js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/write_form.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/js/summernote/lang/summernote-ko-KR.js"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="container">
		<div class="WritingWrap">
			<div class="WritingHeader">
				<h2 class="title">추천 장소 작성하기</h2>
			</div>
			<div class="WritingContent">
				<div class="column_title">
					<form id="write_form" action="spotWrite.do" method="post" enctype="multipart/form-data">
						<select class="nice-select" name="category" id="category">
							<option data-display="Select" class="nice-select" value="">지역을 선택해주세요</option>
							<option value="1">동부</option>
							<option value="2">서부</option>
							<option value="3">남부</option>
							<option value="4">북부</option>
						</select>
				</div>
				<div class="FlexableTextArea">
					<textarea placeholder="제목을 입력해 주세요." class="textarea_input" style="height: 40px;" name="title" id="title">${spot.title }</textarea>
				</div>
				<div class="form-group">
					<textarea id="summernote" name="content">${spot.content }</textarea>
				</div>
				<label for="filename">파일</label> <input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty spot.filename}">
					<br>
					<span id="file_detail"> (${spot.filename})파일이 등록되어 있습니다. 다시 파일을 업로드하면 기존 파일은 삭제됩니다. <input type="button" value="파일삭제" id="file_del">
					</span>
					<script type="text/javascript">
	$(function(){
		$('#file_del').click(function(){
			let choice = confirm('삭제하시겠습니까?');
			if(choice){
				$.ajax({
					url:'deleteFile.do',
					type:'post',
					data:{board_num:${board.board_num}},
					dataType:'json',
					cache:false,
					timeout:30000,
					success:function(param){
						if(param.result == 'logout'){
							alert('로그인 후 사용하세요!');
						}else if(param.result == 'success'){
							$('#file_detail').hide();
						}else if(param.result == 'wrongAccess'){
							alert('잘못된 접속입니다.');
						}else{
							alert('파일 삭제 오류 발생');
						}
					},
					error:function(){
						alert('네트워크 오류 발생!');
					}
				});
			}
		});
	});
</script>
				</c:if>
				<div class="form-group" align="center">
					<input type="submit" value="등록" class="btn btn-primary"> <input type="button" value="취소" class="btn btn-primary" onclick="location.href='spotList.do'">
				</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>




