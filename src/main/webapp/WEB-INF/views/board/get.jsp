<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ include file="../includes/header.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
.bigPictureWrapper {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100vh;
	background: rgba(0, 0, 0, 0.5);
	display: none;
	justify-content: center;
	align-items: center;
	color: #fff;
}
</style>
<!-- Content Wrapper -->
<div id="content-wrapper" class="d-flex flex-column">

	<!-- Main Content -->
	<div id="content">

		<!-- Topbar -->

		<!-- End of Topbar -->

		<!-- Begin Page Content -->
		<div class="container-fluid">

			<!-- Page Heading -->
			<h1 class="h3 mb-2 text-gray-800">Tables</h1>
			<p class="mb-4">
				DataTables is a third party plugin that is used to generate the demo
				table below. For more information about DataTables, please visit the
				<a target="_blank" href="https://datatables.net">official
					DataTables documentation</a>.
			</p>
			<h1>상세조회 페이지</h1>
			<p>
				<c:out value="${board.bno}"></c:out>
			</p>
			<p>
				<c:out value="${board.title}"></c:out>
			</p>
			<p>
				<c:out value="${board.writer}"></c:out>
			</p>
			<p>
				<c:out value="${board.content}"></c:out>
			</p>
			<p>
				<button
					onclick="location.href='/board/list?pageNum=${cri.pageNum}&amount=${cri.amount}&type=${cri.type}&keyword=${cri.keyword}'">목록</button>
				
				<sec:authentication property="principal" var="pinfo" />
				<sec:authorize access="isAuthenticated()">
					<!-- 로그인한 사용자랑 글쓴이랑 같아야함 -->
					<c:if test="${pinfo.username eq board.writer}">
						<button
							onclick="location.href='/board/modify?bno=${board.bno}&pageNum=${cri.pageNum}&amount=${cri.amount}&type=${cri.type}&keyword=${cri.keyword}'">수정</button>
						<form method="post" action="/board/remove">
							<input type="hidden" name="bno" value="${board.bno}" />
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button type="submit">삭제</button>
						</form>	
					</c:if>
				</sec:authorize>
			</p>
			<!-- 첨부파일 이미지 영역  -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">파일들</div>
						<div class="panel-body">
							<div class="uploadResult">
								<ul>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 첨부파일 이미지 영역 끝 -->
		</div>
	</div>
	<div class="bigPictureWrapper">
		<div class="bigPicture"></div>
	</div>
	<script>
		$(document).ready(function() {
			//스스로 실행되는 함수
			(function() {
				let bno = '<c:out value="${board.bno}" />';
				$.getJSON("/board/getAttachList", {bno : bno}, 
				function(arr) {
					console.log(arr);
					//이미지 나타내기
					let str = "";
					$(arr).each(function(i, attach){
						let fileCallpath = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid+"_"+attach.fileName);
					str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"'"
					str += "data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>";
					str += "<div><img src='/display?fileName="+fileCallpath+"'/></div></li>"
					})
					$(".uploadResult ul").html(str);
				}) //end getJSON
			})();
			//li를 클릭하면 bicPictureWrapper 나타나게 하고
			//.bigPicture의 html내용에 이미지가 나타나도록 지정
			$(".uploadResult").on("click", "li", function(e) {
				console.log("view image");
				let liObj = $(this);
				let path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
				showImage(path.replace(new RegExp(/\\/g),"/"));
			})
			function showImage(fileCallPath) {
				alert(fileCallPath);
				$(".bigPictureWrapper").css("display", "flex");
				$(".bigPicture").html("<img src='/display?fileName="+fileCallPath+"' width='500'/><span>x</span>");
			}
			$(".bigPictureWrapper").on("click","span", function(e) {
				$(".bigPictureWrapper").css("display", "none");
			})
		})
	</script>
	<%@ include file="../includes/footer.jsp"%>
	</body>
	</html>