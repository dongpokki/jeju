package kr.area.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.area.dao.AreaDAO;
import kr.area.vo.AreaVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class AreaDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int spot_num = Integer.parseInt(request.getParameter("spot_num"));

		AreaDAO dao = AreaDAO.getInstance();
		// dao.updateReadcount(board_num);
		AreaVO area = dao.getSpotBoard(spot_num);

		area.setTitle(StringUtil.useBrNoHtml(area.getTitle()));
		area.setContent(StringUtil.useBrNoHtml(area.getContent()));
		request.setAttribute("area", area);

		// JSP 경로 반환
		return "/WEB-INF/views/area/areaDetail.jsp";
	}

}
