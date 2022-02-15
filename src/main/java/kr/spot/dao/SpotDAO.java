package kr.spot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.spot.vo.SpotCmtVO;
import kr.spot.vo.SpotGoodVO;
import kr.spot.vo.SpotVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class SpotDAO {
	// 싱글턴 패턴
	private static SpotDAO instance = new SpotDAO();

	public static SpotDAO getInstance() {
		return instance;
	}

	private SpotDAO() {
	}

	// 추천 장소 등록
	public void insertSpot(SpotVO Spot) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			sql = "INSERT INTO jboard_spot (spot_num, title, content, filename, category, user_num)"
					+ "VALUES (jboard_spot_seq.nextval, ?,?,?,?,?)";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Spot.getTitle());
			pstmt.setString(2, Spot.getContent());
			pstmt.setString(3, Spot.getFilename());
			pstmt.setInt(4, Spot.getCategory());
			pstmt.setInt(5, Spot.getUser_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 추천 장소 수정
	public void updateSpotBoard(SpotVO spot) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if (spot.getFilename() != null) {
				sub_sql = ", filename=?";
			}

			sql = "UPDATE jboard_spot SET title=?, content=?, modify_date=SYSDATE" + sub_sql
					+ ", category=? WHERE spot_num=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(++cnt, spot.getTitle());
			pstmt.setString(++cnt, spot.getContent());
			if (spot.getFilename() != null) {
				pstmt.setString(++cnt, spot.getFilename());
			}
			pstmt.setInt(++cnt, spot.getCategory());
			pstmt.setInt(++cnt, spot.getSpot_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 추천 장소 삭제
	public void deleteSpotBoard(int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			sql = "DELETE FROM jboard_spot WHERE spot_num=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, spot_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 파일삭제
	public void deleteFile(int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE jboard_spot SET filename='' WHERE spot_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, spot_num);

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
	public int getSpotBoardCount(String keyword, int category) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			if (keyword != null && !"".equals(keyword)) {// 검색어가 있는 경우
				sub_sql = "WHERE content LIKE ?";
				if (category != 0) {
					sub_sql += " AND category=? ";
				}
			} else {// 검색어가 없는 경우
				if (category != 0) {
					sub_sql = "WHERE category=? ";
				}
			}

			// 전체 또는 검색 레코드 수
			sql = "SELECT COUNT(*) FROM jboard_spot " + sub_sql;
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {// 검색어가 있는 경우
				pstmt.setString(++cnt, "%" + keyword + "%");
				if (category != 0) {
					pstmt.setInt(++cnt, category);
				}
			} else {// 검색어가 없는 경우
				if (category != 0) {
					pstmt.setInt(++cnt, category);
				}
			}

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
	public List<SpotVO> getList(int startRow, int endRow, String keyword, int category, String sort) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SpotVO> list = null;
		String sql = null;
		String sub_sql = "";
		String sub_sql2 = sort;
		String sub_sql3 = "";

		int cnt = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if (category != 0) {
				sub_sql = " WHERE category=? ";
			}
			if (sort != null && sort.equals("good")) {
				sub_sql2 = "good";
			} else if (sort == null || sort.equals("spot_num")) {
				sub_sql2 = "spot_num";
			}
			if (keyword != null && !"".equals(keyword)) {// 검색어가 있는 경우
				sub_sql3 = " WHERE content LIKE ? ";
			}

			sql = "SELECT * FROM ( SELECT aa.*, rownum rnum FROM "
					+ "(SELECT * FROM (SELECT content, spot_num FROM jboard_spot) JOIN "
					+ "(SELECT DISTINCT spot_num, title, filename, hit, good, category FROM jboard_spot b LEFT OUTER JOIN "
					+ "(SELECT spot_num, COUNT(*) good FROM jgood_spot GROUP BY spot_num ) g USING (spot_num) "
					+ sub_sql + "ORDER BY " + sub_sql2 + " DESC NULLS LAST )a " + "USING (spot_num) " + sub_sql3
					+ ") aa ) WHERE rnum>=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);

			if (category != 0) {
				pstmt.setInt(++cnt, category);
			}
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}

			pstmt.setInt(++cnt, startRow);
			pstmt.setInt(++cnt, endRow);
			// SQL문을 테이블에 반영하고 결과행들을 ResultSet 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<SpotVO>();
			while (rs.next()) {
				SpotVO spot = new SpotVO();

				spot.setSpot_num(rs.getInt("spot_num"));
				spot.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				spot.setFilename(rs.getString("filename"));
				spot.setContent(rs.getString("content"));

				// 자바빈(VO)을 ArrayList에 저장
				list.add(spot);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 추천 장소 글 상세
	public SpotVO getSpotBoard(int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SpotVO spot = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM jboard_spot WHERE spot_num = ?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, spot_num);
			// SQL문 테이블에 반영하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if (rs.next()) {
				spot = new SpotVO();
				spot.setSpot_num(rs.getInt("spot_num"));
				spot.setTitle(rs.getString("title"));
				spot.setContent(rs.getString("content"));
				spot.setReg_date(rs.getDate("reg_date"));
				spot.setModify_date(rs.getDate("modify_date"));
				spot.setFilename(rs.getString("filename"));
				spot.setCategory(rs.getInt("category"));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return spot;
	}

	// 조회수 증가
	public void updateReadcount(int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			// SQL문 작성
			sql = "UPDATE jboard_spot SET hit=hit+1 WHERE spot_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터를 바인딩
			pstmt.setInt(1, spot_num);
			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 좋아요 여부 확인
	public int checkGood(int user_num, int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			// 전체 또는 검색 레코드 수
			sql = "SELECT COUNT(*) FROM jgood_spot WHERE user_num = ? AND spot_num = ?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			pstmt.setInt(2, spot_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		return count;
	}

	// 좋아요 기능
	public void jGood(SpotGoodVO good) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			sql = "INSERT INTO jgood_spot VALUES (?,?,?)";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, good.getSpot_num());
			pstmt.setInt(2, good.getUser_num());
			pstmt.setInt(3, 1);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// SQL문이 하나라도 실패하면
			conn.rollback();
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 좋아요 개수
	public int getSpotGoodCount(int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			// 전체 또는 검색 레코드 수
			sql = "SELECT COUNT(*) FROM jGood_spot WHERE spot_num = ?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, spot_num);
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

	// 댓글 등록
	public void insertCmtSpot(SpotCmtVO spotCmt) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO jcmt_spot (spotcmt_num,cmt_content,user_num,spot_num) VALUES (jcmt_spot_seq.nextval,?,?,?)";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, spotCmt.getCmt_content());
			pstmt.setInt(2, spotCmt.getUser_num());
			pstmt.setInt(3, spotCmt.getSpot_num());
			// SQL문 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 댓글 갯수
	public int getCmtSpotCount(int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT COUNT(*) FROM jcmt_spot s JOIN juser u USING(user_num) JOIN juser_detail d USING(user_num) WHERE s.spot_num=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, spot_num);

			// SQL문을 실행해서 결과행을 ResultSet에 담음
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

	// 댓글 목록
	public List<SpotCmtVO> getListCmtSpot(int startRow, int endRow, int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SpotCmtVO> list = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT s.spotcmt_num, TO_CHAR(s.reg_date,'YYYY-MM-DD HH24:MI:SS') reg_date,"
					+ "TO_CHAR(s.modify_date,'YYYY-MM-DD HH24:MI:SS') modify_date,"
					+ "s.cmt_content,s.spot_num,user_num,u.id,d.name,d.photo "
					+ "FROM jcmt_spot s JOIN juser u USING(user_num) "
					+ "JOIN juser_detail d USING(user_num) WHERE s.spot_num=? "
					+ "ORDER BY s.spotcmt_num DESC)a) WHERE rnum>=? AND rnum<=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, spot_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			// SQL문을 실행해서 결과행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			System.out.println(sql);
			list = new ArrayList<SpotCmtVO>();
			while (rs.next()) {
				SpotCmtVO cmt = new SpotCmtVO();
				cmt.setSpotcmt_num(rs.getInt("spotcmt_num"));
				cmt.setReg_date(DurationFromNow.getTimeDiffLabel(rs.getString("reg_date")));
				if (rs.getString("modify_date") != null) {
					cmt.setModify_date(DurationFromNow.getTimeDiffLabel(rs.getString("modify_date")));
				}
				cmt.setCmt_content(StringUtil.useBrHtml(rs.getString("cmt_content")));
				cmt.setSpot_num(rs.getInt("spot_num"));
				cmt.setUser_num(rs.getInt("user_num"));
				cmt.setId(rs.getString("id"));
				cmt.setUser_photo(rs.getString("photo"));

				list.add(cmt);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}// 댓글 상세

	public SpotCmtVO getCmtspot(int spotcmt_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		SpotCmtVO cmt = null;
		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM jcmt_spot s JOIN juser_detail u USING(user_num) JOIN juser u USING(user_num) WHERE spotcmt_num=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, spotcmt_num);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				cmt = new SpotCmtVO();
				cmt.setSpotcmt_num(rs.getInt("spotcmt_num"));
				cmt.setSpot_num(rs.getInt("spot_num"));
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
	public void updateCmtSpot(SpotCmtVO cmt) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();

			sql = "UPDATE jcmt_spot SET cmt_content=?,modify_date=SYSDATE WHERE spotcmt_num=? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmt.getCmt_content());
			pstmt.setInt(2, cmt.getSpotcmt_num());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}// 댓글 삭제

	public void deleteCmtSpot(int spotcmt_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();

			sql = "DELETE FROM jcmt_spot WHERE spotcmt_num=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, spotcmt_num);

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// [정동윤 작성] 메인에 노출할 BEST3 spot 구하기
	public List<SpotVO> getRankingSpot() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SpotVO> list = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			// sql문 작성
			// 게시글별로 추천수의 합(total_good)이 높은 순으로 랭킹1~3위 까지 3개의 컬럼 조회 [추천수(good)이 같은 경우에는,
			// 게시글번호(spot_num)이 낮은(먼저 등록한 순)순으로 추가 비교]
			sql = "select b.spot_num,b.title,b.content,b.filename,b.category,u.total_good,u.rank from jboard_spot b join (select * from (select spot_num,total_good,row_number() over (order by total_good desc,spot_num) rank from(select spot_num,count(*) total_good from jgood_spot where good =1 group by spot_num)) where rank<4) u on b.spot_num = u.spot_num";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// sql문 수행하여 결과 집합을 rs에 담음
			rs = pstmt.executeQuery();

			list = new ArrayList<SpotVO>();

			while (rs.next()) {
				SpotVO spot = new SpotVO();

				spot.setSpot_num(rs.getInt("spot_num"));
				spot.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				spot.setContent(rs.getString("content"));
				spot.setFilename(rs.getString("filename"));
				spot.setCategory(rs.getInt("category"));

				// 자바빈(VO)을 ArrayList에 저장
				list.add(spot);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// [정동윤 작성] 마이페이지에 노출할 내가 추천한 spot 구하기
	public List<SpotVO> MyGoodSpot(int startRow, int endRow, int user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SpotVO> list = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			// sql문 작성
			sql = "select * from (select a.*, rownum rnum from (select b.spot_num,b.title,b.content,b.filename,b.category,g.good from jboard_spot b join jgood_spot g on b.spot_num = g.spot_num where g.user_num=? and good=1 order by b.spot_num)a) where rnum>=? and rnum<=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// ?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			// sql문 수행하여 결과 집합을 rs에 담음
			rs = pstmt.executeQuery();

			list = new ArrayList<SpotVO>();

			while (rs.next()) {
				SpotVO spot = new SpotVO();

				spot.setSpot_num(rs.getInt("spot_num"));
				spot.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				spot.setContent(rs.getString("content"));
				spot.setFilename(rs.getString("filename"));
				spot.setCategory(rs.getInt("category"));

				// 자바빈(VO)을 ArrayList에 저장
				list.add(spot);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// [정동윤 작성] 마이페이지에 노출할 내가 추천한 spot count구하기
	public int MyGoodSpotCount(int session_user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션풀 할당
			conn = DBUtil.getConnection();

			sql = "select count(*) from jgood_spot where user_num=? and good=1";

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
}