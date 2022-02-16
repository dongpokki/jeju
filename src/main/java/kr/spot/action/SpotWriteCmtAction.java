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

public class SpotWriteCmtAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> mapAjax = new HashMap<>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("session_user_num");

		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else {
			request.setCharacterEncoding("utf-8");

			SpotCmtVO cmt = new SpotCmtVO();
			cmt.setCmt_content(request.getParameter("cmt_content"));
			cmt.setUser_num(user_num);
			cmt.setSpot_num(Integer.parseInt(request.getParameter("spot_num")));
			SpotDAO dao = SpotDAO.getInstance();
			dao.insertCmtSpot(cmt);

			mapAjax.put("result", "success");
		}

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
