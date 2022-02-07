package kr.course.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.util.PagingUtil;

public class CourseListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		if(keyfield == null) keyfield = "";
		if(keyword == null) keyword = "";
		
		CourseDAO dao = CourseDAO.getInstance();
		int count = dao.getCourseBoardCount(keyfield, keyword);
		
		//페이지 처리
		//keyfield,keyword,currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(keyfield,keyword,
				          Integer.parseInt(pageNum),count,20,10,"list.do");
		
		List<CourseVO> list = null;
		if(count > 0) {
			list = dao.getCourseListBoard(page.getStartCount(), page.getEndCount(), 
					                                     keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		//JSP 경로 반환
		return "/WEB-INF/views/course/courseList.jsp";
	}

}
