package com.cpaassdk;

import java.io.IOException;

import com.cpaassdk.resources.*;

public class Client {
  Config config;
  public Twofactor twofactor;

  /**
   * Configure the SDK with clientId and clientSecret.
   *
   * <pre class="example">
   * <code>
   *   Client client = new Client(
   *    "private project key",
   *    "private project secret",
   *    "base url"
   *  );
   * </code>
   * </pre>
   *
   * @param clientId <b>String</b> Private project secret
   * @param clientSecret <b>String</b> Private project secret
   * @param baseUrl <b>String</b> URL of the server to be used.
   *
   */
  public Client(String clientId, String clientSecret, String baseUrl) {
    this.config = new Config(clientId, clientSecret, baseUrl);

    try {
      Api api = new Api(this.config);

      this.twofactor = new Twofactor(api);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

