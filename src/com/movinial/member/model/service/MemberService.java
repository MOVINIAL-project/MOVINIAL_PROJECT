package com.movinial.member.model.service;

import static com.movinial.common.JDBCTemplate.close;
import static com.movinial.common.JDBCTemplate.commit;
import static com.movinial.common.JDBCTemplate.getConnection;
import static com.movinial.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.function.Predicate;

import com.movinial.common.model.vo.PageInfo;
import com.movinial.community.model.vo.Community;
import com.movinial.community.model.vo.Reply;
import com.movinial.member.model.dao.MemberDao;
import com.movinial.member.model.vo.Member;
import com.movinial.review.model.vo.ReviewRank;
import com.movinial.member.model.vo.MemberGenre;
import com.movinial.member.model.vo.MyPageFile;
import com.movinial.movie.model.vo.Movie;
import com.movinial.review.model.vo.Review;

public class MemberService {
	
	public Member loginMember(String userId, String userPwd) {
				
		//Service => Connection 객체
		// 1) Connecttion 객체 생성
		
		Connection conn = getConnection();
		
		
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		close(conn);
		
		return m;
		
		
	}
	
	public int insertMember(Member m ) {

		Connection conn = getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		
		// 성공헀으면 1, 실패했다면 0으로 리턴이 되었을 것이다.
		if(result > 0) { //성공했다면
			
		Member mm = new MemberDao().loginMember(conn, m.getMemberId(), m.getMemberPwd());
		m.setMemberNo(mm.getMemberNo());
		result += new MemberDao().insertMemberLikeCommunity(conn, m);
		result += new MemberDao().insertMemberLikeMovie(conn, m);
	    result += new MemberDao().insertMemberLikeReview(conn, m);
			
			commit(conn);
		} else { // 실패했다면
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
		
	}
		
	public String findId(String memberName, String phone) { // 아아디 찾기

		Connection conn = getConnection();
		
		String memberId = new MemberDao().findId(conn, memberName, phone);
		
		close(conn);
		
		return memberId;
	}
	
	public Member forgotPwd(String memberId, String memberName, String phone) { // 비밀번호 변경 대상 찾기
		
		Connection conn = getConnection();
		
		Member m = new MemberDao().forgotPwd(conn, memberId, memberName, phone);

		close(conn);
    
		return m;
	}
	
	public int updateMemberPwd(String updatePwd,String memberId, int memberNo) {	//확인된 회원 비밀번호 변경

		Connection conn = getConnection();

		int result = new MemberDao().updateMemberPwd(conn, updatePwd,memberId,memberNo);

		close(conn);

		
		return result;
	}

	public int selectMemberCount() { // 전체 멤버 수 구하기
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectMemberCount(conn);
				
		close(conn);
		
		return listCount;
		
	} // selectMemberCount

	
	public ArrayList<Member> selectMember(PageInfo pi) {
		
		Connection conn = getConnection();
		
		ArrayList<Member> list = new MemberDao().selectMember(conn, pi);
		
		close(conn);
			
		return list;
		
	}

	public int deleteMember(String str) { // 멤버 삭제(관리자)
		
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn, str);
		
		close(conn);
		
		return result;
		
	} // deleteMember : 멤버 삭제

	
	public ArrayList<Member> searchMember(String keyword) { // searchMember : 키워드로 검색
		
		Connection conn = getConnection();
		
		ArrayList<Member> list = new MemberDao().searchMember(conn, keyword);
		
		close(conn);
		
		return list;
		
	} 

	
	//주현 : 회원탈퇴
	public int deleteMember(String memberId, String memberPwd) {

	
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn, memberId, memberPwd);
		
