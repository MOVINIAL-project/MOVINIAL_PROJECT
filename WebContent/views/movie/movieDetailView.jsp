<%@page import="oracle.net.aso.r"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.movinial.movie.model.vo.Movie, org.json.JSONObject, java.util.ArrayList,
				 com.movinial.review.model.vo.Review, static com.movinial.common.MovieTemplate.*,
				 java.util.Locale"%>
<%
	// 영화 DB, 상세정보, 리뷰 가져오기
	Movie m = (Movie)request.getAttribute("m");
	JSONObject movieDetail = (JSONObject)request.getAttribute("movieDetail");
	ArrayList<Review> list = (ArrayList<Review>)request.getAttribute("list");
	
	// ISO 3166-1 처리 객체
	//Locale locale1 = new Locale("ko", "KR");
	//Locale locale2 = new Locale("ko", "JP");
	
	//String nationalname1 = locale1.getDisplayCountry();
	//String nationalname2 = locale2.getDisplayCountry();
	
	
	// 영화 정보 삽입용 변수
	String homepage = (String)movieDetail.get("homepage");
	Locale productionCountries = new Locale("ko", (String)(movieDetail.getJSONArray("production_countries").getJSONObject(0).get("iso_3166_1"))); // 제작 국가
	String productionCompanies = (String)movieDetail.getJSONArray("production_companies").getJSONObject(0).get("name"); // 제작사
	
	
	
	// 영화 상세보기 - 영화 포스터 가져오기
	String moviePosterUrl = getMoviePosterPath(m.getMovieId(), "w780");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 상세 페이지</title>
<style>
	.content {
		padding: 20px;
        margin-bottom: 20px;
        border: 1px solid #bcbcbc;
    }
	.table-size td {
		padding: 10px;
		border: solid 1px black;
	}
	.btn-group {
		text-decoration: none;
		color: black;
	}
	.btn-group:hover {
		text-decoration: none;
		color: black;
		cursor: pointer;
	}

