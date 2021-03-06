package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardCmtVO;
import kr.board.vo.BoardGoodVO;
import kr.board.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class BoardDAO {
	// 싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();

	public static BoardDAO getInstance() {
		return instance;
	}

	private BoardDAO() {
	}

	// 글등록
	public void insertBoard(BoardVO board) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO jboard (board_num,title,content,filename,ip,course,"// 소진님
					+ "user_num,notice) VALUES (jboard_seq.nextval,?,?,?,?,?,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터를 바인딩
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getFilename());
			pstmt.setString(4, board.getIp());
			pstmt.setString(5, board.getCourse());// 소진님
			pstmt.setInt(6, board.getUser_num());
			pstmt.setInt(7, board.getNotice());
			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 총 레코드 수(검색 레코드 수)
	public int getBoardCount(String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if (keyword != null && !"".equals(keyword)) {
				if (keyfield.equals("1"))
					sub_sql = "WHERE b.title LIKE ?";
				else if (keyfield.equals("2"))
					sub_sql = "WHERE u.id LIKE ?";
				else if (keyfield.equals("3"))
					sub_sql = "WHERE b.content LIKE ?";
			}

			sql = "SELECT COUNT(*) FROM jboard b JOIN juser u USING(user_num) " + sub_sql;

			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%");
			}

			// SQL문을 실행하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}

	// 목록
	public List<BoardVO> getListBoard(int startRow, int endRow, String keyfield, String keyword, String sort)
			throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		String sub_sql2 = "";
		int cnt = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if (keyword != null && !"".equals(keyword)) {
				if (keyfield.equals("1"))
					sub_sql = "WHERE title LIKE ?";
				else if (keyfield.equals("2"))
					sub_sql = "WHERE id LIKE ?";
				else if (keyfield.equals("3"))
					sub_sql = "WHERE content LIKE ?";
			}
			if (sort != null && sort.equals("hit")) {// 조회수 정렬
				sub_sql2 = "ORDER BY notice DESC, hit DESC";
			} else if (sort == null || sort.equals("board_num")) {// 게시글 번호로 정렬 (내림차순)
				sub_sql2 = "ORDER BY notice DESC, board_num DESC";
			} else {
				sub_sql2 = "ORDER BY notice DESC, good DESC NULLS LAST, notice DESC, hit DESC"; // 좋아요 정렬, 좋아요가 0일 경우
																								// 조회수로 정렬
			}

			sql = "SELECT * FROM ( SELECT a.*, rownum rnum FROM (SELECT * FROM "
					// content가 clob 타입이라 ORDER BY가 안돼서 따로 SELECT한 뒤 id 값을 불러오기 위한 juser 테이블과 JOIN
					+ "(SELECT * FROM (SELECT content, board_num, user_num FROM jboard) JOIN juser USING(user_num)) JOIN "
					// content를 제외한 다른 컬럼을 중복 값을 제외한 뒤 JOIN 
					+ "(SELECT DISTINCT board_num, title, hit, reg_date, notice, good FROM jboard LEFT OUTER JOIN "
					// 좋아요 개수 COUNT한 뒤 JOIN
					+ "(SELECT board_num, COUNT(*) good FROM jgood_board GROUP BY board_num ) USING (board_num) ) USING (board_num) "
					// 먼저 조건문 실행한 뒤 rnum 생성
					+ sub_sql + sub_sql2 + " ) a ) WHERE rnum>=? AND rnum <=? ";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, startRow);
			pstmt.setInt(++cnt, endRow);

			// SQL문을 실행해서 결과행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while (rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				// HTML태그를 허용하지 않음
				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setId(rs.getString("id"));
				board.setContent(rs.getString("content"));// 소진님
				board.setNotice(rs.getInt("notice"));

				// BoardVO를 ArrayList에 저장
				list.add(board);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 관리자 공지사항 값 수정
	public void updateNoticeBoard(BoardVO board) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			sql = "UPDATE jboard SET notice=1 WHERE user_num=1";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// SQL문 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 글상세
	public BoardVO getBoard(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO board = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM jboard b JOIN juser u " + "ON b.user_num=u.user_num WHERE b.board_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터를 바인딩
			pstmt.setInt(1, board_num);
			// SQL문 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if (rs.next()) {
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
				board.setCourse(rs.getString("course"));// 소진님
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return board;
	}

	// 조회수 증가
	public void updateReadcount(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			// SQL문 작성
			sql = "UPDATE jboard SET hit=hit+1 WHERE board_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터를 바인딩
			pstmt.setInt(1, board_num);
			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 글수정
	public void updateBoard(BoardVO board) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if (board.getFilename() != null) {
				sub_sql = ",filename=?";
			}

			sql = "UPDATE jboard SET title=?,content=?,modify_date=SYSDATE" + sub_sql + ",ip=? WHERE board_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, board.getTitle());
			pstmt.setString(++cnt, board.getContent());
			if (board.getFilename() != null) {
				pstmt.setString(++cnt, board.getFilename());
			}
			pstmt.setString(++cnt, board.getIp());
			pstmt.setInt(++cnt, board.getBoard_num());

			// SQL문 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 파일삭제
	public void deleteFile(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE jboard SET filename='' WHERE board_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, board_num);

			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 글삭제
	public void deleteBoard(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// 오토커밋 해제
			conn.setAutoCommit(false);

			// 댓글 삭제
			sql = "DELETE FROM jcmt_board WHERE board_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, board_num);
			// SQL문 실행
			pstmt.executeUpdate();

			// 부모글 삭제
			sql = "DELETE FROM jboard WHERE board_num=?";
			// PrepardStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt2.setInt(1, board_num);
			pstmt2.executeUpdate();

			// 정상적으로 모든 SQL문을 실행
			conn.commit();
		} catch (Exception e) {
			// SQL문이 하나라도 실패하면
			conn.rollback();
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 댓글 등록
	public void insertCmtBoard(BoardCmtVO boardCmt) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();

			sql = "INSERT INTO jcmt_board (boardcmt_num,cmt_content,user_num,board_num) VALUES (jcmt_board_seq.nextval,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, boardCmt.getCmt_content());
			pstmt.setInt(2, boardCmt.getUser_num());
			pstmt.setInt(3, boardCmt.getBoard_num());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 댓글 갯수
	public int getCmtBoardCount(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT COUNT(*) FROM jcmt_board WHERE board_num=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, board_num);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}

	// 댓글 목록
	public List<BoardCmtVO> getListCmtBoard(int startRow, int endRow, int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardCmtVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT b.boardcmt_num, TO_CHAR(b.reg_date,'YYYY-MM-DD HH24:MI:SS') reg_date,"
					+ "TO_CHAR(b.modify_date,'YYYY-MM-DD HH24:MI:SS') modify_date,"
					+ "b.cmt_content,b.board_num,user_num,u.id,d.name,d.photo "
					+ "FROM jcmt_board b JOIN juser u USING(user_num) "
					+ "JOIN juser_detail d USING(user_num) WHERE b.board_num=? "
					+ "ORDER BY b.boardcmt_num ASC)a) WHERE rnum>=? AND rnum<=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, board_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rs = pstmt.executeQuery();
			list = new ArrayList<BoardCmtVO>();
			while (rs.next()) {
				BoardCmtVO cmt = new BoardCmtVO();
				cmt.setBoardcmt_num(rs.getInt("boardcmt_num"));
				cmt.setReg_date(DurationFromNow.getTimeDiffLabel(rs.getString("reg_date")));
				if (rs.getString("modify_date") != null) {
					cmt.setModify_date(DurationFromNow.getTimeDiffLabel(rs.getString("modify_date")));
				}
				cmt.setCmt_content(StringUtil.useBrHtml(rs.getString("cmt_content")));
				cmt.setBoard_num(rs.getInt("board_num"));
				cmt.setUser_num(rs.getInt("user_num"));
				cmt.setId(rs.getString("id"));
				cmt.setUser_photo(rs.getString("photo"));

				list.add(cmt);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 댓글 상세
	public BoardCmtVO getCmtBoard(int boardcmt_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		BoardCmtVO cmt = null;
		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM jcmt_board b JOIN juser_detail u USING(user_num) JOIN juser u USING(user_num) WHERE boardcmt_num=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardcmt_num);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				cmt = new BoardCmtVO();
				cmt.setBoardcmt_num(rs.getInt("boardcmt_num"));
				cmt.setBoard_num(rs.getInt("board_num"));
				cmt.setUser_num(rs.getInt("user_num"));
				cmt.setId(rs.getString("id"));
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return cmt;
	}

	// 댓글 수정
	public void updateCmtBoard(BoardCmtVO cmt) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();

			sql = "UPDATE jcmt_board SET cmt_content=?,modify_date=SYSDATE WHERE boardcmt_num=? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmt.getCmt_content());
			pstmt.setInt(2, cmt.getBoardcmt_num());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 댓글 삭제
	public void deleteCmtBoard(int boardcmt_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();

			sql = "DELETE FROM jcmt_board WHERE boardcmt_num=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardcmt_num);

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 좋아요 여부 확인
	public int checkGood(int user_num, int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT COUNT(*) FROM jgood_board WHERE user_num = ? AND board_num = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			pstmt.setInt(2, board_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return count;
	}

	// 좋아요 기능
	public void jGood(BoardGoodVO good) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();

			sql = "INSERT INTO jgood_board VALUES (?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, good.getBoard_num());
			pstmt.setInt(2, good.getUser_num());
			pstmt.setInt(3, 1);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 좋아요 취소 기능
	public void cancelGood(int board_num, int user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();

			sql = "DELETE FROM jgood_board WHERE board_num = ? AND user_num = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, user_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 좋아요 개수
	public int getBoardGoodCount(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT COUNT(*) FROM jGood_board WHERE board_num = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}

	// ============================================================================================================================================
	// [정동윤 작성] 마이페이지에 노출할 내가 추천한 board 구하기
	public List<BoardVO> MyGoodBoard(int user_num, int startRow, int endRow) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			// sql문 작성
			sql = "select * from (select a.*, rownum rnum from (select b.board_num,b.title,g.good from jboard b join jgood_board g on b.board_num = g.board_num where g.user_num=? and good=1 order by b.board_num)a) where rnum>=? and rnum<=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// ?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			// sql문 수행하여 결과 집합을 rs에 담음
			rs = pstmt.executeQuery();

			list = new ArrayList<BoardVO>();

			while (rs.next()) {
				BoardVO board = new BoardVO();

				board.setBoard_num(rs.getInt("board_num"));
				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));

				// 자바빈(VO)을 ArrayList에 저장
				list.add(board);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// [정동윤 작성] 마이페이지에 노출할 내가 추천한 board count구하기
	public int MyGoodBoardCount(int session_user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션풀 할당
			conn = DBUtil.getConnection();

			sql = "select count(*) from jgood_board where user_num=? and good=1";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// ?에 데이터 바인딩
			pstmt.setInt(1, session_user_num);

			// SQL문 수행하여 결과 집합을 rs에 담음
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}

	// [정동윤 작성] - 마이페이지에서 노출할 내가 작성한 게시글 내역
	// 내가 작성한 게시글 리스트 카운트 구하기
	public int getmyBoardCount(int session_user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			// sQL문 작성
			sql = "select count(*) from jboard where user_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, session_user_num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}

	// [정동윤 작성] - 마이페이지에서 노출할 내가 작성한 게시글 내역
	// 내가 작성한 게시글 리스트 조회
	public List<BoardVO> getmyBoardList(int session_user_num, int startRow, int endRow) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			// sql문 작성
			sql = "select * from (select a.*,rownum rnum from (select board_num,title,user_num from jboard where user_num = ?)a) where rnum>=? AND rnum<=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, session_user_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rs = pstmt.executeQuery();

			list = new ArrayList<BoardVO>();

			while (rs.next()) {
				BoardVO board = new BoardVO();
				board.setTitle(rs.getString("title"));
				board.setBoard_num(rs.getInt("board_num"));

				// BoardVO를 ArrayList에 저장
				list.add(board);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

}