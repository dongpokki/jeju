package kr.area.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.area.vo.AreaVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class AreaDAO {
	// 싱글턴 패턴
	private static AreaDAO instance = new AreaDAO();

	public static AreaDAO getInstance() {
		return instance;
	}

	private AreaDAO() {
	}

	// 추천 장소 등록
	public void insertSpot(AreaVO area) throws Exception {
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

			pstmt.setString(1, area.getTitle());
			pstmt.setString(2, area.getContent());
			pstmt.setString(3, area.getFilename());
			pstmt.setString(4, area.getIp());
			pstmt.setInt(5, area.getUser_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 추천 장소 수정
	public void updateSpotBoard(AreaVO area) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if (area.getFilename() != null) {
				sub_sql = ",filename=?";
			}

			sql = "UPDATE jboard_spot SET title=?, content=?, modify_date=SYSDATE" + sub_sql
					+ ", ip=? WHERE spot_num=?";

			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(++cnt, area.getTitle());
			pstmt.setString(++cnt, area.getContent());
			if (area.getFilename() != null) {
				pstmt.setString(++cnt, area.getFilename());
			}
			pstmt.setString(++cnt, area.getIp());
			pstmt.setInt(++cnt, area.getUser_num());
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
	public int getSpotBoardCount(String keyfield, String keyword) throws Exception {
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
				// 검색글 처리
				if (keyfield.equals("1"))
					sub_sql = "WHERE b.title LIKE ?";
				else if (keyfield.equals("3"))
					sub_sql = "WHERE b.content LIKE ?";
			}

			// 전체 또는 검색 레코드 수
			sql = "SELECT COUNT(*) FROM jboard_spot " + sub_sql;
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%");
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
	public List<AreaVO> getList(int startRow, int endRow, String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AreaVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if (keyword != null && !"".equals(keyword)) {
				if (keyfield.equals("1"))
					sub_sql = "WHERE b.title LIKE ?";
				else if (keyfield.equals("2"))
					sub_sql = "WHERE b.content LIKE ?";
			}

			// SQL문 작성
			sql = "SELECT * FROM ( SELECT a.*, rownum rnum FROM ( SELECT * FROM jboard_spot " + sub_sql
					+ "ORDER BY spot_num DESC) a ) WHERE rnum>=? AND rnum <=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, startRow);
			pstmt.setInt(++cnt, endRow);

			// SQL문을 테이블에 반영하고 결과행들을 ResultSet 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<AreaVO>();
			while (rs.next()) {
				AreaVO area = new AreaVO();

				area.setSpot_num(rs.getInt("spot_num"));
				area.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				area.setFilename(rs.getString("filename"));
				area.setContent(rs.getString("content"));

				// 자바빈(VO)을 ArrayList에 저장
				list.add(area);
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
	public AreaVO getSpotBoard(int spot_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AreaVO area = null;
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
				area = new AreaVO();
				area.setSpot_num(rs.getInt("spot_num"));
				area.setTitle(rs.getString("title"));
				area.setContent(rs.getString("content"));
				area.setHit(rs.getInt("hit"));
				area.setReg_date(rs.getDate("reg_date"));
				area.setModify_date(rs.getDate("modify_date"));
				area.setFilename(rs.getString("filename"));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return area;
	}

}
