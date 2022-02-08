package kr.spot.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.FileUtil;

public class SpotWriteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer) session.getAttribute("session_user_num");

		if (session_user_num == null) {// 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		// 로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		SpotVO spot = new SpotVO();
		spot.setTitle(multi.getParameter("title"));
		spot.setContent(multi.getParameter("content"));
		spot.setIp(request.getRemoteAddr());
		spot.setFilename(multi.getFilesystemName("filename"));
		spot.setUser_num(session_user_num);

		SpotDAO dao = SpotDAO.getInstance();
		dao.insertSpot(spot);

		return "redirect:/spot/spotList.do";
	}

}
