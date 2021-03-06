package com.movinial.notice.model.dao;

import static com.movinial.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.movinial.common.model.vo.PageInfo;

import com.movinial.member.model.vo.Member;
import com.movinial.notice.model.vo.Answer;
import com.movinial.notice.model.vo.Category;
import com.movinial.notice.model.vo.Notice;
import com.movinial.notice.model.vo.Qfile;
import com.movinial.notice.model.vo.Question;

public class NoticeDao {

	private Properties prop = new Properties();
		
		public NoticeDao() {
			
			String fileName = NoticeDao.class.getResource("/sql/notice/notice-mapper.xml").getPath();
			
			try {
				prop.loadFromXML(new FileInputStream(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	public ArrayList<Category> selectCategory(Connection conn){
			
			// SELECT문 => ResultSet => 7개 == 여러 행
			// 변수
			ArrayList<Category> list = new ArrayList<>();		
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			
			String sql = prop.getProperty("selectCategory");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					list.add(new Category(rset.getInt("CATEGORY_NO"),
										  rset.getString("CATEGORY_NAME")));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rset);
				close(pstmt);
			}
				
			return list;
		}
	
	public int selectListCount(Connection conn, int memberNo) {
		// SELECT => ResultSet => 우리가 지금 필요한 건 총 게시글의 개수!
		// SELECT문을 쓰지만 상식적으로 반환되는 값이 정수가 필요함
		// 변수
		int listCount=0;

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return listCount;	
	}
	
public ArrayList<Question> selectList(Connection conn, PageInfo pi, int memberNo ) {  // 문의 페이징
		
		ArrayList<Question> list = new ArrayList<>();		
		PreparedStatement pstmt = null;		
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				Question q = new Question(rset.getInt("QNA_NO"),
						   				  rset.getString("CATEGORY_NAME"),
						   				  rset.getString("QNA_TITLE"),
						   				  rset.getString("QNA_WRITER"),
						   				  rset.getDate("CREATE_DATE"));
						
				list.add(q);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);	
		}
		
		return list;
	}

