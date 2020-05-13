package com.cpaassdk;

import java.io.IOException;

import com.cpaassdk.resources.*;

public class Client {
  public Config config;
  public Twofactor twofactor;
  public Conversation conversation;
  public Notification notification;

  /**
   * Configure the SDK with clientId and clientSecret.
   *
   * <pre class="example">
   * <code>
   *  Client client = new Client(
   *    "private project key",
   *    "private project secret",
   *    "base url"
   *  );
   * </code>
   * </pre>
   *
   * @param clientId <b>String</b> Private project key / Account client ID. If Private project key is used then client_secret is mandatory. If account client ID is used then email and password are mandatory.
   * @param baseUrl <b>String</b> URL of the server to be used.
   * @param clientSecret <b>String</b> Private project secret
   *
   */
  public Client(String clientId, String clientSecret, String baseUrl) {
    this.config = new Config(clientId, clientSecret, baseUrl);

    initializeResources();
  }

  /**
   * Configure the SDK with clientId and email/password.
   *
   * <pre class="example">
   * <code>
   *  Client client = new Client(
   *    "account client id",
   *    "account email",
   *    "account password",
   *    "base url"
   *  );
   * </code>
   * </pre>
   *
   * @param clientId <b>String</b> Private project key / Account client ID. If Private project key is used then client_secret is mandatory. If account client ID is used then email and password are mandatory.
   * @param email <b>String</b> Account login email.
   * @param password <b>String</b> Account login password.
   * @param baseUrl <b>String</b> URL of the server to be used.
   *
   */

  public Client(String clientId, String email, String password, String baseUrl) {
    this.config = new Config(clientId, email, password, baseUrl);

    initializeResources();
  }

  void initializeResources() {
    try {
      Api api = new Api(this.config);

      this.twofactor = new Twofactor(api);
      this.conversation = new Conversation(api);
      this.notification = new Notification();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

