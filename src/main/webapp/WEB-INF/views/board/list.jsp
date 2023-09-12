<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#modal {
		width: 100%;
		height: 100vh;
		position: absolute;
		top: 0;
		left: 0;
		background: rgba(0,0,0,0.5);
		display: none;
	}
	#modalbox {
		width: 70%;
		background: #fff;
		margin: 20px auto;
		padding: 20px;
		border-radius: 10px;
	}
</style>
</head>
<body>
	<h1>리스트페이지</h1>
	<table>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>수정일</td>

		</tr>
		<c:forEach items="${list}" var="board">
			<tr>
				<td><c:out value="${board.bno}" /></td>
				<td><a href="/board/get?bno=${board.bno}&pageNum=${pageMaker.cri.pageNum}&amount=${pageMaker.cri.amount}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}"><c:out value="${board.title}" /></a></td>
				<td><c:out value="${board.writer}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
				value="${board.regdate }" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
				value="${board.updatedate }" /></td>
			</tr>
		</c:forEach>
	</table>
	<!-- 검색 추가하기 -->
	<form method="get" action="/board/list">
	<Select name="type">
		<option value="">----</option>
		<option id="T" value="T" <c:out value="${pageMaker.cri.type =='T' ? 'selected' : ''}" />>제목</option>
		<option id="C" value="C" <c:out value="${pageMaker.cri.type =='C' ? 'selected' : ''}" />>내용</option>
		<option id="W" value="W" <c:out value="${pageMaker.cri.type =='W' ? 'selected' : ''}" />>작성자</option>
		<option id="W" value="TC">제목+내용</option>
		<option id="W" value="TW">제목+작성자</option>
		<option id="W" value="TCW">제목+내용+작성자</option>
	</Select>
	<input type="text" name="keyword" value='${pageMaker.cri.keyword}' required />
	<button type="submit">검색</button>
	</form>
	<!-- 페이징 추가하기 -->
	<div class="pull-right">
		<ul class="pagination">
			<c:if test="${pageMaker.prev}">
				<li>
					<a href="/board/list?pageNum=${pageMaker.startPage-1}&amount=10&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}">prev</a>
				</li>
			</c:if>
			<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
				<li>
					<a href="/board/list?pageNum=${num}&amount=10&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}">${num}</a>
				</li>
			</c:forEach>
			<c:if test="${pageMaker.next}">
				<li>
					<a href="/board/list?pageNum=${pageMaker.endPage+1}&amount=10&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}">next</a>
				</li>
			</c:if>
		</ul>
	</div>
	
	<p><button onclick="location.href='/board/register'">게시글등록</button></p>
	<!-- 모달 추가 -->
	<div id="modal">
		<div id="modalbox">
			<h3>페이지 안내</h3>
			<div id="infotext">
				<c:out value="${param.result}" /> 처리가 완료 되었습니다.
			</div>
			<button id="modalclose">닫기</button>
		</div>
	</div>
	<script>
		let result = '<c:out value="${param.result}" />';
		checkModal(result);
		function checkModal(result) {
			if(!result) return;
			
			document.querySelector("#modal").style.display = "block";
		}
		//닫기 누르면 모달창 안보이게 하기
		document.querySelector("#modalclose").addEventListener("click", function() {
			document.querySelector("#modal").style.display = "none";
			})
		
	</script>
</body>
</html>