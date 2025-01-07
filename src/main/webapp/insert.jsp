<%@page import="learnbyteaching.emaillist.vo.EmailVo"%>
<%@page import="learnbyteaching.emaillist.dao.EmailListDao"%>
<%@page import="learnbyteaching.emaillist.dao.EmailListDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%    
// Ctrl + Shift + M : 캐럿이 위치한 대상에 필요한 특정 클래스 Import 시키기
ServletContext context = getServletContext();

String dbUser = context.getInitParameter("dbUser");
String dbPass = context.getInitParameter("dbPass");

// 폼 데이터 받아오기
String firstName = request.getParameter("fn");
String lastName = request.getParameter("ln");
String email = request.getParameter("email");

EmailListDao dao = new EmailListDaoImpl(dbUser, dbPass);
EmailVo vo = new EmailVo(lastName, firstName, email);
dao.insert(vo);

// Redirect (3xx)
response.sendRedirect("index.jsp");
%>
