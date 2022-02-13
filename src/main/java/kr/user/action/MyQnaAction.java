package kr.user.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.PagingUtil;

public class MyQnaAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");

		//현재 접속한 회원의 회원번호 및 id 반환
		HttpSession session = request.getSession();
		String session_user_id = (String) session.getAttribute("session_user_id");

		//내가 작성한 문의사항 페이지 넘버 반환
		String myqna_pageNum = request.getParameter("myqna_pageNum");
		if(myqna_pageNum == null) {myqna_pageNum = "1";}
		
		// qnadao 객체 생성 및 페이지 넘버링 작업
		QnaDAO myqna_dao = QnaDAO.getInstance();
		int myqna_count = myqna_dao.getmyListQnaCount(session_user_id); 
		int myqna_rowCount = 3;
		PagingUtil myqna_page = new PagingUtil(Integer.parseInt(myqna_pageNum),myqna_count,myqna_rowCount,1,null);

		List<QnaVO> myqna_list = null;
		if(myqna_count > 0) {
			myqna_list = myqna_dao.getmyListQna(session_user_id,myqna_page.getStartCount(), myqna_page.getEndCount());
		}else { // 조회 결과가 없는 경우
			// 리스트를 빈 배열로 만든다.
			myqna_list = Collections.emptyList();
		}
		

		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("myqna_count", myqna_count);
		mapAjax.put("myqna_rowCount", myqna_rowCount);
		mapAjax.put("myqna_list",myqna_list);
		
		//JSON 데이터로 반환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);
			
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}