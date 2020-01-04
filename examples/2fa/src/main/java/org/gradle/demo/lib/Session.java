package org.gradle.demo.lib;

import javax.servlet.http.*;
import javax.servlet.http.Cookie;

public class Session {
  public static Boolean isLoggedIn(HttpServletRequest request) {
    String credentialsVerified = get(request, "credentialsVerified");
    String codeVerified = get(request, "codeVerified");

    return (!credentialsVerified.isEmpty() && credentialsVerified.equals("true") && !codeVerified.isEmpty() && codeVerified.equals("true"));
  }

  public static Boolean credentialsVerified(HttpServletRequest request) {
    String credentialsVerified = get(request, "credentialsVerified");

    return (!credentialsVerified.isEmpty() && credentialsVerified.equals("true"));
  }

  public static HttpServletResponse login(HttpServletResponse response) {
    Cookie credentialsVerified = new Cookie("credentialsVerified", "true");
    Cookie codeVerified = new Cookie("codeVerified", "true");

    response.addCookie(credentialsVerified);
    response.addCookie(codeVerified);

    return response;
  }

  public static HttpServletResponse logout(HttpServletResponse response) {
    Cookie credentialsVerified = new Cookie("credentialsVerified", "false");
    Cookie codeVerified = new Cookie("codeVerified", "false");

    response.addCookie(credentialsVerified);
    response.addCookie(codeVerified);

    return response;
  }

  public static HttpServletResponse setCredentialsVerified(HttpServletResponse response) {
    Cookie credentialsVerified = new Cookie("credentialsVerified", "true");
    Cookie codeVerified = new Cookie("codeVerified", "false");

    response.addCookie(credentialsVerified);
    response.addCookie(codeVerified);

    return response;
  }

  public static HttpServletResponse setCodeId(HttpServletResponse response, String codeId) {
    Cookie codeIdCookie = new Cookie("codeId", codeId);

    response.addCookie(codeIdCookie);

    return response;
  }

  public static String getCodeId(HttpServletRequest request) {
    return get(request, "codeId");
  }

  private static String get(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
          Cookie cookie = cookies[i];
          String cookieName = cookie.getName();
          String cookieValue = cookie.getValue();

          if (cookieName.equals(name)) {
            return cookieValue;
          }
      }
    }

    return "";
  }
}