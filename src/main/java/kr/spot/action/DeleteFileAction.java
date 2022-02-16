package kr.spot.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.spot.dao.SpotDAO;
import kr.spot.vo.SpotVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class DeleteFileAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();

		int spot_num = Integer.parseInt(request.getParameter("spot_num"));

		SpotDAO dao = SpotDAO.getInstance();
		SpotVO db_spot = dao.getSpotBoard(spot_num);

		// 파일 삭제
		dao.deleteFile(spot_num);
		FileUtil.removeFile(request, db_spot.getFilename());

		mapAjax.put("result", "success");

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
