package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class QnaDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		if(user_num==null) {
			return "redirect:/user/loginForm.do";
		}
		int qna_num=Integer.parseInt(request.getParameter("qna_num"));
		QnaDAO dao=QnaDAO.getInstance();
		QnaVO db_qna = dao.getQna(qna_num);
		if(qna_num!= db_qna.getQna_num() && user_auth!=3) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		dao.deleteQna(qna_num);
		
		FileUtil.removeFile(request, db_qna.getFilename());
		
		return "redirect:/qna/qnaList.do";
	}

}
