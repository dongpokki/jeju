package kr.course.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;


import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.util.FileUtil;

public class CourseUpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int course_num = Integer.parseInt(multi.getParameter("course_num"));
		String filename = multi.getFilesystemName("filename");
		
		CourseDAO dao = CourseDAO.getInstance();
		//수정전 데이터
		CourseVO db_course = dao.getCourse(course_num);
		if(user_num!=db_course.getUser_num()) {//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, filename);//업로드된 파일이 있으면 파일 삭제
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		CourseVO course = new CourseVO();
		course.setCourse_num(course_num);
		course.setTitle(multi.getParameter("title"));
		course.setContent(multi.getParameter("content"));
		course.setIp(request.getRemoteAddr());
		course.setFilename(filename);
		
		//글수정
		dao.updateCourse(course);
		
		//전송된 파일이 있을 경우 이전 파일 삭제
		if(filename!=null) {
			FileUtil.removeFile(request, db_course.getFilename());
		}
		
		return "redirect:/course/courseDetail.do?course_num="+course_num;
	}

}
