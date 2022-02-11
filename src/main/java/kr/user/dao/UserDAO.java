package kr.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.user.vo.UserVO;
import kr.util.DBUtil;

public class UserDAO {
	// 싱글턴 패턴
	private static UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	private UserDAO() {}

	//===============================================================================================================================================================//
	/*								 [일반회원]							*/
	// 회원가입
	public void insertUser(UserVO user)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0; // 시퀀스 번호 저장

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			//오토커밋 해제(sql문이 1개가 아니므로 커밋을 수작업으로 해줘야함)
			conn.setAutoCommit(false);

			//회원번호(user_num)생성 (존재하는 테이블컬럼이 아닌 특수한 작업을 할 경우 dual 테이블 이용)
			//두 테이블에 같은 회원번호를 넣기 위해 동일한 하나의 회원번호(시퀀스)먼저 조회(생성)
			sql = "select juser_seq.nextval from dual";

			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			//SQL문 테이블에 반영하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();

			if(rs.next()) {
				num = rs.getInt(1); // 컬럼인덱스 명시(1부터 시작)
			}

			//juser 테이블에 데이터 저장
			sql = "insert into juser (user_num,id) values(?,?)"; // auth 컬럼은 default 2

			//PreparedStatemet 객체 생성
			pstmt2 = conn.prepareStatement(sql);

			//?에 데이터 바인딩
			pstmt2.setInt(1, num);
			pstmt2.setString(2, user.getId());

			//SQL 문장 실행
			pstmt2.executeUpdate();

			//juser_detail 테이블에 데이터 저장
			sql = "insert into juser_detail (user_num,name,passwd,phone,email,zipcode,address1,address2) values (?,?,?,?,?,?,?,?)";

			//PreparedStatemet 객체 생성
			pstmt3 = conn.prepareStatement(sql);

			//?에 데이터 바인딩
			pstmt3.setInt(1, num);
			pstmt3.setString(2, user.getName());
			pstmt3.setString(3, user.getPasswd());
			pstmt3.setString(4, user.getPhone());
			pstmt3.setString(5, user.getEmail());
			pstmt3.setString(6, user.getZipcode());
			pstmt3.setString(7, user.getAddress1());
			pstmt3.setString(8, user.getAddress2());

			//SQL 문장 실행
			pstmt3.executeUpdate();

			//SQL문 실행 시 모두 성공하면 commit (sql문이 2개이상일 경우 커밋을 수작업으로 해줘야함)
			conn.commit();

		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback (sql문이 2개이상일 경우 롤백 수작업으로 해줘야함)
			conn.rollback();

