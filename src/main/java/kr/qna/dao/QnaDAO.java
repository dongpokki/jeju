package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.qna.vo.QnaCmtVO;
import kr.qna.vo.QnaVO;
import kr.user.vo.UserVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class QnaDAO {
	//싱글턴 패턴
	private static QnaDAO instance = new QnaDAO();
	public static QnaDAO getInstance() {
		return instance;
	}
	private QnaDAO() {};
	
	//글등록 
	public void insertQna(QnaVO qna) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn =DBUtil.getConnection();
			
			sql="INSERT INTO jboard_qna(qna_num,title,content,hit,viewable_check,filename,ip,user_num) "
					+ "VALUES (jboard_qna_seq.nextval,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getTitle());
			pstmt.setString(2, qna.getContent());
			pstmt.setInt(3, qna.getHit());
			pstmt.setInt(4, qna.getViewable_check());
			pstmt.setString(5, qna.getFilename());
			pstmt.setString(6, qna.getIp());
			pstmt.setInt(7, qna.getUser_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//총레코드 수 (검색 레코드 수)
	public int getQnaCount(String keyfield,String keyword) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String sql=null;
		String sub_sql="";
		int count=0;
		int cnt=0;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("0")) sub_sql="WHERE q.title LIKE ? OR u.id LIKE ? OR q.content LIKE ?";				
				if(keyfield.equals("1")) sub_sql="WHERE q.title LIKE ?";
				else if(keyfield.equals("2")) sub_sql="WHERE u.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql="WHERE q.content LIKE ?";
			}
			//sQL문 작성
			sql="SELECT COUNT(*) FROM jboard_qna q JOIN juser u USING(user_num) LEFT JOIN juser_detail d USING(user_num)"+sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null & !"".equals(keyword)) {
				if(keyfield.equals("0")) {
					pstmt.setString(++cnt, "%"+keyword+"%");
					pstmt.setString(++cnt, "%"+keyword+"%");
					pstmt.setString(++cnt, "%"+keyword+"%");
				}else {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
			}
			
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

	//목록 
	public List<QnaVO> getListQna(int startRow,int endRow,String keyfield,String keyword) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		List<QnaVO> list =null;
		String sql =null;
		String sub_sql="";
		int cnt=0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("0")) sub_sql="WHERE q.title LIKE ? OR u.id LIKE ? OR q.content LIKE ?";
				else if(keyfield.equals("1")) sub_sql="WHERE q.title LIKE ?";
				else if(keyfield.equals("2")) sub_sql="WHERE u.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql="WHERE q.content LIKE ?";
			}
			
			
			sql ="SELECT (SELECT COUNT(*) FROM jcmt_qna c WHERE c.qna_num =a.qna_num) as cnt,"
					+ " a.* FROM (SELECT b.*, rownum rnum FROM "
					+ "(SELECT * FROM jboard_qna q JOIN juser u USING(user_num) "
					+ "LEFT JOIN juser_detail d USING(user_num) "
					+ sub_sql
					+ " ORDER BY q.qna_num DESC)b)a "
					+ "WHERE a.rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("0")) {
					pstmt.setString(++cnt, "%"+keyword+"%");
					pstmt.setString(++cnt, "%"+keyword+"%");
					pstmt.setString(++cnt, "%"+keyword+"%");
				}else {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
			}
			pstmt.setInt(++cnt, startRow);
			pstmt.setInt(++cnt, endRow);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<QnaVO>();
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				//HTML 태그를 허용하지않음
				qna.setTitle(StringUtil.useNoHtml(rs.getString("title")));//태그 불허
				qna.setHit(rs.getInt("hit"));
				qna.setReg_date(rs.getDate("reg_date"));
				qna.setUser_num(rs.getInt("user_num"));
				qna.setId(rs.getString("id"));
				qna.setName(rs.getString("name"));
				qna.setViewable_check(rs.getInt("viewable_check"));
				qna.setCmt_count(rs.getString("cnt"));
				
				//BoardVO를 ArrayList에 저장
				list.add(qna);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//글 상세 정보
	public QnaVO getQna(int qna_num) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String sql =null;
		QnaVO qna = null;
		try {
			conn = DBUtil.getConnection();
			
			sql="SELECT * FROM jboard_qna b JOIN juser u USING(user_num) "
					+ "LEFT JOIN juser_detail d USING(user_num) "
					+ "WHERE b.qna_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setTitle(rs.getString("title"));
				qna.setContent(rs.getString("content"));
				qna.setHit(rs.getInt("hit"));
				qna.setViewable_check(rs.getInt("viewable_check"));
				qna.setReg_date(rs.getDate("reg_date"));
				qna.setModify_date(rs.getDate("modify_date"));
				qna.setFilename(rs.getString("filename"));
				qna.setIp(rs.getString("ip"));
				qna.setUser_num(rs.getInt("user_num"));
				qna.setName(rs.getString("name"));
				qna.setId(rs.getString("id"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return qna;
	}
	//조회수증가
	 public void updateReadCount(int qna_num) throws Exception{
		 Connection conn=null;
		 PreparedStatement pstmt =null;
		 String sql=null;
		 try {
			 conn=DBUtil.getConnection();
			 
			 sql="UPDATE jboard_qna SET hit=hit+1 WHERE qna_num=?";
			 
			 pstmt =conn.prepareStatement(sql);
			 pstmt.setInt(1, qna_num);
			 
			 pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	 }
	 //글 수정
	public void updateQna(QnaVO qna) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		String sql =null;
		String sub_sql="";
		int cnt =0;
		try {
			conn=DBUtil.getConnection();
			
			if(qna.getFilename()!=null) {
				sub_sql=",filename=?";
			}
			
			sql="UPDATE jboard_qna SET title=?,content=?,modify_date=SYSDATE,viewable_check=?"
					+sub_sql +",ip=? WHERE qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, qna.getTitle());
			pstmt.setString(++cnt, qna.getContent());
			pstmt.setInt(++cnt, qna.getViewable_check());
			if(qna.getFilename()!=null) {
				pstmt.setString(++cnt, qna.getFilename());
			}
			pstmt.setString(++cnt, qna.getIp());
			pstmt.setInt(++cnt, qna.getQna_num());
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//파일 삭제
	public void deleteFile(int qna_num) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		String sql =null;
		try{
			conn =DBUtil.getConnection();
			
			sql="UPDATE jboard_qna SET filename='' WHERE qna_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	 //글 삭제
	public void deleteQna(int qna_num) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmt2 =null;
		String sql=null;
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql="DELETE FROM jcmt_qna WHERE qna_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, qna_num);
			pstmt.executeUpdate();
			
			sql="DELETE FROM jboard_qna WHERE qna_num=?";
			
			pstmt2= conn.prepareStatement(sql);
			pstmt2.setInt(1, qna_num);
			
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
		}
	}
	
	/////////////////////////////////////////
	//댓글
	//댓글 등록
	public void insertCmtQna(QnaCmtVO qnaCmtVO) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		String sql =null;
		try {
			conn =DBUtil.getConnection();
			
			sql ="INSERT INTO jcmt_qna(qnacmt_num,qna_num,cmt_content,user_num) "
					+ "VALUES (jcmt_qna_seq.nextval,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaCmtVO.getQna_num());
			pstmt.setString(2, qnaCmtVO.getCmt_content());
			pstmt.setInt(3, qnaCmtVO.getUser_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//댓글 갯수
	public int getCmtQnaCount(int qna_num) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String sql=null;
		int count=0;
		try {
			conn=DBUtil.getConnection();
			
			sql="SELECT COUNT(*) FROM jcmt_qna c JOIN juser u USING(user_num) LEFT JOIN juser_detail d USING(user_num) WHERE c.qna_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			rs = pstmt.executeQuery();
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
	
	//댓글 목록
	public List<QnaCmtVO> getListCmtQna(int startRow,int endRow,int qna_num) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		List<QnaCmtVO> list=null;
		String sql=null;
		try {
			conn =DBUtil.getConnection();
			
			sql="SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT c.qnacmt_num, c.reg_date,"
					+ "c.modify_date,"
					+ "c.cmt_content,c.qna_num,user_num,u.id,d.name "
					+ "FROM jcmt_qna c JOIN juser u USING(user_num) "
					+ "LEFT JOIN juser_detail d USING(user_num) WHERE c.qna_num=? "
					+ "ORDER BY c.qnacmt_num DESC)a) WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rs=pstmt.executeQuery();
			list = new ArrayList<QnaCmtVO>();
			while(rs.next()) {
				QnaCmtVO cmt = new QnaCmtVO();
				cmt.setQnacmt_num(rs.getInt("qnacmt_num"));
				cmt.setReg_date(rs.getDate("reg_date"));
				if(rs.getString("modify_date")!=null) {
					cmt.setModify_date(rs.getDate("modify_date"));
				}
				cmt.setCmt_content(StringUtil.useBrHtml(rs.getString("cmt_content")));
				cmt.setQna_num(rs.getInt("qna_num"));
				cmt.setQnacmt_num(rs.getInt("qnacmt_num"));
				cmt.setUser_num(rs.getInt("user_num"));
				cmt.setName(rs.getString("name"));
				
				list.add(cmt);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//댓글 상세
	public QnaCmtVO getCmtQna(int qnacmt_num) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		String sql=null;
		QnaCmtVO cmt = null;
		try {
			conn = DBUtil.getConnection();
			
			sql="SELECT * FROM jcmt_qna c JOIN juser_detail u USING(user_num) WHERE qnacmt_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnacmt_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cmt = new QnaCmtVO();
				cmt.setQnacmt_num(rs.getInt("qnacmt_num"));
				cmt.setQna_num(rs.getInt("qna_num"));
				cmt.setUser_num(rs.getInt("user_num"));
				cmt.setName(rs.getString("name"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return cmt;
	}
	//댓글 수정
	public void updateCmtQna(QnaCmtVO cmt) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt =null;
		String sql=null;
		try {
			conn =DBUtil.getConnection();
			
			sql="UPDATE jcmt_qna SET cmt_content=?,modify_date=SYSDATE WHERE qnacmt_num=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmt.getCmt_content());
			pstmt.setInt(2, cmt.getQnacmt_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 삭제 
	public void deleteCmtQna(int qnacmt_num) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt =null;
		String sql=null;
		try {
			conn = DBUtil.getConnection();
			
			sql="DELETE FROM jcmt_qna WHERE qnacmt_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnacmt_num);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// [정동윤 작성] - 마이페이지에서 노출할 내 문의사항 내역
	// 내가 작성한 문의사항 리스트 카운트 구하기
	public int getmyListQnaCount(String session_user_id) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String sql=null;
		int count=0;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//sQL문 작성
			sql="SELECT COUNT(*) FROM jboard_qna b JOIN juser u USING(user_num) JOIN juser_detail d USING(user_num) where id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, session_user_id);
			
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
	
	
	// 내가 작성한 문의사항 리스트 조회
	public List<QnaVO> getmyListQna(String session_user_id,int startRow,int endRow) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		List<QnaVO> list =null;
		String sql =null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//sql문 작성
			sql="SELECT (SELECT COUNT(*) FROM jcmt_qna c WHERE c.qna_num = a.qna_num) as cnt,a.* FROM (SELECT b.*, rownum rnum FROM (SELECT * FROM jboard_qna q JOIN juser u USING(user_num) LEFT JOIN juser_detail d USING(user_num) where u.id=? ORDER BY q.qna_num DESC)b)a WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, session_user_id);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<QnaVO>();
			
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setId(rs.getString("id"));
				qna.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				qna.setCmt_count(rs.getString("cnt"));
				
				//BoardVO를 ArrayList에 저장
				list.add(qna);				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}



}
