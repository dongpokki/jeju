package kr.user.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.util.PagingUtil;

public class MyGoodBoardAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");

		//현재 접속한 회원의 회원번호 및 id 반환
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");

		//내가 좋아하는 게시글 페이지 넘버 반환
		String mygoodboard_pageNum = request.getParameter("mygoodboard_pageNum");
		if(mygoodboard_pageNum == null) {mygoodboard_pageNum = "1";}

		BoardDAO mygoodboard = BoardDAO.getInstance();
		int mygoodboard_count = mygoodboard.MyGoodBoardCount(session_user_num);
		int mygoodboard_rowCount = 3;
		PagingUtil mygoodboard_page = new PagingUtil(Integer.parseInt(mygoodboard_pageNum),mygoodboard_count,mygoodboard_rowCount,1,null);

		List<BoardVO> mygoodboard_list = null;
		if(mygoodboard_count > 0) {
			mygoodboard_list = mygoodboard.MyGoodBoard(session_user_num, mygoodboard_page.getStartCount(), mygoodboard_page.getEndCount());
			
		}else { // 조회 결과가 없는 경우
			// 리스트를 빈 배열로 만든다.
			mygoodboard_list = Collections.emptyList();
		}

		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("mygoodboard_count", mygoodboard_count);
		mapAjax.put("mygoodboard_rowCount", mygoodboard_rowCount);
		mapAjax.put("mygoodboard_list",mygoodboard_list);

		//JSON 데이터로 반환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		System.out.println(ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";

	}
}