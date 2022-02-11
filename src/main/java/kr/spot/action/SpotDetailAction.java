package kr.spot.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.StringUtil;

public class SpotDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 글번호 반환
		int spot_num = Integer.parseInt(request.getParameter("spot_num"));

		SpotDAO dao = SpotDAO.getInstance();

		// 조회수 증가
		dao.updateReadcount(spot_num);

		SpotVO spot = dao.getSpotBoard(spot_num);

		spot.setTitle(StringUtil.useBrNoHtml(spot.getTitle()));
		request.setAttribute("spot", spot);
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer) session.getAttribute("session_user_num");
		if (session_user_num == null) {// 로그인이 되지 않은 경우
			session_user_num = 0;
		}

		int good = dao.getSpotGoodCount(spot_num);
		request.setAttribute("good", good);
		request.setAttribute("user_num", session_user_num);

		// JSP 경로 반환
		return "/WEB-INF/views/spot/spotDetail.jsp";
	}

}
