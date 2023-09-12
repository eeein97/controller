<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>

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
                    <p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below.
                        For more information about DataTables, please visit the <a target="_blank"
                            href="https://datatables.net">official DataTables documentation</a>.</p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
										<tr>
											<td>번호</td>
											<td>제목</td>
											<td>작성자</td>
											<td>작성일</td>
											<td>수정일</td>
										</tr>
                                    </thead>
                                    <tfoot>
                                       	<tr>
											<td>번호</td>
											<td>제목</td>
											<td>작성자</td>
											<td>작성일</td>
											<td>수정일</td>
										</tr>
                                    </tfoot>
                                    <tbody>
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
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->
            
<%@ include file="../includes/footer.jsp" %>     