package com.movinial.movie.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movinial.common.model.vo.PageInfo;
import com.movinial.member.model.vo.Member;
import com.movinial.movie.model.service.MovieService;
import com.movinial.movie.model.vo.Movie;
import com.movinial.review.model.service.ReviewService;
import com.movinial.review.model.vo.Review;

/**
 * Servlet implementation class MovieReviewDetailListController
 */
@WebServlet("/reviewList.mo")
public class MovieReviewDetailListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieReviewDetailListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int memberNo = 0; // 회원 번호
		int movieNo; // 조회하고자 하는 영화번호
		
		int listCount; // 현재 공개된 리뷰의 총 갯수
		int currentPage; // 리뷰 첫 페이지
		int pageLimit; // 페이지 하단에 보여줄 페이징바의 최대 갯수 => 10개
		int boardLimit; // 한 페이지에 보여질 게시글 최대 갯수 => 10개
		
		int maxPage; // 가장 마지막 페이지가 몇 번 페이지인지 (== 총 페이지의 갯수)
		int startPage; // 페이지 하단에 보여질 첫번째 페이징바
		int endPage; // 페이지 하단에 보여질 마지막 페이징바
		
		// 영화번호 가져오기
		movieNo = Integer.parseInt(request.getParameter("movieNo"));
		
		// 로그인 여부에 따른 총 리뷰 개수 가져오기
		if(request.getSession().getAttribute("loginUser") != null) { // 로그인
			memberNo = ((Member)request.getSession().getAttribute("loginUser")).getMemberNo();
			listCount = new ReviewService().selectListCountLogin(memberNo, movieNo);
		} else { // 로그아웃
			listCount = new ReviewService().selectListCountLogout(movieNo);
		}
		
		// 정렬 확인
		// 1: 최신순, 2: 등록순, 3: 좋아요순
		int sort = Integer.parseInt(request.getParameter("sort"));
		String orderBy = "";
			
		switch(sort) {
			case 1: 
				orderBy = "CREATE_DATE DESC";
				break;
			case 2:
				orderBy = "CREATE_DATE";
				break;
			case 3:
				orderBy = "LIKES DESC, CREATE_DATE DESC";
				break;				
		}
		
		// 페이징 처리 (10페이지 기준)
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		pageLimit = 10;
		boardLimit = 10;
		
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 페이징바 정보 PageInfo로 가공
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		// 해당 영화 리뷰 정보 받아오기
		ArrayList<Review> list = new ArrayList<>();
		
		// 현재 정렬의 리뷰 가져오기
		if(memberNo != 0) { // 로그인
			list = new ReviewService().selectMovieReviewListLogin(memberNo, movieNo, pi, orderBy);
		} else { // 로그아웃
			list = new ReviewService().selectMovieReviewListLogout(movieNo, pi, orderBy);
		}
		
		// 해당 영화 정보 받아오기
		Movie m = new MovieService().selectMovieDetail(movieNo);
		
		request.setAttribute("sort", sort);
		request.setAttribute("pi", pi);
		request.setAttribute("list", list);
		request.setAttribute("m", m);
		
		request.getRequestDispatcher("views/movie/movieReviewDetailView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
