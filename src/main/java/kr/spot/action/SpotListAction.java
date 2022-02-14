package kr.spot.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.PagingUtil;

public class SpotListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1";

		String keyword = request.getParameter("keyword");

		SpotDAO dao = SpotDAO.getInstance();
		String category = request.getParameter("category");
		if (category == null) {
			category = "0";
		}
		int count = dao.getSpotBoardCount(keyword, Integer.parseInt(category));
		String sort = request.getParameter("sort");
		String addKey = "&category=" + Integer.parseInt(category);
		if (sort != null) {
			addKey += "&sort=" + sort;
		}
		// 페이지 처리
		PagingUtil page = new PagingUtil("", keyword, Integer.parseInt(pageNum), count, 9, 9, "spotList.do", addKey);

		List<SpotVO> list = null;
		if (count > 0) {
			list = dao.getList(page.getStartCount(), page.getEndCount(), keyword, Integer.parseInt(category), sort);
		}

		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		request.setAttribute("category", category);
		request.setAttribute("sort", sort);
		// JSP 경로 반환
		return "/WEB-INF/views/spot/spotList.jsp";
	}

}
