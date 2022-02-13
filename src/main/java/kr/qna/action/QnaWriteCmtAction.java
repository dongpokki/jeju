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

public class QnaWriteCmtAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> mapAjax = new HashMap<>();
		
		HttpSession session = request.getSession();
		Integer user_num =(Integer)session.getAttribute("session_user_num");

		if(user_num==null) {//로그인 된 경우
			mapAjax.put("result", "logout");
		}
		Integer user_auth =(Integer)session.getAttribute("session_user_auth");

		if(user_auth==3) {
			//관리자로 로그인 한 경우 
			request.setCharacterEncoding("utf-8");
			QnaCmtVO cmt = new QnaCmtVO();
			cmt.setCmt_content(request.getParameter("cmt_content"));
			cmt.setUser_num(user_num);
			cmt.setQna_num(Integer.parseInt(request.getParameter("qna_num")));
			System.out.println(request.getParameter("qna_num"));
			QnaDAO dao = QnaDAO.getInstance();
			dao.insertCmtQna(cmt);
			mapAjax.put("result", "success");
			
		}else {
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터 설정
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
