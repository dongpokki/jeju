package kr.course.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class CourseDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//글번호 반환
		int course_num = Integer.parseInt(request.getParameter("course_num"));
		
		CourseDAO dao = CourseDAO.getInstance();
		
		//조회수 증가
		dao.updateReadcount(course_num);
		
		CourseVO course = dao.getCoursecourse(course_num);
		
		//HTML태그를 허용하지 않음
		course.setTitle(StringUtil.useNoHtml(course.getTitle()));
		//HTML태그를 허용하지 않으면서 줄바꿈 처리
		course.setContent(StringUtil.useBrNoHtml(course.getContent()));
		
		request.setAttribute("course", course);
		
		return "/WEB-INF/views/course/courseDetail.jsp";
	}

}
