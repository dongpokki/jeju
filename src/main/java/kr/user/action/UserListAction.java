package kr.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;
import kr.util.PagingUtil;

public class UserListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		if(user_auth < 3) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		if(keyfield == null) keyfield = "";
		if(keyword == null) keyword = "";
		
		UserDAO dao = UserDAO.getInstance();
		int count = dao.getUserCountByAdmin(keyfield, keyword);
		
		//페이지 처리
		//keyfield,keyword,currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(keyfield,keyword,
				          Integer.parseInt(pageNum),count,20,10,"userList.do");
		
		List<UserVO> list = null;
		if(count > 0) {
			list = dao.getListUserByAdmin(page.getStartCount(), 
					                        page.getEndCount(), 
					                        keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/user/userList.jsp";
	}

}



