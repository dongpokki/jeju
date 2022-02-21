<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when
		test="${session_user_auth!=3 && qna.viewable_check==1 && session_user_num!=qna.user_num}">
		<script type="text/javascript">
			alert('비공개 글입니다.');
			location.href = 'qnaList.do';
		</script>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html>
<head>
<meta charset="UTF-8">
<title>Qna 상세</title>
<link rel="icon" href="${pageContext.request.contextPath}/images/jeju.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/qna-cmt.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-section">
		<div class="container">
			<div class="WritingEditor">
				<div class="WritingHeader">
					<h2 class="title">${qna.qna_num }번.QnA상세 페이지</h2>
				</div>
				<div class="blog-single-wrap" style="padding-top: 28px">
					<h5 class="post-title">
						<c:if test="${qna.viewable_check==1}">[ 비공개 ]</c:if>
						${qna.title }
					</h5>
					<hr size="1" width="100%" style="border-top: 2px dashed rgba(0, 0, 0, 0.1)">
					<div class="post-meta"style="text-align:right">
						<div class="post-name">
							작성자 ${qna.id }
							<%-- <c:if test="${qna.id ne 'admin'}">
						(<c:choose>
									<c:when test="${fn:length(qna.id) gt 3}">
										<c:out value="${fn:substring(qna.id, 0, 2)}" />
										<c:forEach begin="3" end="${fn:length(qna.id)}">*</c:forEach>

									</c:when>
									<c:otherwise>
										<c:out value="${qna.id}" />
									</c:otherwise>
								</c:choose>)
						</c:if> --%>
						</div>
						<div class="post-lookup">| 조회 ${qna.hit } |</div>
						<div class="post-date">
							<c:if test="${!empty qna.modify_date }">
						최근 수정일 : ${qna.modify_date }
					</c:if>
							<c:if test="${empty qna.modify_date }">
						작성일 : ${qna.reg_date }
					</c:if>
						</div>
					</div>
					
					<div class="post-content">${qna.content }</div>
				</div>
				<c:if test="${!empty qna.filename }">
					<div>
						<table class="qnaTable">
							<colgroup>
								<col style="width:20%">
								<col style="width:80%">
							</colgroup>
							<tr>
								<td id="qna_file">파일</td>
								<td onclick="location.href='${pageContext.request.contextPath }/upload/${qna.filename}'" style="cursor:pointer;text-align:center">
									${qna.filename}<br>
									<a class="btn qna_answer">파일보기</a>
								</td>
							</tr>
							<tr></tr>
						</table>
					</div>
				</c:if>
				<hr size="1" width="100%">
				<div style="text-align: right">
					<c:if test="${session_user_num==qna.user_num}">
						<input type="button" class="btn btn-primary qna" value="수정"
							onclick="location.href='qnaUpdateForm.do?qna_num=${qna.qna_num}'">
					</c:if>
					<c:if
						test="${session_user_num==qna.user_num || session_user_auth==3 }">
						<input type="button" class="btn btn-tertiary qna" value="삭제"
							id="delete_btn_qna">
						<script type="text/javascript">
							let delete_btn = document.getElementById('delete_btn_qna');
							//이벤트 연결
							delete_btn.onclick = function() {
								let choice = confirm('삭제하시겠습니까?');
								if (choice) {
									location.replace('qnaDelete.do?qna_num=${qna.qna_num}');
								}
							};
						</script>
					</c:if>
					<input type="button" class="btn btn-secondary qna" value="목록" onclick="location.href='qnaList.do'">
				</div>
				<hr size="1" width="100%" style="border-top: 2px solid rgba(0, 0, 0, 0.1)">
				<!-- 댓글 시작 -->
				<!-- 댓글 목록 출력 -->
				<div id="output" class="blog-single-wrap" style="padding: 24px 0 0 0;"></div>
				<div class="paging-button" style="display: none; text-align: right">
					<input type="button" value="다음글 보기" class="btn btn-secondary">
				</div>
				<div id="loading" style="display: none">
					<img
						src="${pageContext.request.contextPath }/images/ajax-loader.gif">
				</div>
				<!-- 댓글 목록 출력 끝 -->
				<c:if test="${session_user_auth!=3}">
					<form style="">
						<input type="hidden" name="qna_num" value="${qna.qna_num }"
							id="qna_num">
					</form>
				</c:if>
				<div>
				<c:if test="${session_user_auth==3}">
					<div class="blog-single-wrap" style="padding-top:10px">
						
						<form id="cmtForm_qna">
							<div class="post-meta" style="padding: 12px 0;">
								<span class="post-cmt" style="float:left"><img alt="답변이미지" src="${pageContext.request.contextPath }/images/cmt-speech.png"> <b>답변</b></span>
								<div id="cmt_first" style="float:right">
									<span class="letter-count-qna">300 / 300</span>
								</div>
							</div>
							
							<input type="hidden" name="qna_num" value="${qna.qna_num }" id="qna_num">
							<textarea name="cmt_content" id="cmt_content" class="cmtp-content"></textarea>
							<p>
							<div id="cmt_second" style="text-align:right;">
								<input type="submit" value="답변" class="btn btn-secondary qna" onclick="location.href='qnaDqnaDetail.do?qna_num='${qna.qna_num}">
							</div>
						</form>
					</div>
				</c:if>
				</div>
				
				<!-- 댓글 끝 -->
			</div>
		</div>
		<div></div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
		</html>
	</c:otherwise>
</c:choose>