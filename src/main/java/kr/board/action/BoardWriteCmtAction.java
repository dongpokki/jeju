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

public class BoardWriteCmtAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> mapAjax = new HashMap<>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("session_user_num");

		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else {
			request.setCharacterEncoding("utf-8");

			BoardCmtVO cmt = new BoardCmtVO();
			cmt.setCmt_content(request.getParameter("cmt_content"));
			cmt.setUser_num(user_num);
			cmt.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
			BoardDAO dao = BoardDAO.getInstance();
			dao.insertCmtBoard(cmt);

			mapAjax.put("result", "success");
		}

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
