<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<%@ include file="../includes/header.jsp"%><!-- Content Wrapper -->
<div id="content-wrapper" class="d-flex flex-column">

	<!-- Main Content -->
	<div id="content">

		<!-- Topbar -->
		<h2>수정하기</h2>
		<form method="post" class="modify" action="/board/modify">
			<table>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="pageNum" value="${cri.pageNum}" />
				<input type="hidden" name="amount" value="${cri.amount}" />
				<input type="hidden" name="type" value="${cri.type}" />
				<input type="hidden" name="keyword" value="${cri.keyword}" />
				<input type="hidden" name="bno" value="${board.bno }" />
				<tr>
					<td>제목</td>
					<td><input type="text" name="title" required
						value="${board.title }" /></td>
				</tr>
				<tr>
					<td>글쓴이</td>
					<td><input type="text" name="writer" required
						value="${board.writer}" /></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea rows="" cols="" name="content" required>${board.content}</textarea></td>
				</tr>
				<tr>
					<td>
						<button type="submit">등록</button> <input type="reset" value="취소" />
					</td>
				</tr>
			</table>
		</form>
		<!-- 첨부파일 이미지 영역  -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						파일들
					</div>
					<div class="panel-body">
						<div>
							<input type="file" name="uploadFile" multiple />
						</div>
						<div class="uploadResult">
							<ul></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 첨부파일 이미지 영역 끝 -->
	</div>
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
					str += "data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
					str += "<div><img src='/display?fileName="+fileCallpath+"'/>"
					str += "<button class='btn' data-type='image' data-file='"+fileCallpath+"'>삭제</button>"
					str += "</div></li>"
					})
					$(".uploadResult ul").html(str);
				}) //end getJSON
			})();
			//삭제버튼 클릭시 화면상에서 사라지도록 하기
	         $(".uploadResult").on("click", "button",function(){
	            console.log("delete file");
	            if(confirm("파일을 삭제하시겠습니까?")){
	               let targetLi = $(this).closest("li");
	               targetLi.remove();
	            }
	         })
			//input태그 중 type이 file요소 선택
			//요소의 변경이 있으면 콜백함수 실행
			
			$("input[type='file']").change(function(){
				//가상의 폼을 생성(폼태그)
				let formData = new FormData();
				let inputFiles = $("input[name='uploadFile']");
				let files = inputFiles[0].files;
				console.log(files);
				for(let i=0; i<files.length; i++){
					formData.append("uploadFile", files[i]);
				}
				
				let csrfHeaderName = "${_csrf.headerName}";
				let csrfTokenValue = "${_csrf.token}";
				$.ajax({
					url:'/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData,
					beforeSend: function(xhr){
						xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
					},
					type: 'POST',
					dataType: 'json',
					success: function(result) {
						console.log(result);
						showuploadResult(result);
					}
				})	
			})
			function showuploadResult(uploadResultArr) {
				//결과 배열이 null이거나 길이가 0이면 함수종료
				if(!uploadResultArr || uploadResultArr.length==0) {return}
				let uploadul = $(".uploadResult ul");
				let str ="";
				$(uploadResultArr).each(function(i, obj){
					console.log(obj);
					let fileCallpath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
					str += "<li data-path='"+obj.uploadPath+"' data-filename='"+obj.fileName+"' data-uuid='"+obj.uuid+"' data-type='"+obj.image+"'>"
					+"<img src='/display?fileName="+fileCallpath+"' />"
					+"<button class='btn' data-file=\'"+fileCallpath+"\' data-type='image'>"
											//파일로 접근 					//타입으로 접근
					+"삭제</button></li>"
				})
				uploadul.append(str);
			}
	       //전송버튼 누를 때 파일관련 데이터도 같이 전송
	 		$("button[type=submit]").on("click", function(e){
	 			//연결된 이벤트 제거 (submit전송 제거)
	 			e.preventDefault();
	 			//폼선택 formObj에 할당
	 			let formObj = $("form.modify");
	 			console.log("submit클릭");
	 			let str ="";
	 			$(".uploadResult ul li").each(function(i, obj){
	 				let jobj = $(obj);
	 			str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'/>"
	 			str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'/>"
	 			str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'/>"
	 			str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'/>"
	 			})
	 			//form에 데이터 추가 append()메소드 submit()전송하기
	 			formObj.append(str).submit();
	 		})
	 	})
</script>
<%@ include file="../includes/footer.jsp"%>