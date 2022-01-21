<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.movinial.notice.model.vo.Category" %>
<%
	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("list");
%>
   
        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 작성하기</title>
<style>
	.outer{
     
        color: black;
        width: 1000px;
		height: 500px;
        margin : auto;
        margin-top : 50px;
    }

	#enroll-form>table {border : 1px solid white;  margin : auto;}
	#enroll-form input, #enroll-form textarea{
		width: 800px;
		box-sizing: border-box;
	}

	#enroll-form textarea{
		height: 300px;
	}

	.btn1{
		width: 400px;
	}

	#gongback{
		height: 150px;
	}

</style>
</head>
<body>
 <%@ include file="../common/header.jsp" %>

<div class="outer">
		<!-- 파일을 첨부하는 요청을 할 경우에는 "반드시" enctype="multipart/form-data"를 지정해야한다.   -->
		<form id="enroll-form" action="<%= contextPath %>/insert.no" method="post" enctype="multipart/form-data">
			
			<div align="center">
				&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
				<a href="<%= contextPath %>" >공지사항</button> &nbsp; &nbsp;
				<a href="<%= contextPath %>">FAQ</button> &nbsp; &nbsp;
				<a href="<%= contextPath %>">문의하기</button> &nbsp; &nbsp;
				<a href="<%= contextPath %>/questionList.no">나의 문의 내역</button>
			</div>

			<br><br>
			<!-- 제목, 내용, 카테고리, 첨부파일 입력받기 -->
			<!--  작성자의 회원번호를 hidden으로 같이 넘길것  -->
			<input type="hidden" name="userNo" value="">
			
			<table align="center" border="1">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<select name="category">
							<%for(Category c : list){ %>
								<option value="<%= c.getCategoryNO() %>"><%= c.getCategoryName() %></option>
							<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea name="content" rows="10" style="resize: none" required></textarea>
					</td>
				</tr>
				<tr>
					<th>이미지</th>
					<td><input type="file" name="upfile"></td>
				</tr>
			</table>

			<br>
			<div align="center">
				&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp;
				<button type="submit" class="btn1 btn-secondary">작성하기</button> 
				<button type="reset" class="btn1 btn-secondary">취소하기</button>
			</div>

			<br><br>
		</form>
	
	</div>

	<div id = gongback></div>
	
 <%@ include file="../common/footer.jsp" %>

</body>
</html>