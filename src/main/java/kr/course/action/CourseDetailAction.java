package kr.course.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class CourseDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int board_co_num = Integer.parseInt(request.getParameter("board_Co_num"));

		CourseDAO dao = CourseDAO.getInstance();
		// dao.updateReadcount(board_num);
		CourseVO course = dao.getCourseBoard(board_co_num);

		course.setTitle(StringUtil.useBrNoHtml(course.getTitle()));
		course.setContent(StringUtil.useBrNoHtml(course.getContent()));
		request.setAttribute("course", course);

		// JSP 경로 반환
		return "/WEB-INF/views/course/courseDetail.jsp";
	}

}