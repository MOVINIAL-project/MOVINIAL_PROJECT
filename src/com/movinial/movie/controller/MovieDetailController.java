package com.movinial.movie.controller;

import static com.movinial.common.MovieTemplate.getMovieDetail;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.movinial.member.model.vo.Member;
import com.movinial.movie.model.service.MovieService;
import com.movinial.movie.model.vo.Movie;
import com.movinial.review.model.service.ReviewService;
import com.movinial.review.model.vo.Review;

/**
 * Servlet implementation class MovieDetailController
 */
@WebServlet("/detail.mo")
public class MovieDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 쿼리스트링 request 값 뽑기
		int memberNo = 0; // 회원 번호
		int movieNo = Integer.parseInt(request.getParameter("movieNo")); // 영화 번호
		
		// 로그인 여부 확인
		if(request.getSession().getAttribute("loginUser") != null) {
			memberNo = ((Member)request.getSession().getAttribute("loginUser")).getMemberNo();
		}
		
		// 해당 영화 DB 정보 가져오기
		Movie m = new MovieService().selectMovieDetail(movieNo);
		
		// TMDB 해당 영화 상세정보 가져오기
		JSONObject movieDetail = getMovieDetail(m.getMovieId());
		
		// 로그인 여부에 따른 해당 영화 리뷰 정보 받아오기
		ArrayList<Review> list = new ArrayList<>();
		
		if(memberNo == 0) { // 로그아웃
			list = new ReviewService().selectMovieReviewLogout(movieNo);
		} else { // 로그인
			list = new ReviewService().selectMovieReviewLogin(memberNo, movieNo);
		}
		
		// 값 담기
		request.setAttribute("m", m);
		request.setAttribute("movieDetail", movieDetail);
		request.setAttribute("list", list);
		
		// 값  넘기기
		request.getRequestDispatcher("views/movie/movieDetailView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
