package kr.spot.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.util.FileUtil;
import kr.controller.Action;

public class SpotDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer session_user_num = (Integer) session.getAttribute("session_user_num");

		if (session_user_num == null) {
			return "redirect:/user/loginForm.do";
		}

		SpotDAO dao = SpotDAO.getInstance();

		String[] ajaxMsg = request.getParameterValues("valueArr");
		if (ajaxMsg != null) { // 여러개 선택 삭제할 경우
			int size = ajaxMsg.length;
			for (int i = 0; i < size; i++) {
				SpotVO db_spot = dao.getSpotBoard(Integer.parseInt(ajaxMsg[i]));
				dao.deleteSpotBoard(Integer.parseInt(ajaxMsg[i]));
				FileUtil.removeFile(request, db_spot.getFilename());
			}
		} else { // 상세 페이지에서 삭제할 경우
			int spot_num = Integer.parseInt(request.getParameter("spot_num"));
			SpotVO db_spot = dao.getSpotBoard(spot_num);
			dao.deleteSpotBoard(spot_num);
			db_spot.getFilename();
		}

		// alert 창으로 안내
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("<script>alert('게시글을 성공적으로 삭제했습니다.'); location.href='spotList.do';</script>");
		writer.close();

		// 목록 페이지로 리다이렉트
		return "redirect:/spot/spotList.do";
	}

}
