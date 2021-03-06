package cn.wxjia.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 8993493049503406102L;

	public VerifyCodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String inputVerifyCode = request.getParameter("verifyCode");
		String createVerifyCode = (String) request.getSession().getAttribute(
				"verifyCode");
		// System.out.println("inputVerifyCode="+inputVerifyCode);
		// System.out.println("createVerifyCode="+createVerifyCode);
		if (inputVerifyCode.equalsIgnoreCase(createVerifyCode)) {
			out.print("true");
		} else {
			out.print("false");
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
	}
}
