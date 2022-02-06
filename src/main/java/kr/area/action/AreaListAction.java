package kr.area.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.area.dao.AreaDAO;
import kr.area.vo.AreaVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class AreaListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1";

		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		if (keyfield == null)
			keyfield = "";
		if (keyword == null)
			keyword = "";

		AreaDAO dao = AreaDAO.getInstance();
		int count = dao.getSpotBoardCount(keyfield, keyword);
		// 페이지 처리
		// keyfield,keyword,currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "list.do");

		List<AreaVO> list = null;
		if (count > 0) {
			list = dao.getList(page.getStartCount(), page.getEndCount(), keyfield, keyword);
		}

		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());

		// JSP 경로 반환
		return "/WEB-INF/views/area/areaList.jsp";
	}

}
