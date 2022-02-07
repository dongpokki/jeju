package kr.course.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.course.vo.CourseVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class CourseDAO {
	//싱글턴 패턴
	 private static CourseDAO instance = new CourseDAO();
	 public static CourseDAO getInstance() {
		 return instance;
	 }
	 private CourseDAO() {}
	 
	 // 추천 코스 등록
	 public void insertCourseBoard(CourseVO board)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션을 할당
			 conn = DBUtil.getConnection();
			 //SQL문 작성
			 sql = "INSERT INTO jboard_course (course_num,title,content,filename,ip,"
			 	 + "user_num) VALUES (jboard_course_seq.nextval,?,?,?,?,?)";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 //?에 데이터를 바인딩
			 pstmt.setString(1, board.getTitle());
			 pstmt.setString(2, board.getContent());
			 pstmt.setString(3, board.getFilename());
			 pstmt.setString(4, board.getIp());
			 pstmt.setInt(5, board.getUser_num());
			 //SQL문 실행
			 pstmt.executeUpdate();
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	 }
	 //총 레코드 수(검색 레코드 수)
	 public int getCourseBoardCount(String keyfield,String keyword)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String sql = null;
		 String sub_sql = "";
		 int count = 0;
		 
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 
			 if(keyword != null && !"".equals(keyword)) {
				 if(keyfield.equals("1")) sub_sql = "WHERE b.title LIKE ?";
				 else if(keyfield.equals("3")) sub_sql = "WHERE b.content LIKE ?";
			 }
			 
			 sql = "SELECT COUNT(*) FROM jboard_course " + sub_sql;
			 
			 pstmt = conn.prepareStatement(sql);
			 if(keyword != null && !"".equals(keyword)) {
				 pstmt.setString(1, "%"+keyword+"%");
			 }
			 
			 //SQL문을 실행하고 결과행을 ResultSet에 담음
			 rs = pstmt.executeQuery();
			 if(rs.next()) {
				 count = rs.getInt(1);
			 }	 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(rs, pstmt, conn);
		 }
		 return count;
	 }
	 // 추천 코스 목록
	 public List<CourseVO> getCourseListBoard(int startRow, int endRow, 
			                  String keyfield, String keyword)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 List<CourseVO> list = null;
		 String sql = null;
		 String sub_sql = "";
		 int cnt = 0;
		 
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 
			 if(keyword != null && !"".equals(keyword)) {
				 if(keyfield.equals("1")) sub_sql = "WHERE b.title LIKE ?";
				 else if (keyfield.equals("2")) sub_sql = "WHERE b.content LIKE ?";
			 }
			 
			 sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
			 	 + "(SELECT * FROM jboard_course " + sub_sql + " ORDER BY b.board_num DESC) a) "
			     + "WHERE rnum >= ? AND rnum <= ?";
			 
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 if(keyword != null && !"".equals(keyword)) {
				 pstmt.setString(++cnt, "%"+keyword+"%");
			 }
			 pstmt.setInt(++cnt, startRow);
			 pstmt.setInt(++cnt, endRow);
			 
			 //SQL문을 실행해서 결과행들을 ResultSet에 담음
			 rs = pstmt.executeQuery();
			 list = new ArrayList<CourseVO>();
			 while(rs.next()) {
				 CourseVO board = new CourseVO();
				 board.setCourse_num(rs.getInt("course_num"));
				 //HTML태그를 허용하지 않음
				 board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				 board.setHit(rs.getInt("hit"));
				 board.setReg_date(rs.getDate("reg_date"));
				 
				 //BoardVO를 ArrayList에 저장
				 list.add(board);
			 }
			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(rs, pstmt, conn);
		 }
		 return list;
	 }
	 // 추천 코스 상세
	 public CourseVO getCourseBoard(int course_num) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			CourseVO Course = null;
			String sql = null;

			try {
				// 커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				// SQL문 작성
				sql = "SELECT * FROM jboard_course WHERE course_num = ?";
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setInt(1, course_num);
				// SQL문 테이블에 반영하고 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				if (rs.next()) {
					Course = new CourseVO();
					Course.setCourse_num(rs.getInt("course_num"));
					Course.setTitle(rs.getString("title"));
					Course.setContent(rs.getString("content"));
					Course.setHit(rs.getInt("hit"));
					Course.setReg_date(rs.getDate("reg_date"));
					Course.setModify_date(rs.getDate("modify_date"));
					Course.setFilename(rs.getString("filename"));
				}
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				// 자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return Course;
		}
	 
		// 추천 코스 수정
		public void updateCourseBoard(CourseVO Course) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			try {
				// 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();

				if (Course.getFilename() != null) {
					sub_sql = ",filename=?";
				}

				sql = "UPDATE jboard_course SET title=?, content=?, modify_date=SYSDATE" + sub_sql
						+ ", ip=? WHERE course_num=?";

				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(++cnt, Course.getTitle());
				pstmt.setString(++cnt, Course.getContent());
				if (Course.getFilename() != null) {
					pstmt.setString(++cnt, Course.getFilename());
				}
				pstmt.setString(++cnt, Course.getIp());
				pstmt.setInt(++cnt, Course.getUser_num());
				pstmt.executeUpdate();
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				// 자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}

		// 추천 코스 삭제
		public void deleteCourseBoard(int course_num) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				// 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();

				sql = "DELETE FROM jboard_course WHERE WHERE course_num=?";

				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				pstmt.executeUpdate();
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				// 자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
}
