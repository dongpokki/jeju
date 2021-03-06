package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class BoardWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		BoardVO board = new BoardVO();
		board.setTitle(multi.getParameter("title"));
		board.setContent(multi.getParameter("content"));
		board.setIp(request.getRemoteAddr());
		board.setFilename(multi.getFilesystemName("filename"));
		board.setCourse(multi.getParameter("course"));//소진님
		board.setUser_num(user_num);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.insertBoard(board);
		dao.updateNoticeBoard(board);
		
		return "/WEB-INF/views/board/boardWrite.jsp";
	}

}




