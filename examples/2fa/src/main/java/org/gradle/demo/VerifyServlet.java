package org.gradle.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;
import com.cpaassdk.resources.Twofactor;

import org.gradle.demo.BaseServlet;
import org.gradle.demo.lib.Session;

@WebServlet("/verify/*")
public class VerifyServlet extends BaseServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (Session.isLoggedIn(request)) {
      response.sendRedirect("/dashboard");
    } else if (!Session.credentialsVerified(request)) {
      response.sendRedirect("/login");
    } else {
      request.getRequestDispatcher("verify.jsp").forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    Twofactor twofactor = null;
    HttpSession session = request.getSession(false);

    if (pathInfo != null && pathInfo.equals("/sendcode")) {
      String codeType = request.getParameter("codeType");

      if (codeType.equals("sms")) {
        JSONObject params = new JSONObject();
        params.put("destinationAddress", this.dotenv.get("PHONE_NUMBER"));
        params.put("message", "Verification code: {code}");

        twofactor = this.client.twofactor.sendCode(params);
      } else {
        JSONObject params = new JSONObject();
        params.put("destinationAddress", this.dotenv.get("DESTINATION_EMAIL"));
        params.put("method", "email");
        params.put("message", "Verification code: {code}");
        params.put("subject", "Verification code");

        twofactor = this.client.twofactor.sendCode(params);
      }

      if (twofactor.hasError()) {
        String errorMessage = "%s: %s (%s)".format(twofactor.getErrorName(), twofactor.getErrorMessage(), twofactor.getErrorId());

        session.setAttribute("alertType", "error");
        session.setAttribute("alertMessage", errorMessage);
      } else {
        response = Session.setCodeId(response, twofactor.codeId);

        session.setAttribute("alertType", "success");
        session.setAttribute("alertMessage", "2FA code sent.");
      }

      response.sendRedirect("/verify");
    } else {
      String codeId = Session.getCodeId(request);

      JSONObject params = new JSONObject();
      params.put("verificationCode", request.getParameter("code"));
      params.put("codeId", codeId);

      twofactor = this.client.twofactor.verifyCode(params);

      if (twofactor.verified) {
        response = Session.login(response);
        response.sendRedirect("/dashboard");
      } else {
        session.setAttribute("alertType", "error");
        session.setAttribute("alertMessage", twofactor.verificationMessage);

        response.sendRedirect("/verify");
      }
    }
  }
}
