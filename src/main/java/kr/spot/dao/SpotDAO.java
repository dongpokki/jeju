package kr.spot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.spot.vo.SpotVO;
import kr.util.DBUtil;
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
	public void updateSpotBoard(SpotVO Spot) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if (Spot.getFilename() != null) {
				sub_sql = ",filename=?";
			}

			sql = "UPDATE jboard_spot SET title=?, content=?, modify_date=SYSDATE" + sub_sql
					+ ", user_num=? WHERE spot_num=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(++cnt, Spot.getTitle());
			pstmt.setString(++cnt, Spot.getContent());
			if (Spot.getFilename() != null) {
				pstmt.setString(++cnt, Spot.getFilename());
			}
			pstmt.setInt(++cnt, Spot.getUser_num());
			pstmt.setInt(++cnt, Spot.getUser_num());
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
					System.out.println(cnt);
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
	public List<SpotVO> getList(int startRow, int endRow, String keyword, int category) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SpotVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
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

			sql = "SELECT * FROM ( SELECT a.*, rownum rnum FROM ( SELECT * FROM jboard_spot " + sub_sql
					+ "ORDER BY spot_num DESC) a ) WHERE rnum>=? AND rnum <=? ";
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
				spot.setHit(rs.getInt("hit"));
				spot.setReg_date(rs.getDate("reg_date"));
				spot.setModify_date(rs.getDate("modify_date"));
				spot.setFilename(rs.getString("filename"));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return spot;
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
				spot.setContent(rs.getString("filename"));
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
			sql = "select * from (select a.*, rownum rnum from (select b.spot_num,b.title,b.content,g.good from jboard_spot b join jgood_spot g on b.spot_num = g.spot_num where g.user_num=? and good=1 order by b.spot_num)a) where rnum>=? and rnum<=?";

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