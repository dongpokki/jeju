package kr.spot.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotCmtVO;

public class SpotUpdateCmtAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		int spotcmt_num = Integer.parseInt(request.getParameter("spotcmt_num"));

		SpotDAO dao = SpotDAO.getInstance();
		SpotCmtVO db_cmt = dao.getCmtspot(spotcmt_num);

		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("session_user_num");
		Map<String, String> mapAjax = new HashMap<>();
		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else if (user_num != null && user_num == db_cmt.getUser_num()) {
			SpotCmtVO cmt = new SpotCmtVO();
			cmt.setSpotcmt_num(spotcmt_num);
			cmt.setCmt_content(request.getParameter("cmt_content"));
			dao.updateCmtSpot(cmt);
			mapAjax.put("result", "success");
		} else {
			mapAjax.put("result", "wrongAccess");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
