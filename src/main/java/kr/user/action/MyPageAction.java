package kr.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class MyPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//회원 전용 페이지니까 세션 조회 필수
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		if(session_user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		
		// 로그인이 된 경우
		UserDAO dao = UserDAO.getInstance();
		UserVO user = dao.getUser(session_user_num);
		
		// 내가 추천한 장소 데이터를 가져오기 위한 작업
		// SpotDAO 객체 생성
		SpotDAO spot_dao = SpotDAO.getInstance();
		// 로그인한 회원이 추천한 게시글 전부 가져와서 리스트에 담기
		List<SpotVO> spot_list = spot_dao.MyGoodSpot(0,0,session_user_num); 
		
		request.setAttribute("user", user);
		request.setAttribute("spot_list", spot_list);
		
		// jsp 경로 반환
		return "/WEB-INF/views/user/myPage.jsp";
	}

}
