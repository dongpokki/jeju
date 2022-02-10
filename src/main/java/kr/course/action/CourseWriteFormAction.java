package kr.course.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class CourseWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		if(session_user_num == null) {//로그인 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		//로그인 된 경우
		return "/WEB-INF/views/course/courseWriteForm.jsp";
	}

}
