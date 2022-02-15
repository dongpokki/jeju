package kr.spot.action;

import java.util.Collection;
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
import kr.spot.vo.SpotCmtVO;
import kr.util.PagingUtil;

public class SpotListCmtAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1";

		int spot_num = Integer.parseInt(request.getParameter("spot_num"));
		// System.out.println(request.getParameter("spot_num"));

		SpotDAO dao = SpotDAO.getInstance();
		int count = dao.getCmtSpotCount(spot_num);

		int rowCount = 10;
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 10, null);

		List<SpotCmtVO> list = null;
		if (count > 0) {
			list = dao.getListCmtSpot(page.getStartCount(), page.getEndCount(), spot_num);
		} else {
			list = Collections.emptyList();
		}
		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("session_user_num");

		Map<String, Object> mapAjax = new HashMap<>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		mapAjax.put("user_num", user_num);

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
