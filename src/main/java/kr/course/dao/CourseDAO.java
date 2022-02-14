package kr.course.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.course.vo.CourseVO;
import kr.qna.vo.QnaVO;
import kr.spot.vo.SpotVO;
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
	 public void insertCourse(CourseVO course)throws Exception{
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
	 public int getCourseCount(String keyfield,String keyword)throws Exception{
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
	 public List<CourseVO> getListCourse(int startRow, int endRow, 
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
	 public CourseVO getCourse(int course_num)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 CourseVO course = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션을 할당
			 conn = DBUtil.getConnection();
			 //SQL문 작성
			 sql = "SELECT * FROM jboard_course c JOIN juser u ON c.user_num=u.user_num WHERE c.course_num=?";
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
		public void updateCourse(CourseVO Course) throws Exception {
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
		
		// ----------------------------------------------------------- 이하 메서드 정동윤 작성 ------------------------------------------------------------------
		
		
		// [정동윤 작성] 메인에 노출할 BEST3 spot 구하기
		public List<CourseVO> getRankingCourse() throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<CourseVO> list = null;
			String sql = null;

			try {
				// 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();

				// sql문 작성
				// 게시글별로 추천수의 합(total_good)이 높은 순으로 랭킹1~3위 까지 3개의 컬럼 조회 [추천수(good)이 같은 경우에는,
				// 게시글번호(course_num)이 낮은(먼저 등록한 순)순으로 추가 비교]
				sql = "select b.course_num,b.title,b.content,b.filename,u.total_good,u.rank from jboard_course b join (select * from (select course_num,total_good,row_number() over (order by total_good desc,course_num) rank from(select course_num,count(*) total_good from jgood_course where good =1 group by course_num)) where rank<4) u on b.course_num = u.course_num order by rank";

				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				// sql문 수행하여 결과 집합을 rs에 담음
				rs = pstmt.executeQuery();

				list = new ArrayList<CourseVO>();

				while (rs.next()) {
					CourseVO course = new CourseVO();

					course.setCourse_num(rs.getInt("course_num"));
					course.setTitle(StringUtil.useNoHtml(rs.getString("title")));
					course.setContent(rs.getString("content"));
					course.setFilename(rs.getString("filename"));
					
					// 자바빈(VO)을 ArrayList에 저장
					list.add(course);
				}

			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				// 자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		
		// [정동윤 작성] - 마이페이지에서 노출할 내가 추천한 코스 내역
		// 내가 추천한 코스 리스트 조회
		public List<CourseVO> MyGoodCourse(int session_user_num,int startRow,int endRow) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<CourseVO> list = null;
			String sql = null;

			try {
				// 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				System.out.println("MyGoodCourse 메서드 실행");
				System.out.println("session_user_num : " + session_user_num); //44
				System.out.println("startRow : " + startRow); //1
				System.out.println("endRow : " + endRow); //3
				
				// sql문 작성
				sql = "select * from (select a.*, rownum rnum from (select b.course_num,b.title from jboard_course b join jgood_course g on b.course_num = g.course_num where g.user_num=? and good=1 order by b.course_num)a) where rnum>=? and rnum<=?";

				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				// ?에 데이터 바인딩
				pstmt.setInt(1, session_user_num);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);

				// sql문 수행하여 결과 집합을 rs에 담음
				rs = pstmt.executeQuery();

				list = new ArrayList<CourseVO>();

				while (rs.next()) {
					CourseVO course = new CourseVO();

					course.setCourse_num(rs.getInt("course_num"));
					course.setTitle(StringUtil.useNoHtml(rs.getString("title")));

					// 자바빈(VO)을 ArrayList에 저장
					list.add(course);
				}

			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				// 자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		
		// [정동윤 작성] - 마이페이지에서 노출할 내가 추천한 코스 내역
		// 내가 추천한 코스 리스트 카운트 구하기
		public int MyGoodCourseCount(int session_user_num) throws Exception{
			Connection conn =null;
			PreparedStatement pstmt =null;
			ResultSet rs =null;
			String sql=null;
			int count=0;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				
				//sQL문 작성
				sql="select count(*) from jgood_course where user_num=? and good=1";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, session_user_num);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					count=rs.getInt(1);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		
		// [정동윤 작성] - 마이페이지에서 노출할 내가 작성한 코스 내역
		// 내가 작성한 코스 리스트 카운트 구하기
		public int getmyCourseCount(int session_user_num) throws Exception{
			Connection conn =null;
			PreparedStatement pstmt =null;
			ResultSet rs =null;
			String sql=null;
			int count=0;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				
				//sQL문 작성
				sql="select count(*) from jboard_course where user_num = ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, session_user_num);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					count=rs.getInt(1);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		
		
		// [정동윤 작성] - 마이페이지에서 노출할 내가 작성한 코스 내역
		// 내가 작성한 코스 리스트 조회
		public List<CourseVO> getmyCourseList(int session_user_num,int startRow,int endRow) throws Exception{
			Connection conn =null;
			PreparedStatement pstmt =null;
			ResultSet rs=null;
			List<CourseVO> list =null;
			String sql =null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				//sql문 작성
				sql="select * from (select a.*,rownum rnum from (select course_num,title,user_num from jboard_course where user_num = ?)a) where rnum>=? AND rnum<=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, session_user_num);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
				
				rs = pstmt.executeQuery();
				
				list = new ArrayList<CourseVO>();
				
				while(rs.next()) {
					CourseVO course = new CourseVO();
					course.setTitle(rs.getString("title"));
					course.setCourse_num(rs.getInt("course_num"));
					
					//BoardVO를 ArrayList에 저장
					list.add(course);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}

}
