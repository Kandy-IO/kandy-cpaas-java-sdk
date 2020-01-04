package org.gradle.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.gradle.demo.BaseServlet;
import org.gradle.demo.lib.Session;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response = Session.logout(response);

    response.sendRedirect("/login");
  }
}