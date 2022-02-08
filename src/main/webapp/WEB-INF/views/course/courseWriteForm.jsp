<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>추천 코스 등록</title>

<!-- 레이아웃을 위한  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">

<!-- 글작성 api  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/summernote/summernote-lite.css">


</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<div class="page-section">
		<div class="container">
			<div class="row">
				<form id="write_form" action="areaWrite.do" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label for="title">제목 : </label> 
						<input type="text" class="" placeholder="제목을 입력해주세요." name="title" required="required" />
					</div>
					<div class="form-group">
						<textarea id="summernote" name="content"></textarea>
					</div>
				</form>
			</div>
		</div>

	</div>


	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/summernote/summernote-lite.js"></script>
	<script src="${pageContext.request.contextPath}/js/summernote/lang/summernote-ko-KR.js"></script>
	<script>
		$('#summernote').summernote(
				{
					toolbar : [
							// [groupName, [list of button]]
							[ 'fontname', [ 'fontname' ] ],
							[ 'fontsize', [ 'fontsize' ] ],
							[
									'style',
									[ 'bold', 'italic', 'underline',
											'strikethrough', 'clear' ] ],
							[ 'color', [ 'forecolor', 'color' ] ],
							[ 'table', [ 'table' ] ],
							[ 'para', [ 'ul', 'ol', 'paragraph' ] ],
							[ 'height', [ 'height' ] ],
							[ 'insert', [ 'picture', 'link', 'video' ] ],
							[ 'view', [ 'fullscreen', 'help' ] ] ],
					fontNames : [ 'Arial', 'Arial Black', 'Comic Sans MS',
							'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체',
							'바탕체' ],
					fontSizes : [ '8', '9', '10', '11', '12', '14', '16', '18',
							'20', '22', '24', '28', '30', '36', '50', '72' ],
					height : 300, // 에디터 높이
					minHeight : null, // 최소 높이
					maxHeight : null, // 최대 높이
					focus : true, // 에디터 로딩후 포커스를 맞출지 여부
					lang : "ko-KR", // 한글 설정
					placeholder : '내용을 입력해주세요.' //placeholder 설정
				});
	</script>
</body>
</html>




