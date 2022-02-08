package kr.qna.dao;

public class QnaDAO {
	//싱글턴 패턴
	private static QnaDAO instance = new QnaDAO();
	public static QnaDAO getInstance() {
		return instance;
	}
	private QnaDAO() {};
	
	//글등록
	
}
