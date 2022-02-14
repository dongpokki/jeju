<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q&amp;A 게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
</head>
<body>
<div>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-section">
		<div class="container mt-5">
			<div id="qna-banner">
				<h1>Q&amp;A 게시판</h1>
			</div>
			<div class="search-form" >
			<form action="qnaList.do" method="get" style="float: right;">
				<div class="FormSelectButton">
					<div>
					<ul id="search">
						<li>
							<select name="keyfield" class="form-control" style="width:100px;height:38px">
							<option value="0"<c:if test="${param.keyfield==0}">selected</c:if>>전체</option>
							<option value="1"<c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2"<c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
							<option value="3"<c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
							</select>
						</li>
						<li>
							<input type="search" size="16" name="keyword" id="keyword" class="form-control" value="${param.keyword }" placeholder="검색어를 입력해주세요" style="height:38px">
							<button style="height:38px;" type="submit" class="btn btn-primary" style="backgroun-color:#FEA82F">
								<img src='${pageContext.request.contextPath }/images/search.png'width="25px">
							</button>
						</li>
					</ul>
					</div>
				</div>
			</form>
			</div>
			<div>
				<table id="Qna" style="width:100%">
					<tr>
						<c:if test="${session_user_auth==3}"><th>체크</th></c:if>
						<th>글번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회</th>
						<th>답변여부</th>
					</tr>
					<c:if test="${count==0 }">
					<tr>
						<td class="table-none" colspan="<c:if test="${session_user_auth==3 }">7</c:if><c:if test="${session.user_auth<3 }">6</c:if> align-center">표시할 내용이 없습니다.</td>
					</tr>
					</c:if>
					<c:if test="${count>0 }">
					<c:forEach var="qna" items="${list }">
					<tr>
						<c:if test="${session_user_auth==3}"><td>
							<input type="checkbox" name="delet_check" >
						</td></c:if>
						<td>${qna.qna_num }</td>
						<td onclick="location.href='qnaDetail.do?qna_num=${qna.qna_num}'" style="cursor:pointer">
							${qna.title }
							<c:if test="${qna.viewable_check==1 }">
								<img src="${pageContext.request.contextPath }/images/lock1.png" width="20px">
							</c:if>
						</td>
						<td>${qna.name }
						<c:if test="${qna.id ne 'admin'}">
							(<c:choose>
								<c:when test="${fn:length(qna.id) gt 2}">
									<c:out value="${fn:substring(qna.id, 0, 2)}"/><c:forEach begin="3" end="${fn:length(qna.id) }">*</c:forEach>
								</c:when>
							<c:otherwise>
								<c:out value="${qna.id}"/>
							</c:otherwise>
							</c:choose>)
						</c:if>
						</td>
						<td>${qna.reg_date }</td>
						<td>${qna.hit }</td>
						<td></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>	
				<div class="d-grid gap-2 col-6">
					<c:if test="${session_user_auth==3}"><input class="btn btn-tertiary" type="button" value="삭제" onclick="location.href='qnaDelete.do'"></c:if>
					<input <c:if test="${empty session_user_num}">disabled="disabled"</c:if> class="btn btn-primary" type="button" value="작성" onclick="location.href='qnaWriteForm.do'" style="backgroun-color:#FEA82F"> 
					<input class="btn btn-secondary" type="button" value="목록" onclick="location.href='qnaList.do'">
				</div>
			</div>
		</div>
		
	</div>
</div>
<p>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>