		if(result>0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	//주현 : 내 글 리스트
	public ArrayList<Community> selectList(PageInfo pi, int memberNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Community> list = new MemberDao().selectList(conn,pi,memberNo);
		
		close(conn);
		
		return list;
		
	}
	
	//주현 : 내 글 수
	public int selectListCount(int memberNo) {
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectListCount(conn,memberNo);
		
		close(conn);
		
		return listCount;
	}
	
	//주현 : 내 댓글 목록 가져오기
	public ArrayList<Reply> selectReplyList(PageInfo pi, int memberNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Reply> list = new MemberDao().selectReplyList(conn,pi,memberNo);
		
		close(conn);

		return list;
	}

	//주현 : 내 댓글 수 가져오기
	public int selectReplyListCount(int memberNo) {
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectReplyListCount(conn,memberNo);

		close(conn);
		
		return listCount;
	}

	//주현 : 리뷰 좋아요 수 카운트
	public int selectReviewLikesCount(int memberNo) {
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectReviewLikesCount(conn, memberNo);

		close(conn);
		
		return listCount;
	}
	
	//주현 : 리뷰 좋아요 리스트 보기
	public ArrayList<Review> myReviewLikesListView(PageInfo pi, int memberNo) {
					
		Connection conn = getConnection();
			
		ArrayList<Review> list = new MemberDao().myReviewLikesListView(conn,pi,memberNo);
			
		close(conn);

		return list;
	}
	
	//주현 : 내가 쓴 영화 리뷰 글 수
	public int selectMyReviewCount(int memberNo) {
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectMyReviewCount(conn, memberNo);

		close(conn);
		
		return listCount;
	}
	
	//주현 : 내가 쓴 영화 리뷰 글
	public ArrayList<Review> selectMyReview(PageInfo pi, int memberNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Review> list = new MemberDao().selectMyReview(conn,pi,memberNo);
		
		close(conn);

		return list;
	}
	
	//주현 : 비밀번호 변경
	public Member updatePwdMember(String memberId, String memberPwd, String updatePwd) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().updatePwdMember(conn, memberId, memberPwd, updatePwd);
		
		Member updateMem = null;
		
		if(result>0) {
			commit(conn);

			updateMem = new MemberDao().selectMember(conn, memberId);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return updateMem;

	}
	
	//주현 : 회원정보 수정
	public Member updateMember(Member m) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		
		Member updateMem = null;
		
		if(result>0) {
			commit(conn);
			updateMem = new MemberDao().selectMember(conn, m.getMemberId());
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return updateMem;
	}
	
	//주현 : 내가 누른 커뮤니티 게시글 좋아요 수
	public int selectCommunityLikesCount(int memberNo) {
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectCommunityLikesCount(conn, memberNo);
		
		close(conn);
		
		return listCount;
	
	}
	
	//주현 : 내가 누른 커뮤니티 게시글 좋아요 목록
	public ArrayList<Community> myCommunityLikesList(PageInfo pi, int memberNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Community> list = new MemberDao().myCommunityLikesList(conn,pi,memberNo);
			
		close(conn);
		
		return list;
		
	}

	public ArrayList<ReviewRank> reviewRanking() {

		Connection conn = getConnection();
		
		ArrayList<ReviewRank> list = new MemberDao().reviewRanking(conn);
		
		close(conn);
		
		return list;
	}
	
	//주현 : 내가 누른 영화 좋아요 리스트 수
	public int selectMovieLikesCount(int memberNo) {
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectMovieLikesCount(conn, memberNo);
		
		close(conn);
		
		return listCount;
	}
	
	//주현 : 내가 누른 영화 좋아요 리스트
	public ArrayList<Movie> myMovieLikesList(PageInfo pi, int memberNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Movie> list = new MemberDao().myMovieLikesList(conn,pi,memberNo);
			
		close(conn);

		return list;
	}
	
	//주현 : 내가 누른 영화 봤어요 수 카운트
	public int selectMyMovieSeenCount(int memberNo) {
		
		Connection conn = getConnection();
		
		int listCount = new MemberDao().selectMyMovieSeenCount(conn, memberNo);
		
		close(conn);
		
		return listCount;
		
	}
	
	//주현 : 내가 누른 영화 봤어요 목록
	public ArrayList<Movie> myMovieSeenList(PageInfo pi, int memberNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Movie> list = new MemberDao().myMovieSeenList(conn,pi,memberNo);
			
		close(conn);

		return list;
	}

	//주현: 마이페이지 프로필이미지(업로드)
	public int updateProfileImage(MyPageFile mpf) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().updateProfileImage(conn,mpf);
			
		close(conn);

		return result;
	}
	
	//주현 : 마이페이지 프로필 이미지(가져오기)
	public String getProfileImage(int memberNo) {
		
		Connection conn = getConnection();
		
		String result = new MemberDao().getProfileImage(conn,memberNo);
			
		close(conn);

		return result;
	}
	
	//주현 : 마이페이지 리뷰쓰기 봤어요 검색 리스트 출력
	public ArrayList<Movie> searchSeenMovie(int memberNo) { 

		Connection conn = getConnection();
		
		ArrayList<Movie> list = new MemberDao().searchSeenMovie(conn, memberNo);
		
		close(conn);
		
		return list;
	} 

	
	//주현 : 닉네임 중복확인
	public int nicknameCheck(String nicknameUp) {
		
		Connection conn = getConnection();
		
		int count = new MemberDao().nicknameCheck(conn, nicknameUp);
		
		close(conn);
		
		return count;
		
	}
	
public int idCheck(String checkId) { // 아이디 중복 체크
		
		Connection conn = getConnection();
		int count = new MemberDao().idCheck(conn, checkId);
		close(conn);
	
		return count;

		
	}

public  ArrayList<MemberGenre> selectGenreMoiveList() { // 장르별영화목록
	
	Connection conn = getConnection();
	ArrayList<MemberGenre> memberGenreMovieList = new MemberDao().selectGenreMoiveList(conn);
	close(conn);

	return memberGenreMovieList;

	
}

public  ArrayList<MemberGenre> selectGenreList() { // 장르조회
	
	Connection conn = getConnection();
	ArrayList<MemberGenre> memberGenreList = new MemberDao().selectGenreList(conn);
	close(conn);

	return memberGenreList;

	
}




	
	

}
