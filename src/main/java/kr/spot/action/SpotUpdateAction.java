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
import kr.util.StringUtil;

public class SpotUpdateAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		MultipartRequest multi = FileUtil.createFile(request);
		int spot_num = Integer.parseInt(multi.getParameter("spot_num"));
		String filename = multi.getFilesystemName("filename");

		SpotDAO dao = SpotDAO.getInstance();
		SpotVO db_spot = dao.getSpotBoard(spot_num);

		SpotVO spot = new SpotVO();
		spot.setSpot_num(spot_num);
		spot.setTitle(multi.getParameter("title"));
		spot.setContent(multi.getParameter("content"));
		spot.setCategory(Integer.parseInt(multi.getParameter("category")));
		spot.setFilename(filename);

		// 게시글 수정
		dao.updateSpotBoard(spot);

		// 전송된 파일이 있을 경우 이전 파일 삭제
		if (filename != null) {
			FileUtil.removeFile(request, db_spot.getFilename());
		}
		
		// alert 창으로 안내
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("<script>alert('게시글을 성공적으로 수정했습니다.'); location.href='spotDetail.do?spot_num=" + spot_num
				+ "';</script>");
		writer.close();

		// 해당 게시글로 리다이렉트
		return "redirect:/spot/spotDetail.do?spot_num=" + spot_num;
	}

}
