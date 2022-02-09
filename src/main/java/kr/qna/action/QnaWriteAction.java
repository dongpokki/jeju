package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class QnaWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		QnaVO qna = new QnaVO();
		qna.setTitle(multi.getParameter("title"));
		qna.setContent(multi.getParameter("content"));
		qna.setIp(request.getRemoteAddr());
		qna.setFilename(multi.getParameter("filename"));
		qna.setUser_num(user_num);
		qna.setViewable_check(Integer.parseInt(multi.getParameter("viewable_check")));
		qna.setName(multi.getParameter("name"));
		
		QnaDAO dao = QnaDAO.getInstance();
		dao.insertBoard(qna);
		
		return "/WEB-INF/views/qna/qnaWrite.jsp";
	}

}
