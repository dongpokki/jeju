<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
<link rel="icon" href="${pageContext.request.contextPath}/images/jeju.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script>
<style>
  table {
    width: 100%;
    border-top: 1px solid #6b6c67;
    border-collapse: collapse;
  }
  th, td {
    border-bottom: 1px solid #6b6c67;
    padding: 10px;
    text-align: center;
  }
  #search-form {
  	text-align: right;
  	}
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="container">
	<h2 class="text-primary" style="margin: 20px 0px 30px 0px; font-weight: bolder;"><img src="${pageContext.request.contextPath }/images/stone.png" style= "width: 50px; height: 50px;"> 자유 게시판</h2>
	<hr width=100%>
	<div id="notice-box" style="border:1px solid #6b6c67; paddig:10px; margin: 30px 20px 30px 20px; backgorund padding: bg ";>
	<div id="notice-img" style="float:left; margin-right:20px;">
	<img src="${pageContext.request.contextPath }/images/notice.png"></div>
	<div id="notice-context" style="margin:20px 20px 20px 30px">
	<p>이 곳은 JEJU 회원분들의 진솔한 이야기, 미담사례 등 건전한 의사를 자유롭게 게시하는 곳으로<br>
	게시된 의견에 대하여는 관리자는 답변하지 않습니다.</p>
	<p>국가안전이나 보안에 위배되는 경우, 정치적 목적이나 성향이 있는 경우, 특정개인, 단체(특정종교 포함) 등에
	대한 비방, 욕설· 음란물 등 불건전한 내용, 상업적인 광고, 유언비어, 반복적인 내용,<br> 
	기타 해당란의 설정취지에 부합되지 않은 내용 등의 게시물은 「JEJU 홈페이지 자유게시판 관리방안」에 의거 알림절차 없이 삭제될 수 있음을 알려드립니다.</p>
	<p>주민등록번호, 핸드폰번호, 은행 계좌번호, 신용카드번호 등 개인정보(본인 또는 타인)가 누출되지 않도록 주의하시기 바랍니다.</p>
	<p> 답변을 원하시는 내용 또는 생활불편 사항은 상단 “Q&A”를 이용하여 주시기 바랍니다.</p>
	<p>게시글이 삭제되면 그 글의 답글도 같이 삭제되니 참고하시기 바랍니다.</p>
	<p>게시글 당사자가 사생활 침해나 명예훼손 등의 사유로 정정 삭제 요청을 한 경우 「정보통신망 이용촉진 및 정보보호 등에 관한 법률」 제44조의 2 에 의거 해당 게시물을 삭제 · 임시조치하고, 신고 및 처리내역을 게시자에게 통지합니다.
	</p>
	</div>
	</div>
			<hr>
	<div id="search-form">
	<div style="float: left;font-size:20px;margin-top: 10px">
				전체 <span class="text-primary">${count }</span>건</div>
	<form id="search_form" action="boardList.do" method="get">
		<ul class="search">
		<li>
				<select name="keyfield" class="form-control" style="width:120px;height:42px;margin:3px">
					<option value="1"<c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
					<option value="2"<c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
					<option value="3"<c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
				</select>
			
				<input type="text" class="form-control" value="${param.keyword}" name="keyword" id="keyword" placeholder="검색어를 입력해주세요">
		<button type="submit" class="btn btn-primary btn-block" style="margin-bottom: 3px;">찾기</button>
			</li>
		</ul>
		<select name="sort" class="nice-select" onChange="this.form.submit()" style="border-color: transparent; height: none; padding: 0; float:right;">
							<option <c:if test="${empty param.sort }">selected</c:if> value="board_num">최신순</option>
							<option <c:if test="${param.sort eq'hit' }">selected</c:if> value="hit">조회수순</option>
							<option <c:if test="${param.sort eq'good' }">selected</c:if> value="good">좋아요순</option>
						</select>
	</form>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display" align="center" style="border:1px solid #6b6c67; margin: 40px 0px 40px 0px; padding: 100px 100px 100px 100px;">
		게시글이 존재하지 않습니다.
	</div>	
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회</th>
		</tr>
		<c:forEach var="board" items="${list}">
		<c:if test="${board.notice==1}"><tr style="background-color:#dddddd"></c:if>
			<td>
			<c:if test="${board.notice==1}"><img src="${pageContext.request.contextPath }/images/board-no.png" style= "width: 20px; height: 20px;"></c:if>
			<c:if test="${board.notice!=1}">${board.board_num}</c:if>
			</td>
			<td style="text-overflow:ellipsis;overflow:hidden;width:240px;"><a href="boardDetail.do?board_num=${board.board_num}">${board.title}</a></td>
			<td>${board.id}</td>
			<td>${board.reg_date}</td>
			<td>${board.hit}</td>
		</tr>	
		</c:forEach>
	</table>
	<div class="align-center" style="margin-top: 20px;">
		${pagingHtml}
	</div>
	</c:if>
	<div class="list-space" align="right">
		<input class="btn btn-primary" type="button" value="글쓰기" style="margin: 10px 0px 10px 0px;" onclick="location.href='boardWriteForm.do'"
		<c:if test="${empty user_num}"> onclick="location.href='${pageContext.request.contextPath}/main/loginform.do'"</c:if>>
		<input class="btn btn-secondary" type="button" value="목록" onclick="location.href='boardList.do';">
	</div>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>