</style>
</head>
<body>

	<%@ include file="../common/header.jsp" %>

	<!-- 영화 상세 정보 -->
	<div class="content">
		<table class="table-size">
			<tr>
				<td rowspan="7" style="width: 30%;">
					<% if(moviePosterUrl != null) { %>
						<img src="<%= moviePosterUrl %>" alt="<%= m.getTitle() %> 영화 포스터">
					<% } else { %>
						<h2>영화 포스터 없음</h2>
					<% } %>
				</td>
				<td style="width: 40%;">
					<h1><%= m.getTitle() %></h1>
				</td>
				<td colspan="3" style="text-align: center;">
					<h4>이 영화 보셨나요?</h4>
				</td>
			</tr>
			<tr>
				<td>
					<h1><%= m.getOriginalTitle() %></h1>
				</td>
				<td>
					<a class="movie-seen-btn btn-group" onclick="checkSeen()">
						<img src="<%= contextPath %>/resources/images/movie_seen_icon.png" alt="봤어요 아이콘" style="text-align: center;">
						<h3><%= m.getMovieSeen() %></h3>
					</a>
				</td>
				<td>
					<a class="movie-likes-btn btn-group" onclick="checkLikes()">
						<img src="<%= contextPath %>/resources/images/movie_likes_icon.png" alt="좋아요 아이콘">
						<h3 style="text-align: center;"><%= m.getMovieLikes() %></h3>
					</a>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<h4 style="font-weight: bold;">개요</h4>
					<% if(m.getOverview() != null) { %>
						<p>
							<%= m.getOverview() %>
						</p>
					<% } else { %>
						<p>
							없음
						</p>
					<% } %>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<br>
					<h4 style="font-weight: bold;">개봉일</h4>
					<h5><%= m.getReleaseDate() %></h5>
					<br>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<br>
					<h4 style="font-weight: bold;">제작국가</h4>
					<h5><%= productionCountries.getDisplayCountry() %></h5>
					<br>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<br>
					<h4 style="font-weight: bold;">제작사</h4>
					<h5><%= productionCompanies %></h5>
					<br>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<br>
					<h4 style="font-weight: bold;">공식 홈페이지 &nbsp&nbsp
					<% if(!homepage.equals("")) { %>
						<a href="<%= homepage %>" target="_blank">
							<img src="<%= contextPath %>/resources/images/external-link-btn.png" alt="<%= m.getTitle() %> 공식 홈페이지로 이동" style="width: 30px; height: 30px;">
						</a>
						</h4>
						<% } else { %>
						</h4>
						<h5>없음</h5>
					<% } %>
					
					<br>
				</td>
			</tr>
		</table>
	</div>


	<%-- 영화 봤어요 & 좋아요: 로그인 여부 확인 --%>
	<% if(loginUser == null) { %>
	
		<script>
			function checkSeen() {
				alert("로그인 후 이용해주시기 바랍니다");
				location.href = "<%= contextPath %>/login.me"
			}
			function checkLikes() {
				alert("로그인 후 이용해주시기 바랍니다");
				location.href = "<%= contextPath %>/login.me"
			}
		</script>
		
	<%-- 영화 봤어요 & 좋아요: 증감 처리 --%> 
	<%-- 회원 봤어요 & 좋아요: 영화 번호 저장/삭제 처리 --%>
	<% } else { %>
		<script>
			
			var movieNo = "<%= m.getMovieNo() %>"; // 현재 영화 번호
			var movieSeenValue = <%= m.getMovieSeen() %>; // 페이지 진입시 봤어요 값
			var movieLikesValue = <%= m.getMovieLikes() %>; // 페이지 진입시 좋아요 값
			
			// ---------- 영화 봤어요 ----------
			
			// 회원이 해당 영화에 '봤어요'를 누른 적이 있는지 확인
			function checkSeen(){
				
				$.ajax({
					url: "chkseen.mo",
					data: { mno : <%= loginUser.getMemberNo() %> },
					success: function(lm) {
						
						// 봤어요를 눌렀는지 확인
						if(lm.seenMovie != null) { 
							// 해당 회원의 '이영화 봤어요' 영화 번호 뽑아내기 (String[])
							var seenMovie = lm.seenMovie.split(',');
						}
						
						// 영화번호가 이미 있는지 확인
						if(seenMovie.indexOf(movieNo) != -1) { // 있다면, 영화 번호 삭제
							iDontSeenIt();
						}
						else { // 없다면, 영화 번호 삽입
							iSeenIt();
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}
				})
			}
			
			// 해당 영화 '봤어요' 체크 
			function iSeenIt() {
				
				$.ajax({
					url: "seen.mo",
					data: { 
						mno : <%= loginUser.getMemberNo() %>,
						movieNo: <%= m.getMovieNo() %>
					},
					success: function(seen) {
						
						if(seen > 0){ // 성공 시
							movieSeenValue++; // 봤어요 값 + 1
							$(".movie-seen-btn").children().eq(1).text(movieSeenValue);
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}		
				})
				
			}
			
			// 해당 영화 '봤어요' 체크 해제
			function iDontSeenIt(){

				$.ajax({
					url: "dontSeen.mo",
					data: { 
						mno : <%= loginUser.getMemberNo() %>,
						movieNo: <%= m.getMovieNo() %>
					},
					success: function(dontSeen) {
						
						if(dontSeen > 0){
							movieSeenValue--; // 봤어요 값 - 1
							$(".movie-seen-btn").children().eq(1).text(movieSeenValue);
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}	
				})
				
			}
			
			
			// ---------- 영화 좋아요 ----------
			
			// 회원이 해당 영화에 '좋아요'를 누른 적이 있는지 확인
			function checkLikes(){
				
				$.ajax({
					url: "chklike.mo",
					data: { mno : <%= loginUser.getMemberNo() %> },
					success: function(lm) {
						
						// 좋아요를 눌렀는지 확인
						if(lm.likesMovie != null) { 
							// 해당 회원의 '좋아요' 영화 번호 뽑아내기 (String[])
							var likesMovie = lm.likesMovie.split(',');
						}
						
						// 영화번호가 이미 있는지 확인
						if(likesMovie.indexOf(movieNo) != -1) { // 있다면, 영화 번호 삭제
							iDontLikeIt();
						}
						else { // 없다면, 영화 번호 삽입
							iLikeIt();
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}
				})
			}
			
			// 해당 영화 '좋아요' 체크 
			function iLikeIt() {
				
				$.ajax({
					url: "like.mo",
					data: { 
						mno : <%= loginUser.getMemberNo() %>,
						movieNo: <%= m.getMovieNo() %>
					},
					success: function(like) {
						
						if(like > 0){ // 성공 시
							movieLikesValue++; // 봤어요 값 + 1
							$(".movie-likes-btn").children().eq(1).text(movieLikesValue);
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}		
				})
				
			}
			
			// 해당 영화 '좋아요' 체크 해제
			function iDontLikeIt(){

				$.ajax({
					url: "dislike.mo",
					data: { 
						mno : <%= loginUser.getMemberNo() %>,
						movieNo: <%= m.getMovieNo() %>
					},
					success: function(dislike) {
						
						if(dislike > 0){
							movieLikesValue--; // 봤어요 값 - 1
							$(".movie-likes-btn").children().eq(1).text(movieLikesValue);
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}	
				})
				
			}
			
		</script>
	<% } %>


	<!-- 리뷰 -->
	<div class="content">
		<table class="table table-borderless" style="border: 1px black solid;">

			<!-- 리뷰 제목 -->
			<tr>
				<td>
					<h2>리뷰</h2>
				</td>
				<td align="right">
					<h5>
						<a style="text-decoration: none; color: black;" href="<%= contextPath %>/reviewList.mo?currentPage=1&movieNo=<%= m.getMovieNo() %>">MORE</a>
					</h5>
				</td>
			</tr>

			<!-- 리뷰 게시글 한 개당 목록 -->
			<!-- 영화 상세 페이지에서는 지정된 개수만큼 출력 -->
			<!-- 조회된 리뷰가 없을 때 -->
           	<% if(list.isEmpty()) { %>
           	
            	<tr>
            	    <td style="border: 1px black solid;" colspan="6">조회된 리뷰가 없습니다.</td>
            	</tr>
            	
           	<% } else {%>
           	
           		 <!-- 리뷰 n개 출력 -->
           		<% for(Review r: list) { %>
	                <tr>
	                    <td style="width: 20%; border: 1px black solid;">
	                    	<%= r.getReviewWriter() %>
	                    </td>
	                    <td>
                               작성일 <%= r.getCreateDate() %>
							<a type="button" class="btn btn-sm btn-secondary" data-toggle="modal" data-target="#reportForm">신고하기</a><!-- MODAL -->
	                    </td>
	                </tr>
	                <tr>
	                    <td rowspan="2">
	                        <img src="" alt="유저 프로필 이미지 경로">
	                    </td>
	                    <td>
	                    	<p>
	                    		<%= r.getReviewContent() %>
	                    	</p>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
							<a class="review-likes-btn btn-group" onclick="checkReviewLikes('<%= r.getReviewWriter() %>', '<%= r.getReviewNo() %>', this)">
	                        	<img src="<%= contextPath %>/resources/images/movie_likes_icon.png" alt="좋아요 아이콘" style="width: 50%; height: 50%;">&nbsp&nbsp
								<h4 style="text-align: center;"><%= r.getLikes() %></h4>
							</a>
	                    </td>
	                </tr>
                <% } %>
                
               <% } %>
		</table>
	</div>

	<%-- 리뷰 좋아요: 로그인 여부 확인 --%>
	<% if(loginUser == null) { %>
	
		<script>
			function checkReviewLikes() {
				alert("로그인 후 이용해주시기 바랍니다");
				location.href = "<%= contextPath %>/login.me"
			}
		</script>
		
	<%-- 리뷰 좋아요: 증감 처리 --%> 
	<%-- 리뷰 좋아요: 리뷰 번호 저장/삭제 처리 --%>
	<% } else { %>
		<script>
		
			// 회원이 해당 리뷰에 '좋아요'를 누른 적이 있는지 확인
			// 매개변수: 현재 리뷰 작성자(닉네임), 현재 리뷰 번호, 사용자가 누른 review-likes-btn 클래스 요소
			function checkReviewLikes(reviewWriter, reviewNo, reviewLikesBtn){
				
				var $reviewLikesValue = $(reviewLikesBtn).children().eq(1).text(); // 현재 리뷰의 좋아요 개수
				
				if(reviewWriter == "<%= loginUser.getMemberNickname() %>") { // 리뷰 작성자 확인
					alert("자신의 리뷰에 좋아요를 할 수 없습니다");
					return;
				}
			
				$.ajax({
					url: "chklike.rev",
					data: { mno : <%= loginUser.getMemberNo() %> },
					success: function(lr) {
						
						// 좋아요를 눌렀는지 확인
						if(lr.likesReview != null) { 
							// 해당 회원의 '좋아요' 리뷰 번호 뽑아내기 (String[])
							var likesReview = lr.likesReview.split(',');
						}
						
						// 리뷰번호가 이미 있는지 확인
						if(likesReview.indexOf(reviewNo) != -1) { // 있다면, 리뷰 번호 삭제
							iDontLikeItReview(reviewNo, reviewLikesBtn, $reviewLikesValue);
						}
						else { // 없다면, 리뷰 번호 삽입
							iLikeItReview(reviewNo, reviewLikesBtn, $reviewLikesValue);
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}
				})
				
			}
			
			// 해당 영화 '좋아요' 체크 
			function iLikeItReview(reviewNo, reviewLikesBtn, $reviewLikesValue) {
				
				$.ajax({
					url: "like.rev",
					data: { 
						mno : <%= loginUser.getMemberNo() %>,
						reviewNo: reviewNo
					},
					success: function(like) {
						
						if(like > 0){ // 성공 시
							$reviewLikesValue++; // 좋아요 값 - 1
							$(reviewLikesBtn).children().eq(1).text($reviewLikesValue);
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}		
				})
				
			}
			
			// 해당 영화 '좋아요' 체크 해제
			function iDontLikeItReview(reviewNo, reviewLikesBtn, $reviewLikesValue) {
				
				$.ajax({
					url: "dislike.rev",
					data: { 
						mno : <%= loginUser.getMemberNo() %>,
						reviewNo: reviewNo
					},
					success: function(dislike) {
						
						if(dislike > 0){ // 성공 시
							$reviewLikesValue--; // 좋아요 값 - 1
							$(reviewLikesBtn).children().eq(1).text($reviewLikesValue);
						}
						
					},
					error: function() {
						alert("서버와 접속이 원활하지 않습니다 \n 잠시 후 다시 시도해주세요");
					}	
				})
				
			}
			
		</script>
	<% } %>


	</script>

	
	<!-- Modal 영역 -->
	<!-- 신고하기 -->
	<div class="modal fade" id="reportForm">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
	
		<!-- Modal Header -->
		<div class="modal-header">
			<h4 class="modal-title">신고 사유</h4>
			<button type="button" class="close" data-dismiss="modal">&times;</button>
		</div>
	
			<!-- Modal body -->
			<div class="modal-body">
				<form action="신고처리할 서블릿" method="post">
						<table>
							<textarea name="reportContent" cols="60" rows="10" style="resize: none;"></textarea>

						</table>
						<br>

						<button type="submit" class="btn btn-info btn-sm">비밀번호 변경</button>
				</form>
			</div>
		</div>
		</div>
	</div>
	
	<%@ include file="../common/footer.jsp" %>

</body>
</html>