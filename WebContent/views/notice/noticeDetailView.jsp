<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.movinial.notice.model.vo.Notice" %>
<%
	Notice n = (Notice)request.getAttribute("n");  // : Object
	// n ==> 글번호, 글제목, 글내용, 작성자 아이디, 작성일 
	
	int nno = n.getNoticeNo();
%>    
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세보기</title>
<style>
.outer{
    border: 1px solid #bcbcbc; 
    width: 1100px; 
    height:730px;
    margin: auto;
    margin-bottom: 30px;
}
.list-area{
    border: 1px solid #bcbcbc; 
    font-size: 22px;  
    width: 1000px;
}
.btn-area{
    width : 100%;
    height: 80px;
    align: center;
}
.btn-area>a{
    color:white;
    font-size: 25px;
    margin-left: 50px;
    text-decoration: none;
    text-align:center;

}

#noticeTitle{
   border-bottom: 1px solid #bcbcbc; 
   font-weight: bold;
}
#noticeDate{
    border-bottom: 1px solid #bcbcbc; 
    text-align: right;
    font-weight: bold;
}

td{
    height: 55px;
}
#h2{
    font-weight: bolder;
}
#noticeContent{
    height:400px;
}
.qbtn{
	height : 50px;
}
#listFont{
	font-size:22px;
}

.qbtn{
	height : 50px;
	font-size:22px;
	}

</style>

</head>
<body>
 <%@ include file="../common/header.jsp" %>

	<div class="outer">
        <br>
        <h2 id="h2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고객센터</h2>
        <br>
        <div class="btn-area">
            <a href="<%= contextPath%>/noticeList.no?currentPage=1" class="btn btn-sm btn-secondary" style="background: black;">공지사항 &nbsp;&nbsp;&nbsp;</a>
            <a href="<%=contextPath%>/FAQList.no" class="btn btn-sm btn-secondary" style="background: black;">FAQ&nbsp;&nbsp;&nbsp;</a>
            
            <% if(loginUser != null && !loginUser.getMemberId().equals("admin")){ %>
            <a href="<%=contextPath%>/qEnrollForm.no" class="btn btn-sm btn-secondary" style="background: black;">문의하기&nbsp;&nbsp;&nbsp;</a>
            <a href="<%=contextPath%>/questionList.no?currentPage=1" class="btn btn-sm btn-secondary" style="background: black;">나의 문의내역</a>
            <%} %>
            
            <% if(loginUser != null && loginUser.getMemberId().equals("admin")){ %>
            	<a href="<%=contextPath%>/questionListManagement.no?currentPage=1" class="btn btn-sm btn-secondary" style="background: black;">문의내역</a>  <!-- 문의 내역 조회하기  -->
            <% } %>
            
        </div>

        <table align="center" class="list-area">
          <!--  <thead>
                <tr>                
                    <th width="500">제목</th>
                    <th width="300">작성일</th>                  
                </tr>
            </thead> -->
            <tbody>
		        <tr>		                
		            <td id="noticeTitle"><%= n.getNoticeTitle() %></td> 
		            <td id="noticeDate"><%= n.getCreateDate() %></td>
		        </tr> 
                <tr>
                    <td colspan="2" id="noticeContent"><%= n.getNoticeContent() %></td> 
                </tr>                      
                	
            </tbody>
        </table>
        <br>
        <div align="center">
        	<a href="<%= contextPath%>/noticeList.no?currentPage=1" class="btn btn-secondary qbtn" style="background: black; font-size: 22px;">목록가기</a>
        	 
            <% if(loginUser != null && loginUser.getMemberId().equals("admin")){ %>
                <a href="<%=contextPath%>/updateForm.no?nno=<%= n.getNoticeNo() %>" id="noticeUpdatebtn"  class="btn btn-danger" style="background: black; font-size: 22px;">수정</a> <!-- 수정 -->
                <a href="<%=contextPath%>/delete.no?nno=<%= n.getNoticeNo() %>" id="noticeDeletebtn" class="btn btn-danger" style="background: black; font-size: 22px;">삭제</a> <!-- 삭제 -->
            <% } %>
     
        </div>
 </div>




 <%@ include file="../common/footer.jsp" %>        
</body>
</html>