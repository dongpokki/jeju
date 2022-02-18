package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class BoardDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//글번호 반환
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		
		//조회수 증가
		dao.updateReadcount(board_num);
		
		BoardVO board = dao.getBoard(board_num);
		
		board.setTitle(StringUtil.useBrNoHtml(board.getTitle()));
		request.setAttribute("board", board);
		
		// 좋아요 기능을 위한 user_num 호출
				HttpSession session = request.getSession();
				Integer session_user_num = (Integer) session.getAttribute("session_user_num");
				if (session_user_num == null) {
					session_user_num = 0;
				}
				
				// 좋아요 상태 확인
				int checked = dao.checkGood(session_user_num, board_num);
				
				// 좋아요 갯수 확인
				int good = dao.getBoardGoodCount(board_num);
				
				request.setAttribute("good", good);
				request.setAttribute("checked", checked);
				request.setAttribute("user_num", session_user_num);
		
		return "/WEB-INF/views/board/boardDetail.jsp";
	}

}
