package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardGoodVO;
import kr.controller.Action;
public class BoardGoodAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer) session.getAttribute("session_user_num");

		if (session_user_num == null) {
			return "redirect:/user/loginForm.do";
		}

		BoardDAO dao = BoardDAO.getInstance();

		int board_num = Integer.parseInt(request.getParameter("board_num"));

		// 좋아요 여부 확인
		int checked = dao.checkGood(session_user_num, board_num);

		Map<String, String> mapAjax = new HashMap<String, String>();
		if (checked == 1) { // 이미 좋아요를 한 상태에서 한번 더 클릭한 경우
			dao.cancelGood(board_num, session_user_num); // 좋아요 취소
			mapAjax.put("result", "cancel");
			
			// 새로운 좋아요 갯수 반환
			int good_result = dao.getBoardGoodCount(board_num); 
			mapAjax.put("good_result", String.valueOf(good_result));
			
		} else if (checked == 0) { // 처음 좋아요를 누른 경우
			mapAjax.put("result", "success");
			BoardGoodVO good = new BoardGoodVO();

			good.setBoard_num(board_num);
			good.setUser_num(session_user_num);
			dao.jGood(good); // 좋아요
			
			// 새로운 좋아요 갯수 반환
			int good_result = dao.getBoardGoodCount(board_num);
			mapAjax.put("good_result", String.valueOf(good_result));
		}

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
