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
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.PagingUtil;

public class MyGoodSpotAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");

		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {pageNum = "1";}

		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		
		SpotDAO spot_dao = SpotDAO.getInstance();
		int count = spot_dao.MyGoodSpotCount(session_user_num);

		int rowCount = 3;

		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,rowCount,1,null);

		List<SpotVO> list = null;
		if(count > 0) {
			list = spot_dao.MyGoodSpot(page.getStartCount(), page.getEndCount(), session_user_num);
		}else { // 조회 결과가 없는 경우
			// 리스트를 빈 배열로 만든다.
			list = Collections.emptyList();
		}


		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list",list);

		//JSON 데이터로 반환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}