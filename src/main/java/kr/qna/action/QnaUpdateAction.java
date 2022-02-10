package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class QnaUpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num==null) {
			return "redirect:/user/loginForm.do";
		}
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		int qna_num = Integer.parseInt(multi.getParameter("qna_num"));
		String filename = multi.getFilesystemName("filename");
		
		QnaDAO dao = QnaDAO.getInstance();
		QnaVO db_qna = dao.getQna(qna_num);
		if(user_num!=db_qna.getUser_num()) {
			FileUtil.removeFile(request, filename);
			return "/WEB-INF/veiws/common/notice.jsp";
		}
		QnaVO qna = new QnaVO();
		qna.setQna_num(qna_num);
		qna.setTitle(multi.getParameter("title"));
		qna.setContent(multi.getParameter("content"));
		qna.setIp(request.getRemoteAddr());
		qna.setViewable_check(Integer.parseInt(multi.getParameter("viewable_check")));
		qna.setFilename(filename);
		
		dao.updateQna(qna);
		
		if(filename!=null) {
			FileUtil.removeFile(request, db_qna.getFilename());
		}
		
		return "redirect:/qna/qnaDetail.do?qna_num="+qna_num;
	}

}
