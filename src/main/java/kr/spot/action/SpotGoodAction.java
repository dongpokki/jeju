package kr.spot.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotGoodVO;

public class SpotGoodAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer) session.getAttribute("session_user_num");

		if (session_user_num == null) {// 로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		SpotDAO dao = SpotDAO.getInstance();
		int spot_num = Integer.parseInt(request.getParameter("spot_num"));
		int checked = dao.checkGood(session_user_num, spot_num);

		Map<String, String> mapAjax = new HashMap<String, String>();
		if (checked == 1) {
			mapAjax.put("result", "checked");
		} else {
			mapAjax.put("result", "success");
			SpotGoodVO good = new SpotGoodVO();

			good.setSpot_num(spot_num);
			good.setUser_num(session_user_num);
			dao.jGood(good);
		}

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
