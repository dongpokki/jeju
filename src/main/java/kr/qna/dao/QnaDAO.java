package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	public void insertBoard(QnaVO qna) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn =DBUtil.getConnection();
			
			sql="INSERT INTO jboard_qna(qna_num,title,content,hit,viewable_check,reg_date,filename,ip,user_num) "
					+ "VALUES (jboard_qna_seq.nextval,?,?,?,?,SYSDATE,?,?,?)";
			
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
	public int getBoardCount(String keyfield,String keyword) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String sql=null;
		String sub_sql="";
		int count=0;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql="WHERE b.title LIKE ?";
				else if(keyfield.equals("2")) sub_sql="WHERE u.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql="WHERE b.content LIKE ?";
			}
			//sQL문 작성
			sql="SELECT COUNT(*) FROM jboard_qna b JOIN juser u USING(user_num) JOIN juser_detail d USING(user_num)"+sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null & !"".equals(keyword)) {
				pstmt.setString(1, "%"+keyword+"%");
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
	public List<QnaVO> getListBoard(int startRow,int endRow,String keyfield,String keyword) throws Exception{
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
				if(keyfield.equals("1")) sub_sql="WHERE b.title LIKE ?";
				else if(keyfield.equals("2")) sub_sql="WHERE u.id LIKE ?";
				else if(keyfield.equals("3")) sub_sql="WHERE b.content LIKE ?";
			}
			
			sql="SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM jboard_qna b JOIN juser u USING(user_num) JOIN juser_detail d USING(user_num)"
				+sub_sql + " ORDER BY u.auth DESC,b.qna_num DESC)a) "
				+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
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
				qna.setViewable_check(rs.getInt("viewable_check"));;
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
			
			sql="SELECT * FROM jboard_qna b JOIN zuser u ON b.user_num=u.user_num "
					+ "JOIN zmember_detail d ON b.user_numer=d.user_num "
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
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return qna;
	}
}
