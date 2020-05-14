package org.gradle.demo;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import org.gradle.demo.BaseServlet;
import org.json.JSONObject;
import com.cpaassdk.resources.Notification;

import org.gradle.demo.lib.Json;

@WebServlet("/webhook/*")
public class WebhookServlet extends BaseServlet {
  private static final long serialVersionUID = -7381704910932548681L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Notification notification = this.client.notification.parse(getBody(request));

    JSONObject n = new JSONObject()
      .put("type", notification.type)
      .put("notificationId", notification.notificationId)
      .put("subscriptionId", notification.subscriptionId)
      .put("messageId", notification.messageId)
      .put("notificationDateTime", notification.notificationDateTime)
      .put("message", notification.message)
      .put("senderAddress", notification.senderAddress)
      .put("destinationAddress", notification.destinationAddress);

    Json.write(n);

    response.setStatus(HttpServletResponse.SC_OK);
  }

  public static JSONObject getBody(HttpServletRequest request) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;

    try {
      InputStream inputStream = request.getInputStream();
      if (inputStream != null) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] charBuffer = new char[128];
        int bytesRead = -1;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
          stringBuilder.append(charBuffer, 0, bytesRead);
        }
      } else {
        stringBuilder.append("");
      }
    } catch (IOException ex) {
        throw ex;
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException ex) {
          throw ex;
        }
      }
    }

    return new JSONObject(stringBuilder.toString());
  }
}


