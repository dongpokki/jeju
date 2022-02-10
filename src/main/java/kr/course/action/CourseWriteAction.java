package kr.course.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.util.FileUtil;

public class CourseWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}*/
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		CourseVO course = new CourseVO();
		course.setTitle(multi.getParameter("title"));
		course.setContent(multi.getParameter("content"));
		course.setIp(request.getRemoteAddr());
		course.setFilename(multi.getFilesystemName("filename"));
		/*course.setUser_num(user_num);*/
		
		CourseDAO dao = CourseDAO.getInstance();
		dao.insertCourseBoard(course);
		
		return "/WEB-INF/views/course/courseWrite.jsp";
	}

}




