package himedia.kdt.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learnbyteaching.emaillist.dao.EmailListDao;
import learnbyteaching.emaillist.dao.EmailListDaoImpl;
import learnbyteaching.emaillist.vo.EmailVo;

@WebServlet(name = "Emaillist", urlPatterns = "/el")
public class EmaillistServlet extends BaseServlet {

	/**
	 * @Author : 202-12
	 * @Date : 2025. 1. 8.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String actionName = req.getParameter("a");

//		a=form -> form.jsp로 요청 제어권 이전
		if ("form".equals(actionName)) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/form.jsp");
//			포워딩
			rd.forward(req, resp);
		} else if("delete".equals(actionName)) {
			EmailListDao dao = new EmailListDaoImpl(dbUser, dbPass);
			Long no = Long.valueOf(req.getParameter("no"));
			
			dao.delete(no);
			
//			게시물 홈으로 Redirect
			resp.sendRedirect(req.getContextPath() + "/el");
		} else {

//		DAO로 부터 데이터 객체를 불러옴
			EmailListDao dao = new EmailListDaoImpl(dbUser, dbPass);
			List<EmailVo> list = dao.getList();
//		요청에 속성으로 추가
			req.setAttribute("list", list);

//		목록 표시 page : index.jsp로 요청을 전달
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(req, resp);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lastName = req.getParameter("ln");
		String firstName = req.getParameter("fn");
		String email = req.getParameter("email");

//		dao 준비
		EmailListDao dao = new EmailListDaoImpl(dbUser, dbPass);
//		EmailVo 받아오기
		EmailVo vo = new EmailVo(lastName, firstName, email);
		
		boolean success = dao.insert(vo);
		
		if(success) {
			System.out.println("INSERT SUCCESS");
		} else {
			System.err.println("INSERT FAILED");
		}
		
//		리다이렉트 작업
		resp.sendRedirect(req.getContextPath() + "/el");
	}

}
