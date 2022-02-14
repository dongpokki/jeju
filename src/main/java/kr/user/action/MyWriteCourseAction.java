package kr.user.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.spot.vo.SpotVO;
import kr.util.PagingUtil;

public class MyWriteCourseAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");

		//현재 접속한 회원의 회원번호 및 id 반환
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");

		//내가 좋아하는 장소 페이지 넘버 반환
		String mywritecourse_pageNum = request.getParameter("mywritecourse_pageNum");
		if(mywritecourse_pageNum == null) {mywritecourse_pageNum = "1";}

		CourseDAO mywritecourse = CourseDAO.getInstance();
		int mywritecourse_count = mywritecourse.getmyCourseCount(session_user_num);
		int mywritecourse_rowCount = 3;
		PagingUtil mywritecourse_page = new PagingUtil(Integer.parseInt(mywritecourse_pageNum),mywritecourse_count,mywritecourse_rowCount,1,null);

		List<CourseVO> mywritecourse_list = null;
		if(mywritecourse_count > 0) {
			mywritecourse_list = mywritecourse.getmyCourseList(session_user_num, mywritecourse_page.getStartCount(), mywritecourse_page.getEndCount());
		}else { // 조회 결과가 없는 경우
			// 리스트를 빈 배열로 만든다.
			mywritecourse_list = Collections.emptyList();
		}

		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("mywritecourse_count", mywritecourse_count);
		mapAjax.put("mywritecourse_rowCount", mywritecourse_rowCount);
		mapAjax.put("mywritecourse_list",mywritecourse_list);

		//JSON 데이터로 반환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";

	}
}