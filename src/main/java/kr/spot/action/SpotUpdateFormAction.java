package kr.spot.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.StringUtil;

public class SpotUpdateFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int spot_num = Integer.parseInt(request.getParameter("spot_num"));

		SpotDAO dao = SpotDAO.getInstance();

		SpotVO spot = dao.getSpotBoard(spot_num);

		spot.setTitle(StringUtil.useBrNoHtml(spot.getTitle()));
		request.setAttribute("spot", spot);
		
		return "/WEB-INF/views/spot/spotUpdateForm.jsp";
	}

}
