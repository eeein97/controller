<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	 	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../includes/header.jsp"%>

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

			<h1>등록페이지입니다.</h1>
			<form class="user" method="post" action="/board/register">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="form-group row">
					<div class="col-sm-12 mb-3 mb-sm-0">
						<input type="text" class="form-control form-control-user"
							id="exampleFirstName" placeholder="제목" name="title" required>
					</div>
					<div class="col-sm-12">
						<input type="text" class="form-control form-control-user"
							id="exampleLastName" placeholder="글쓴이" name="writer"  value='<sec:authentication property="principal.username"/>' readonly required>
					</div>
					<div class="col-sm-12">
						<textarea rows="" cols="" class="form-control form-control-user" 
						id="exampleLastName" name="content" required>내용</textarea>
					</div>
					<div>
						<button type="submit" class="btn btn-primary btn-user">등록</button>
						<input type="reset" value="취소" class="btn btn-primary btn-user" />
					</div>
			</form>
		</div>
		<!-- 새로 추가되는 부분 -->
		<div class="row">
			<div class="col-la-12">
				<div class="panel panel-default">
					<div class="panel-heading">파일 첨부</div>
					<div class="paner-body">
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
	</div>
	
	<script>
		//html문서를 다 로드하면 실행
		$(document).ready(function() {
			
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
			//삭제버튼 추가하기		 클릭하면      버튼에	이벤트 위임
			$(".uploadResult").on("click", "button" ,function(e) {
				//이벤트를 발생시키면 이벤트 발생시킨 객체를 this가 가르킴
				//커스텀 속성 dataset에 접근하는 방식 ==> data("키이름")
				let targetFile = $(this).data("file"); //this = 버튼
				let type = $(this).data("type");
				let targetLi = $(this).closest("li");
				//closest(00) = 00에 가까운
				$.ajax ({
					url:'/deleteFile',
					data: {fileName: targetFile, type: type},	//post전송시 데이터를 가지고 감
					dataType: 'text',
					type:'POST',
					success: function(result){
						alert(result);
				}
			})
			targetLi.remove();
		})
		//전송버튼 누를 때 파일관련 데이터도 같이 전송
		$("button[type=submit]").on("click", function(e){
			//연결된 이벤트 제거 (submit전송 제거)
			e.preventDefault();
			//폼선택 formObj에 할당
			let formObj = $("form.user");
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