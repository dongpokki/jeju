package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.PagingUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// SpotDAO 객체 생성
		SpotDAO dao = SpotDAO.getInstance();
		
		// 랭킹 1~3위 추천 장소 데이터 받아오기
		List<SpotVO> list = dao.getRankingSpot();
		
		// request에 list 저장
		request.setAttribute("list", list);

		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}

}
