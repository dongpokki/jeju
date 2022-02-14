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

public class QnaDeleteCmtAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int qnacmt_num = Integer.parseInt(request.getParameter("qnacmt_num"));
		
		Map<String,String> mapAjax = new HashMap<>();
		
		QnaDAO dao = QnaDAO.getInstance();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		if(user_num==null) {
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_auth==3) {
			dao.deleteCmtQna(qnacmt_num);
			mapAjax.put("result", "success");
		}else {
			mapAjax.put("result", "wrongAccess");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
