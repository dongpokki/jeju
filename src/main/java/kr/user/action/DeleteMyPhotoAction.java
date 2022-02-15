package kr.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;
import kr.util.FileUtil;

public class DeleteMyPhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		Map<String,String> mapAjax = new HashMap<String, String>();
		
		//회원 전용 서비스 이므로 세션 정보 조회
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		String session_user_photo = (String)session.getAttribute("session_user_photo");
		
		if(session_user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { // 로그인 된 경우
			UserDAO dao = UserDAO.getInstance();
			UserVO db_user = dao.getUser(session_user_num); // 현재 등록되어 있는 회원 레코드 내 photo 정보 읽기
			
			//프로필 수정        파일명  회원번호(user_num)
			dao.updateMyPhoto(null,session_user_num);
			
			//세션에 저장된 프로필 사진 정보 제거
			session.removeAttribute("session_user_photo");
			
			//이전 프로필 이미지 삭제
			FileUtil.removeFile(request, session_user_photo);
			
			mapAjax.put("result", "success");
		}
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData= mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// jsp 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}