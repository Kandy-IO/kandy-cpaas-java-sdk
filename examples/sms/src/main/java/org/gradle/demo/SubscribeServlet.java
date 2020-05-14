package org.gradle.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;

import org.gradle.demo.BaseServlet;
import com.cpaassdk.resources.Conversation;

@WebServlet("/subscribe/*")
public class SubscribeServlet extends BaseServlet {
  private static final long serialVersionUID = -7381704910932548681L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);

    JSONObject subscriptionParams = new JSONObject();
    subscriptionParams.put("destinationAddress", this.dotenv.get("PHONE_NUMBER"));
    subscriptionParams.put("webhookURL", String.format("%s/webhook", request.getParameter("webhook")));
    subscriptionParams.put("type", Conversation.types.get("SMS"));

    Conversation subscription = this.client.conversation.subscribe(subscriptionParams);

    if (subscription.hasError()) {
      String errorMessage = String.format("%s: %s (%s)", subscription.getErrorName(), subscription.getErrorMessage(), subscription.getErrorId());

      session.setAttribute("alertType", "error");
      session.setAttribute("alertMessage", errorMessage);
    } else {
      session.setAttribute("alertType", "success");
      session.setAttribute("alertMessage", "Created subscription");
    }

    response.sendRedirect("/");
  }
}
