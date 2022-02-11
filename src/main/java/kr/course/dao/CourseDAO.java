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
	 public void insertCoursecourse(CourseVO course)throws Exception{
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
			 pstmt.setString(1, course.getTitle());
			 pstmt.setString(2, course.getContent());
			 pstmt.setString(3, course.getFilename());
			 pstmt.setString(4, course.getIp());
			 pstmt.setInt(5, course.getUser_num());
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
	 public int getCoursecourseCount(String keyfield,String keyword)throws Exception{
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
				 if(keyfield.equals("1")) sub_sql = "WHERE jboard_course.title LIKE ?";
				 else if(keyfield.equals("3")) sub_sql = "WHERE jboard_course.content LIKE ?";
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
	 public List<CourseVO> getCourseListcourse(int startRow, int endRow, 
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
				 if(keyfield.equals("1")) sub_sql = "WHERE jboard_course.title LIKE ?";
				 else if (keyfield.equals("2")) sub_sql = "WHERE jboard_course.content LIKE ?";
			 }
			 
			 sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
			 	 + "(SELECT * FROM jboard_course " + sub_sql + " ORDER BY jboard_course.course_num DESC) a) "
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
				 CourseVO course = new CourseVO();
				 course.setCourse_num(rs.getInt("course_num"));
				 //HTML태그를 허용하지 않음
				 course.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				 course.setHit(rs.getInt("hit"));
				 course.setReg_date(rs.getDate("reg_date"));
				
				 //courseVO를 ArrayList에 저장
				 list.add(course);
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
	 public CourseVO getCoursecourse(int course_num)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 CourseVO course = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션을 할당
			 conn = DBUtil.getConnection();
			 //SQL문 작성
			 sql = "SELECT * FROM jboard_course c JOIN juser u "
			 	+ "ON c.user_num=u.user_num WHERE c.course_num=?";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 //?에 데이터를 바인딩
			 pstmt.setInt(1, course_num);
			 //SQL문 실행해서 결과행을 ResultSet에 담음
			 rs = pstmt.executeQuery();
			 if(rs.next()) {
				 course = new CourseVO();
				 course.setCourse_num(rs.getInt("course_num"));
				 course.setTitle(rs.getString("title"));
				 course.setContent(rs.getString("content"));
				 course.setHit(rs.getInt("hit"));
				 course.setReg_date(rs.getDate("reg_date"));
				 course.setModify_date(rs.getDate("modify_date"));
				 course.setFilename(rs.getString("filename"));
				 course.setUser_num(rs.getInt("user_num"));
			 }
			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(rs, pstmt, conn);
		 }
		 return course;
	 }
	 //조회수 증가
	 public void updateReadcount(int course_num)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션을 할당
			 conn = DBUtil.getConnection();
			 
			 //SQL문 작성
			 sql = "UPDATE jboard_course SET hit=hit+1 WHERE course_num=?";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 //?에 데이터를 바인딩
			 pstmt.setInt(1, course_num);
			 //SQL문 실행
			 pstmt.executeUpdate();
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	 }
	 
		// 추천 코스 수정
		public void updateCoursecourse(CourseVO Course) throws Exception {
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
		public void deleteCoursecourse(int course_num) throws Exception {
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
