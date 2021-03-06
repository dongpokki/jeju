package kr.qna.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaCmtVO;

public class QnaUpdateCmtAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");
		
		int qnacmt_num = Integer.parseInt(request.getParameter("qnacmt_num"));
		
		QnaDAO dao = QnaDAO.getInstance();
		QnaCmtVO db_cmt = dao.getCmtQna(qnacmt_num);
		
		HttpSession session = request.getSession();
		Integer user_num=(Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		Map<String, String> mapAjax = new HashMap<>();
		if(user_num==null) {
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_auth==3) {
			QnaCmtVO cmt = new QnaCmtVO();
			cmt.setQnacmt_num(qnacmt_num);
			cmt.setCmt_content(request.getParameter("cmt_content"));
			
			dao.updateCmtQna(cmt);
			mapAjax.put("result", "success");
		}else {
			mapAjax.put("result", "wrongAccess");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData =mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
