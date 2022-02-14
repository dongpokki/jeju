package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.course.dao.CourseDAO;
import kr.course.vo.CourseVO;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.PagingUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// SpotDAO 객체 생성
		SpotDAO spot_dao = SpotDAO.getInstance();
		
		// CoureseDAO 객체 생성
		CourseDAO course_dao = CourseDAO.getInstance();
		
		// 랭킹 1~3위 추천 장소(spot 데이터 받아오기)
		List<SpotVO> spot_list = spot_dao.getRankingSpot();
		
		// 랭킹 1~3위 추천 코스(course 데이터 받아오기)
		List<CourseVO> course_list = course_dao.getRankingCourse();
		
		// request에 list 저장
		request.setAttribute("spot_list", spot_list);
		request.setAttribute("course_list", course_list);
		
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}

}
