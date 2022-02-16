package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class BoardDAO {
	//싱글턴 패턴
	 private static BoardDAO instance = new BoardDAO();
	 public static BoardDAO getInstance() {
		 return instance;
	 }
	 private BoardDAO() {}
	 
	 //글등록
	 public void insertBoard(BoardVO board)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션을 할당
			 conn = DBUtil.getConnection();
			 //SQL문 작성
			 sql = "INSERT INTO jboard (board_num,title,content,filename,ip,"
			 	 + "user_num) VALUES (jboard_seq.nextval,?,?,?,?,?)";
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
	 public int getBoardCount(String keyfield,String keyword)throws Exception{
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
				 else if(keyfield.equals("2")) sub_sql = "WHERE u.id LIKE ?";
				 else if(keyfield.equals("3")) sub_sql = "WHERE b.content LIKE ?";
			 }
			 
			 sql = "SELECT COUNT(*) FROM jboard b JOIN juser u USING(user_num) " + sub_sql;
			 
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
	 //목록
	 public List<BoardVO> getListBoard(int startRow, int endRow, 
			                  String keyfield, String keyword)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 List<BoardVO> list = null;
		 String sql = null;
		 String sub_sql = "";
		 int cnt = 0;
		 
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 
			 if(keyword != null && !"".equals(keyword)) {
				 if(keyfield.equals("1")) sub_sql = "WHERE b.title LIKE ?";
				 else if(keyfield.equals("2")) sub_sql = "WHERE u.id LIKE ?";
				 else if(keyfield.equals("3")) sub_sql = "WHERE b.content LIKE ?";
			 }
			 
			 sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
			 	 + "(SELECT * FROM jboard b JOIN juser u USING(user_num) "
			     + sub_sql + " ORDER BY b.board_num DESC)a) "
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
			 list = new ArrayList<BoardVO>();
			 while(rs.next()) {
				 BoardVO board = new BoardVO();
				 board.setBoard_num(rs.getInt("board_num"));
				 //HTML태그를 허용하지 않음
				 board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				 board.setHit(rs.getInt("hit"));
				 board.setReg_date(rs.getDate("reg_date"));
				 board.setId(rs.getString("id"));
				 
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
	 //글상세
	 public BoardVO getBoard(int board_num)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 BoardVO board = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션을 할당
			 conn = DBUtil.getConnection();
			 //SQL문 작성
			 sql = "SELECT * FROM jboard b JOIN juser u "
			 	+ "ON b.user_num=u.user_num WHERE b.board_num=?";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 //?에 데이터를 바인딩
			 pstmt.setInt(1, board_num);
			 //SQL문 실행해서 결과행을 ResultSet에 담음
			 rs = pstmt.executeQuery();
			 if(rs.next()) {
				 board = new BoardVO();
				 board.setBoard_num(rs.getInt("board_num"));
				 board.setTitle(rs.getString("title"));
				 board.setContent(rs.getString("content"));
				 board.setHit(rs.getInt("hit"));
				 board.setReg_date(rs.getDate("reg_date"));
				 board.setModify_date(rs.getDate("modify_date"));
				 board.setFilename(rs.getString("filename"));
				 board.setUser_num(rs.getInt("user_num"));
				 board.setId(rs.getString("id"));
			 }
			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(rs, pstmt, conn);
		 }
		 return board;
	 }
	 //조회수 증가
	 public void updateReadcount(int board_num)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션을 할당
			 conn = DBUtil.getConnection();
			 
			 //SQL문 작성
			 sql = "UPDATE jboard SET hit=hit+1 WHERE board_num=?";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 //?에 데이터를 바인딩
			 pstmt.setInt(1, board_num);
			 //SQL문 실행
			 pstmt.executeUpdate();
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	 }
	 //글수정
	 public void updateBoard(BoardVO board)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 String sub_sql = "";
		 int cnt = 0;
		 
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 
			 if(board.getFilename()!=null) {
				 sub_sql = ",filename=?";
			 }
			 
			 sql = "UPDATE jboard SET title=?,content=?,modify_date=SYSDATE" 
			     + sub_sql + ",ip=? WHERE board_num=?";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(++cnt, board.getTitle());
			 pstmt.setString(++cnt, board.getContent());
			 if(board.getFilename()!=null) {
				 pstmt.setString(++cnt, board.getFilename());
			 }
			 pstmt.setString(++cnt, board.getIp());
			 pstmt.setInt(++cnt, board.getBoard_num());
			 
			 //SQL문 실행
			 pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	 }
	 //파일삭제
	 public void deleteFile(int board_num)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 //SQL문 작성
			 sql = "UPDATE jboard SET filename='' WHERE board_num=?";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 //?에 데이터 바인딩
			 pstmt.setInt(1, board_num);
			 
			 //SQL문 실행
			 pstmt.executeUpdate();			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	 }
	 //글삭제
	 public void deleteBoard(int board_num)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 PreparedStatement pstmt2 = null;
		 String sql = null;
		 
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 //오토커밋 해제
			 conn.setAutoCommit(false);
			 
			 //댓글 삭제
			 sql = "DELETE FROM jboard_reply WHERE board_num=?";
			 //PreparedStatement 객체 생성
			 pstmt = conn.prepareStatement(sql);
			 //?에 데이터 바인딩
			 pstmt.setInt(1, board_num);
			 //SQL문 실행
			 pstmt.executeUpdate();
			 
			 //부모글 삭제
			 sql = "DELETE FROM jboard WHERE board_num=?";
			 //PrepardStatement 객체 생성
			 pstmt2 = conn.prepareStatement(sql);
			 //?에 데이터 바인딩
			 pstmt2.setInt(1, board_num);
			 pstmt2.executeUpdate();
			 
			 //정상적으로 모든 SQL문을 실행
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








