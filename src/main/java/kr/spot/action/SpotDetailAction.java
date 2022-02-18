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
		int spot_num = Integer.parseInt(request.getParameter("spot_num"));

		SpotDAO dao = SpotDAO.getInstance();

		// 조회수 증가
		dao.updateReadcount(spot_num);
		
		SpotVO spot = dao.getSpotBoard(spot_num);

		spot.setTitle(StringUtil.useBrNoHtml(spot.getTitle()));
		request.setAttribute("spot", spot);
		
		// 좋아요 기능을 위한 user_num 호출
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer) session.getAttribute("session_user_num");
		if (session_user_num == null) {
			session_user_num = 0;
		}
		
		// 좋아요 상태 확인
		int checked = dao.checkGood(session_user_num, spot_num);
		
		// 좋아요 갯수 확인
		int good = dao.getSpotGoodCount(spot_num);
		
		request.setAttribute("good", good);
		request.setAttribute("checked", checked);

		return "/WEB-INF/views/spot/spotDetail.jsp";
	}

}
