package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.qna.vo.QnaVO;
import kr.util.DBUtil;

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
			
			sql="INSERT INTO zboard(board_num,title,content,filename,ip,mem_num) "
					+ "VALUES (zboard_seq.nextval,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getTitle());
			pstmt.setString(2, qna.getContent());
			pstmt.setString(3, qna.getFilename());
			pstmt.setString(4, qna.getIp());
			pstmt.setInt(5, qna.getUser_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
}
