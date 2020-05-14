package org.gradle.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;

import org.gradle.demo.BaseServlet;
import com.cpaassdk.resources.Conversation;

@WebServlet("/send/*")
public class SendServlet extends BaseServlet {
  private static final long serialVersionUID = -258275452460732453L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    JSONObject messageParams = new JSONObject();
    messageParams.put("destinationAddress", request.getParameter("number"));
    messageParams.put("message", request.getParameter("message"));
    messageParams.put("senderAddress", this.dotenv.get("PHONE_NUMBER"));
    messageParams.put("type", Conversation.types.get("SMS"));

    Conversation message = this.client.conversation.createMessage(messageParams);

    if (message.hasError()) {
      String errorMessage = String.format("%s: %s (%s)", message.getErrorName(), message.getErrorMessage(), message.getErrorId());

      session.setAttribute("alertType", "error");
      session.setAttribute("alertMessage", errorMessage);
    } else {
      System.out.println(session);
      session.setAttribute("alertType", "success");
      session.setAttribute("alertMessage", "Success");
    }

    response.sendRedirect("/");
  }
}
