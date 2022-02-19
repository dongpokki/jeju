package kr.user.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.spot.vo.SpotVO;
import kr.util.PagingUtil;

public class MyWriteBoardAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");

		//현재 접속한 회원의 회원번호 및 id 반환
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");

		//내가 작성한 게시글 페이지 넘버 반환
		String mywriteboard_pageNum = request.getParameter("mywriteboard_pageNum");
		if(mywriteboard_pageNum == null) {mywriteboard_pageNum = "1";}

		BoardDAO mywriteboard = BoardDAO.getInstance();
		int mywriteboard_count = mywriteboard.getmyBoardCount(session_user_num);
		int mywriteboard_rowCount = 3;
		PagingUtil mywriteboard_page = new PagingUtil(Integer.parseInt(mywriteboard_pageNum),mywriteboard_count,mywriteboard_rowCount,1,null);

		List<BoardVO> mywriteboard_list = null;
		if(mywriteboard_count > 0) {
			mywriteboard_list = mywriteboard.getmyBoardList(session_user_num, mywriteboard_page.getStartCount(), mywriteboard_page.getEndCount());
		}else { // 조회 결과가 없는 경우
			// 리스트를 빈 배열로 만든다.
			mywriteboard_list = Collections.emptyList();
		}

		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("mywriteboard_count", mywriteboard_count);
		mapAjax.put("mywriteboard_rowCount", mywriteboard_rowCount);
		mapAjax.put("mywriteboard_list",mywriteboard_list);

		//JSON 데이터로 반환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";

	}
}