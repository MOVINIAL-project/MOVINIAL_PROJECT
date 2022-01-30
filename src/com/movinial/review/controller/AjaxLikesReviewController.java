package com.movinial.review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movinial.review.model.service.ReviewService;

/**
 * Servlet implementation class AjaxLikesReviewController
 */
@WebServlet("/like.rev")
public class AjaxLikesReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxLikesReviewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int memberNo = Integer.parseInt(request.getParameter("mno")); // 회원 번호
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo")); // 리뷰 번호
		
		int result1 = new ReviewService().increaseLikes(reviewNo); // 해당 리뷰의 좋아요 수 증가
		
		if(result1 > 0) { // 해당 리뷰의 좋아요 수 증가 처리 성공시, 리뷰 좋아요 테이블 '리뷰' 컬럼에 리뷰 번호 저장
			
			int result2 = new ReviewService().likesReviewStore(memberNo, reviewNo);
			
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(result2); // 저장 성공 유무 상관없이 값 넘기기
			
		} else { // 해당 리뷰 좋아요 수 증가 처리 실패시
			
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
