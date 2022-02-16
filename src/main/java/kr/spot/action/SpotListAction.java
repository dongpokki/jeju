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
		
		// 검색어가 있는 경우 검색어 호출
		String keyword = request.getParameter("keyword");

		SpotDAO dao = SpotDAO.getInstance();
		
		// 카테고리 값 호출
		String category = request.getParameter("category");
		
		if (category == null) { // 카테고리를 따로 설정하지 않은 경우
			category = "0";
		}
		
		int count = dao.getSpotBoardCount(keyword, Integer.parseInt(category));
		
		// 정렬 필터 값 호출
		String sort = request.getParameter("sort");
		
		// 카테고리 설정한 경우 url 주소 값 쿼리스트링으로 변경
		String addKey = "&category=" + Integer.parseInt(category);
		
		if (sort != null) { // 정렬 필터 값이 있을 경우 쿼리스트링 추가
			addKey += "&sort=" + sort;
		}
																		// 한 페이지에 9개의 게시글만 보여지게끔 설정, 쿼리스트링으로 들어갈 값
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

		return "/WEB-INF/views/spot/spotList.jsp";
	}

}