	public int insertQuestion(Connection conn, Question q) {
		// INSERT 문 => 처리된 행의 개수
				// 필요한 변수
				int result = 0;
				
				PreparedStatement pstmt = null;
				
				String sql = prop.getProperty("insertQuestion");
				
				try {
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, Integer.parseInt(q.getCategory()));
					pstmt.setString(2, q.getQnaTitle());
					pstmt.setString(3, q.getQnaContent());
					pstmt.setInt(4, Integer.parseInt(q.getQnaWriter()));
				
					result = pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					close(pstmt);
				}
				return result;
	}

	public int insertQfile(Connection conn, Qfile at) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertQfile");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;	
	}

	
	
	
	public ArrayList<Notice> selectNoticeList(Connection conn, PageInfo pi) { // 공지 페이징
		
		// SELECT문 => ResultSet => 여러 형이므로 ArrayList<Board>
		// 변수
		ArrayList<Notice> list = new ArrayList<>();		
		PreparedStatement pstmt = null;		
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectNoticeList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// TOP-N 분석 활용 : 인라인뷰 활용(서브쿼리 중에서도 FROM절에 서브쿼리가 들어가는 것)
			// 1) ORDER BY 순서가 가장 마지막인데 맨 처음에 실행되어야 하므로
			//    일단 정렬해주는 SELECT문을 만듦 => 서브쿼리
			// 2) 서브쿼리를 FROM절에 넣음(메인쿼리의) + ROWNUM 붙이기
			// 3) 메인 쿼리의 WHERE절에 어디서부터 어디까지만 조회할 건지 잘라내기
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				Notice n = new Notice(rset.getInt("NOTICE_NO"),
									  rset.getString("NOTICE_WRITER"),
								      rset.getString("NOTICE_TITLE"),
								      rset.getDate("CREATE_DATE"));
			   
				list.add(n);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);	
		}
		
		return list;
	}
	
	
	public int selectNoticeListCount(Connection conn) {
		// SELECT => ResultSet => 우리가 지금 필요한 건 총 게시글의 개수!
		// SELECT문을 쓰지만 상식적으로 반환되는 값이 정수가 필요함
		// 변수
		int listCount=0;

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectNoticeListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return listCount;	
	}

	public Notice selectNotice(Connection conn, int noticeNo) {
		
		// SELECT문 => ResultSet => PK제약조건에 의해 한 행만 뽑힘 => Notice 
		// 필요한 변수
		Notice n = null;
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				n = new Notice(rset.getInt("NOTICE_NO"),
							   rset.getString("MEMBER_ID"),
							   rset.getString("NOTICE_TITLE"),
							   rset.getString("NOTICE_CONTENT"),
							   rset.getDate("CREATE_DATE"));
				
				
				// NOTICE_NO은 화면에 보여지는 데이터는 아니지만
				// (항상 테이블의 PK가 화면에 보이지는 않지만) 
				// 수정하거나 삭제할 때 반드시 필요하므로 무조건 같이 조회해오면 좋다
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return n;
	}

	public Question selectQuestion(Connection conn, int questionNo) {
	// SELECT문 => ResultSet => PK에 의해 한 건만 => Board
			// 변수
			Question q = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			
			String sql = prop.getProperty("selectQuestion");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, questionNo);
				
				rset = pstmt.executeQuery();
				
				if(rset.next()) {
					q = new Question(rset.getInt("QNA_NO"),
								  rset.getString("CATEGORY_NAME"),
								  rset.getString("QNA_TITLE"),
								  rset.getString("QNA_CONTENT"),
								  rset.getString("MEMBER_ID"),
								  rset.getDate("CREATE_DATE"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rset);
				close(pstmt);	
			} 
			return q;
}
	
	public Qfile selectQfile(Connection conn, int questionNo) {
		
		Qfile at = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectQfile");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, questionNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				at = new Qfile();
				at.setFileNo(rset.getInt("FILE_NO"));
				at.setOriginName(rset.getString("ORIGIN_NAME"));
				at.setChangeName(rset.getString("CHANGE_NAME"));
				at.setFilePath(rset.getString("FILE_PATH"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return at;
	}

	public int insertNotice(Connection conn, Notice n) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter()));  // "1"
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	} // insertNotice

	public int updateNotice(Connection conn, Notice n) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, n.getNoticeNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	} // updateNotice

	public int deleteNotice(Connection conn, int noticeNo) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, noticeNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	} // deleteNotice

	// 모든 문의 내역 수
	public int selectListManagementCount(Connection conn) { 
		
		int listCount=0;

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListManagementCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return listCount;
		
	} // selectListManagementCount

	// 모든 문의 내역 조회
	public ArrayList<Question> selectListManagement(Connection conn, PageInfo pi) {

		ArrayList<Question> list = new ArrayList<>();		
		PreparedStatement pstmt = null;		
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListManagement");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				Question q = new Question(rset.getInt("QNA_NO"),
						   				  rset.getString("CATEGORY_NAME"),
						   				  rset.getString("QNA_TITLE"),
						   				  rset.getString("MEMBER_ID"),						   				 
						   				  rset.getDate("CREATE_DATE"));
						
				list.add(q);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);	
		}
		
		return list;
		
	}
	
	public Question selectQuestionManagement(Connection conn, int questionNo) {
		// SELECT문 => ResultSet => PK에 의해 한 건만 => Board
				// 변수
				Question q = null;
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				
				String sql = prop.getProperty("selectQuestion");
				
				try {
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, questionNo);
					
					rset = pstmt.executeQuery();
					
					if(rset.next()) {
						q = new Question(rset.getInt("QNA_NO"),
									  rset.getString("CATEGORY_NAME"),
									  rset.getString("QNA_TITLE"),
									  rset.getString("QNA_CONTENT"),
									  rset.getString("MEMBER_ID"),
									  rset.getDate("CREATE_DATE"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					close(rset);
					close(pstmt);	
				} 
				return q;
	}
		
		public Qfile selectQfileManagement(Connection conn, int questionNo) {
			
			Qfile at = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			
			String sql = prop.getProperty("selectQfile");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, questionNo);
				rset = pstmt.executeQuery();
				
				if(rset.next()) {
					at = new Qfile();
					at.setFileNo(rset.getInt("FILE_NO"));
					at.setOriginName(rset.getString("ORIGIN_NAME"));
					at.setChangeName(rset.getString("CHANGE_NAME"));
					at.setFilePath(rset.getString("FILE_PATH"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rset);
				close(pstmt);
			}
			return at;
		}

		public ArrayList<Answer> selectAnswerList(Connection conn, int qnaNo) {
			// SELECT => ResultSet => ArrayList, while
			// 변수
			ArrayList<Answer> list = new ArrayList<>();
			
			PreparedStatement pstmt = null;
			
			ResultSet rset = null;
			
			String sql = prop.getProperty("selectAnswerList");
			
			try {
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setInt(1, qnaNo);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					
					list.add(new Answer(rset.getInt("ANSWER_NO"),
									   	rset.getString("ANSWER_CONTENT"),
									   	rset.getString("MEMBER_ID"),
									   	rset.getString("CREATE_DATE")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rset);
				close(pstmt);
			}
			return list;
		}

		public int insertAnswer(Connection conn, Answer a) {
			// INSERT문 = 처리된 행의 개수
			// 필요한 변수
			int result = 0 ;
			
			PreparedStatement pstmt = null;
			
			String sql = prop.getProperty("insertAnswer");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, a.getRefQno());
				pstmt.setInt(2, Integer.parseInt(a.getAnswerWriter()));
				pstmt.setString(3, a.getAnswerContent());
				

				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}		
			return result;
		}
}

	

		
	

