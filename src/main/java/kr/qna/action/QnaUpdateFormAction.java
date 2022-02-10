package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;

public class QnaUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num==null) {//로그인 되지 않는 경우
			return "redirect:/user/loginForm.do";
		}
		//로그인 된 경우
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		QnaDAO dao = QnaDAO.getInstance();
		QnaVO db_qna=dao.getQna(qna_num);
		if(qna_num!=db_qna.getQna_num()) {//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		request.setAttribute("qna", db_qna);
		
		return "/WEB-INF/views/qna/qnaUpdateForm.jsp";
	}

}
