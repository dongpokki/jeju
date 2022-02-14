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

public class MyGoodCourseAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");

		//현재 접속한 회원의 회원번호 및 id 반환
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");

		//내가 좋아하는 장소 페이지 넘버 반환
		String mygoodcourse_pageNum = request.getParameter("mygoodcourse_pageNum");
		if(mygoodcourse_pageNum == null) {mygoodcourse_pageNum = "1";}

		CourseDAO mygoodcourse = CourseDAO.getInstance();
		int mygoodcourse_count = mygoodcourse.MyGoodCourseCount(session_user_num);
		int mygoodcourse_rowCount = 3;
		PagingUtil mygoodcourse_page = new PagingUtil(Integer.parseInt(mygoodcourse_pageNum),mygoodcourse_count,mygoodcourse_rowCount,1,null);

		List<CourseVO> mygoodcourse_list = null;
		if(mygoodcourse_count > 0) {
			mygoodcourse_list = mygoodcourse.MyGoodCourse(session_user_num, mygoodcourse_page.getStartCount(), mygoodcourse_page.getEndCount());
		}else { // 조회 결과가 없는 경우
			// 리스트를 빈 배열로 만든다.
			mygoodcourse_list = Collections.emptyList();
		}

		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("mygoodcourse_count", mygoodcourse_count);
		mapAjax.put("mygoodcourse_rowCount", mygoodcourse_rowCount);
		mapAjax.put("mygoodcourse_list",mygoodcourse_list);

		//JSON 데이터로 반환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		
		System.out.println(ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";

	}
}