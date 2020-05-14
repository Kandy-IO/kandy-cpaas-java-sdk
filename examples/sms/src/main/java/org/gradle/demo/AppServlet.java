package org.gradle.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AppServlet", loadOnStartup = 1)
public class AppServlet extends BaseServlet {
  private static final long serialVersionUID = 5736844113933764478L;
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher("index.jsp").forward(request, response);
  }
}
