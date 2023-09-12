<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

			<h1>갤러리 리스트 페이지입니다.</h1>
		<ul>
			<c:forEach items="${list}" var="board">
			<li>
				<a href="/gallery/view?gno=${board.gno}" >
				<img src="/display?fileName=${board.fullName}"/>
				<p><c:out value="${board.title}" /></p>
				<p><c:out value="${board.writer}" /></p>
				</a>
			</li>
			</c:forEach>
		</ul>
</div>
</div>
<%@ include file="../includes/footer.jsp"%>