package org.gradle.demo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.gradle.demo.BaseServlet;
import org.gradle.demo.lib.Json;

@WebServlet("/notifications/*")
public class NotificationServlet extends BaseServlet {
  private static final long serialVersionUID = 8911464474954972862L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(Json.read().toString());
    out.flush();
  }
}
