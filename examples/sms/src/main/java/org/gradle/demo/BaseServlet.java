package org.gradle.demo;

import javax.servlet.http.*;
import io.github.cdimascio.dotenv.Dotenv;

import com.cpaassdk.Client;

public class BaseServlet extends HttpServlet {
  private static final long serialVersionUID = -2431546492378039755L;
  public Client client = null;
  public Dotenv dotenv = null;

  @Override
  public void init() {
    this.dotenv = Dotenv.load();

    this.client = new Client(
      this.dotenv.get("CLIENT_ID"),
      this.dotenv.get("CLIENT_SECRET"),
      this.dotenv.get("BASE_URL")
    );
  }
}
