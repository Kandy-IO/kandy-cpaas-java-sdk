package org.gradle.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.gradle.demo.BaseServlet;
import org.gradle.demo.lib.Session;

@WebServlet("/dashboard/*")
public class DashboardServlet extends BaseServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (Session.isLoggedIn(request)) {
      request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    } else {
      response.sendRedirect("/login");
    }
  }
}
