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

			sql = "INSERT INTO jboard_spot (spot_num, title, content, filename, ip, user_num)"
					+ "VALUES (jboard_spot_seq.nextval, ?,?,?,?,?)";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Spot.getTitle());
			pstmt.setString(2, Spot.getContent());
			pstmt.setString(3, Spot.getFilename());
			pstmt.setString(4, Spot.getIp());
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
					+ ", ip=? WHERE spot_num=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(++cnt, Spot.getTitle());
			pstmt.setString(++cnt, Spot.getContent());
			if (Spot.getFilename() != null) {
				pstmt.setString(++cnt, Spot.getFilename());
			}
			pstmt.setString(++cnt, Spot.getIp());
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
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			if (keyword != null && !"".equals(keyword)) {
				sub_sql = "AND content LIKE ? ";
			}
			// 전체 또는 검색 레코드 수
			sql = "SELECT COUNT(*) FROM jboard_spot WHERE category = ? " + sub_sql;
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, "%" + keyword + "%");
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

			if (category == 0) {
				if (keyword != null && !"".equals(keyword)) {
					sub_sql = "WHERE content LIKE ?";
				}

				sql = "SELECT * FROM ( SELECT a.*, rownum rnum FROM ( SELECT * FROM jboard_spot " + sub_sql
						+ "ORDER BY spot_num DESC) a ) WHERE rnum>=? AND rnum <=? ";
				pstmt = conn.prepareStatement(sql);
				if (keyword != null && !"".equals(keyword)) {

					pstmt.setString(++cnt, "%" + keyword + "%");
				}
				pstmt.setInt(++cnt, startRow);
				pstmt.setInt(++cnt, endRow);
			} else if (category != 0) {
				if (keyword != null && !"".equals(keyword)) {
					sub_sql = "AND content LIKE ?";
				}
				sql = "SELECT * FROM ( SELECT a.*, rownum rnum FROM ( SELECT * FROM jboard_spot WHERE category=? "
						+ sub_sql + "ORDER BY spot_num DESC) a ) WHERE rnum>=? AND rnum <=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++cnt, category);
				if (keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%" + keyword + "%");
				}
				pstmt.setInt(++cnt, startRow);
				pstmt.setInt(++cnt, endRow);
			}

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
	// 목록
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
			// 추천수가 높은 순으로 랭킹컬럼 1부터 3까지 조회 [추천수(good)이 같은 경우에는, 조회수(hit) 높은순으로 추가 비교]
						sql = "select * from (select spot_num,title,content,hit,good,row_number() over (order by good desc,hit desc) rank from jboard_spot join jgood_spot using(spot_num)) where rank < 4";

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

}