			//예외 던지기
			throw new Exception(e);
		}finally {
			// 자원정리는 객체 생성 역순으로 (pstmt는 3개 생성했으므로, 자원정리도 3번)
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}

	}


	// ID중복 체크 및 로그인 처리
	public UserVO checkUser(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO user = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			//sql문 작성 (juser와 juser_detail 테이블을 조인. juser의 누락된 데이터가 보여야 id 중복 체크 가능)
			sql = "select * from juser u LEFT OUTER JOIN juser_detail d ON u.user_num=d.user_num where u.id=?";

			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			//?에 데이터 바인딩
			pstmt.setString(1, id);

			//sql문 수행하여 결과집합을 rs에 담음
			rs = pstmt.executeQuery();

			if(rs.next()) {
				user = new UserVO();
				user.setUser_num(rs.getInt("user_num"));
				user.setId(rs.getString("id"));
				user.setAuth(rs.getInt("auth"));
				user.setPasswd(rs.getString("passwd"));
				user.setPhoto(rs.getString("photo"));
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return user;
	}



	// 회원 상세정보
	public UserVO getUser(int session_user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO user = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			//sql문 작성
			sql = "select * from juser u join juser_detail d on u.user_num=d.user_num where u.user_num=?";

			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			//?에 데이터 바인딩
			pstmt.setInt(1, session_user_num);

			//sql문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user = new UserVO();
				user.setUser_num(rs.getInt("user_num"));
				user.setId(rs.getString("id"));
				user.setAuth(rs.getInt("auth"));
				user.setPasswd(rs.getString("passwd"));
				user.setName(rs.getString("name"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setZipcode(rs.getString("zipcode"));
				user.setAddress1(rs.getString("address1"));
				user.setAddress2(rs.getString("address2"));
				user.setPhoto(rs.getString("photo"));
				user.setReg_date(rs.getDate("reg_date"));
				user.setModify_date(rs.getDate("modify_date"));
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return user;
	}	


	// 회원 정보수정
	public void updateUser(UserVO user)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//sql문 작성
			sql = "update juser_detail set name=?,phone=?,email=?,zipcode=?,address1=?,address2=?,modify_date=sysdate where user_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPhone());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getZipcode());
			pstmt.setString(5, user.getAddress1());
			pstmt.setString(6, user.getAddress2());
			pstmt.setInt(7, user.getUser_num());
			
			//sql문 수행
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}	
	}


	// 비밀번호 수정
	public void updatePassword(String passwd,Integer session_user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//sql문 작성
			sql = "update juser_detail set passwd=? where user_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, passwd);
			pstmt.setInt(2, session_user_num);
			
			//sql문 수행
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}


	// 프로필 사진 수정
	public void updateMyPhoto(String photo,int session_user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//sql문 작성
			sql = "update juser_detail set photo=? where user_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, photo);
			pstmt.setInt(2, session_user_num);
			
			//sql문 수행
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}


	// 회원탈퇴(회원 정보 삭제)
	public void deleteUser(int session_user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//auto commit 해제
			conn.setAutoCommit(false);
			
			// juser의 auth 값 변경
			// 정책상 탈퇴회원의 아이디로 재가입 할수 없기 때문에, juser 테이블에 탈퇴유저의 데이터를 남겨놓는다.
			sql = "update juser set auth=0 where user_num =?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, session_user_num);
			
			//SQL문 실행
			pstmt.executeUpdate();
			
			// zmember_detail의 데이터 삭제
			sql = "delete from juser_detail where user_num =?";
			
			//PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt2.setInt(1, session_user_num);
			
			//SQL문 실행
			pstmt2.executeUpdate();
			
			//모든 sql 수행 성공 시 커밋
			conn.commit();
		} catch (Exception e) {
			//sql 수행 실패 시 롤백
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}



	//===============================================================================================================================================================//
	/*								 [관리자] 							*/
	// 총 회원 수
	public int getUserCountByAdmin(String keyfield,String keyword) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String sql=null;
		String sub_sql ="";
		int count =0;
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색글 처리
				if(keyfield.equals("1")) sub_sql = "WHERE id LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE name LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE email LIKE ?";
			}
			
			sql ="SELECT count(*) FROM juser LEFT JOIN juser_detail USING(user_num)" + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%");
			}
			
			rs =pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}


	// 회원 목록 조회
	public List<UserVO> getListUserByAdmin(String keyfield,String keyword,int endRow,int startRow) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		List<UserVO> list =null;
		String sql =null;
		String sub_sql="";
		int cnt = 0;
		
		try {
			conn =DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql = "WHERE id LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE name LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE email LIKE ?";
			}
			
			sql ="SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT * FROM juser u LEFT OUTER JOIN juser_detail d USING(user_num) " + sub_sql + " ORDER BY reg_date DESC NULLS LAST)a) WHERE rnum>=? AND rnum<=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, startRow);
			pstmt.setInt(++cnt, endRow);
			
			rs= pstmt.executeQuery();
			list = new ArrayList<UserVO>();
			while(rs.next()) {
				UserVO user= new UserVO();
				user.setUser_num(rs.getInt("user_num"));
				user.setId(rs.getString("id"));
				user.setAuth(rs.getInt("auth"));
				user.setName(rs.getString("name"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setZipcode(rs.getString("zipcode"));
				user.setAddress1(rs.getString("address1"));
				user.setAddress2(rs.getString("address2"));
				user.setPhoto(rs.getString("photo"));
				user.setReg_date(rs.getDate("reg_date"));
				user.setModify_date(rs.getDate("modify_date"));
				
				list.add(user);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	//회원 정보 수정
		public void updateUserByAdmin(UserVO user)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//오토 커밋 해제
				conn.setAutoCommit(false);
				
				sql = "UPDATE juser SET auth=? WHERE user_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, user.getAuth());
				pstmt.setInt(2, user.getUser_num());
				//SQL문 실행
				pstmt.executeUpdate();
				
				sql = "UPDATE juser_detail SET name=?,phone=?,email=?,"
					+ "zipcode=?,address1=?,address2=?,modify_date=SYSDATE "
					+ "WHERE user_num=?";
				//PreparedStatement 객체 생성
				pstmt2 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt2.setString(1, user.getName());
				pstmt2.setString(2, user.getPhone());
				pstmt2.setString(3, user.getEmail());
				pstmt2.setString(4, user.getZipcode());
				pstmt2.setString(5, user.getAddress1());
				pstmt2.setString(6, user.getAddress2());
				pstmt2.setInt(7, user.getUser_num());
				//SQL 실행
				pstmt2.executeUpdate();
				
				//모든 SQL문이 정상적으로 실행
				conn.commit();
			}catch(Exception e) {
				//SQL문이 하나라도 실패하면
				conn.rollback();
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	}