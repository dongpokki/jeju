package kr.qna.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class QnaDeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num==null) {
			mapAjax.put("result", "logout");
		}else {
			int qna_num = Integer.parseInt(request.getParameter("qna_num"));
			
			QnaDAO dao = QnaDAO.getInstance();
			QnaVO db_qna = dao.getQna(qna_num);
			if(user_num!=db_qna.getUser_num()) {
				mapAjax.put("result", "wrongAccess");
			}else {
				dao.deleteFile(qna_num);
				
				FileUtil.removeFile(request, db_qna.getFilename());
				
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
