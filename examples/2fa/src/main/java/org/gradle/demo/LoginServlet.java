package org.gradle.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.gradle.demo.BaseServlet;
import org.gradle.demo.lib.Session;

@WebServlet("/login/*")
public class LoginServlet extends BaseServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (Session.isLoggedIn(request)) {
      response.sendRedirect("/dashboard");
    } else {
      request.getRequestDispatcher("login.jsp").forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (Session.isLoggedIn(request)) {
      response.sendRedirect("/dashboard");
    } else {
      String email = request.getParameter("email");
      String password = request.getParameter("password");

      if (email.equals(this.dotenv.get("EMAIL")) && password.equals(this.dotenv.get("PASSWORD"))) {
        response = Session.setCredentialsVerified(response);
        HttpSession session = request.getSession(false);

        session.setAttribute("alertType", "success");
        session.setAttribute("alertMessage", "Login success! Press 'Send 2FA' button to receive 2FA code in via sms or email.");
        response.sendRedirect("/verify");
      } else {
        request.getRequestDispatcher("login.jsp").forward(request, response);
      }
    }
  }
}