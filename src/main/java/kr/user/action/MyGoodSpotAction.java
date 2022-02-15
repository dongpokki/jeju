package kr.user.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.PagingUtil;

public class MyGoodSpotAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");

		//현재 접속한 회원의 회원번호 및 id 반환
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");

		//내가 좋아하는 장소 페이지 넘버 반환
		String spot_pageNum = request.getParameter("spot_pageNum");
		if(spot_pageNum == null) {spot_pageNum = "1";}

		// spotdao 객체 생성 및 페이지 넘버링 작업
		SpotDAO spot_dao = SpotDAO.getInstance();
		int spot_count = spot_dao.MyGoodSpotCount(session_user_num);
		int spot_rowCount = 3;
		PagingUtil spot_page = new PagingUtil(Integer.parseInt(spot_pageNum),spot_count,spot_rowCount,1,null);

		List<SpotVO> spot_list = null;
		if(spot_count > 0) {
			spot_list = spot_dao.MyGoodSpot(spot_page.getStartCount(), spot_page.getEndCount(), session_user_num);
		}else { // 조회 결과가 없는 경우
			// 리스트를 빈 배열로 만든다.
			spot_list = Collections.emptyList();
		}

		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("spot_count", spot_count);
		mapAjax.put("spot_rowCount", spot_rowCount);
		mapAjax.put("spot_list",spot_list);

		//JSON 데이터로 반환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}