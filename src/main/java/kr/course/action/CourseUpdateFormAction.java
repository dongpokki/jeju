package kr.course.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;

public class CourseUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		
		int course_num = Integer.parseInt(request.getParameter("course_num"));
		CourseDAO dao = CourseDAO.getInstance();
		CourseVO db_course = dao.getCourse(course_num);
		if(user_num != db_course.getUser_num()) {//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인이 되어있고 로그인한 회원번호와 작성자 회원번호 일치
		request.setAttribute("board", db_course);
		
		return "/WEB-INF/views/course/courseUpdateForm.jsp";
	}

}




