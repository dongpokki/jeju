package kr.spot.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class SpotWriteFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer session_user_num = (Integer) session.getAttribute("session_user_num");
		if (session_user_num == null) {
			return "redirect:/user/loginForm.do";
		}

		return "/WEB-INF/views/spot/spotWriteForm.jsp";
	}

}
