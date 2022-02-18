package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardCmtVO;


public class BoardUpdateCmtAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		int boardcmt_num = Integer.parseInt(request.getParameter("boardcmt_num"));

		BoardDAO dao = BoardDAO.getInstance();
		BoardCmtVO db_cmt = dao.getCmtBoard(boardcmt_num);

		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("session_user_num");
		Map<String, String> mapAjax = new HashMap<>();
		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else if (user_num != null && user_num == db_cmt.getUser_num()) {
			BoardCmtVO cmt = new BoardCmtVO();
			cmt.setBoardcmt_num(boardcmt_num);
			cmt.setCmt_content(request.getParameter("cmt_content"));
			dao.updateCmtBoard(cmt);
			mapAjax.put("result", "success");
		} else {
			mapAjax.put("result", "wrongAccess");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
