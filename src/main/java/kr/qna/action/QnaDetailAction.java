package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.StringUtil;

public class QnaDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		QnaDAO dao = QnaDAO.getInstance();
		
		QnaVO qna = dao.getQna(qna_num);
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		if(user_num==null) {
			return "redirect:/user/loginForm.do";
		}
		if(qna.getViewable_check()==1) {
			if(user_num==qna.getUser_num() || user_auth==3) {
				//조회수증가
				dao.updateReadCount(qna_num);

			}
		}else {
			dao.updateReadCount(qna_num);
		}
		
		//HTML 태그르 허용하지 않음, 줄바꿈 허용하지 않음
		qna.setTitle(StringUtil.useNoHtml(qna.getTitle()));
		//HTML 태그 허용하지 않으면서 줄바꿈허용 
		//qna.setContent(StringUtil.useBrNoHtml(qna.getContent()));
		
		request.setAttribute("qna", qna);
		return "/WEB-INF/views/qna/qnaDetail.jsp";
	}

}
