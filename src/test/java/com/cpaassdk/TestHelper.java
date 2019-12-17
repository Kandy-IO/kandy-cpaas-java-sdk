package com.cpaassdk;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.JSONObject;

public class TestHelper {
  public void mock(Sequence sequence, JSONObject customResponse, Boolean withAuth, int responseCode) throws IOException, InterruptedException {
    MockWebServer server = new MockWebServer();

    if (withAuth) {
      server.enqueue(new MockResponse().setBody(tokenBody()));
    }

    if (customResponse != null && customResponse.length() > 0) {
      server.enqueue(new MockResponse().setBody(customResponse.toString()).setResponseCode(responseCode));
    } else {
      server.enqueue(new MockResponse().setBody("").setResponseCode(responseCode));
    }

    server.start();

    Api api = null;

    if (withAuth) {
      Config config = new Config("clientId", "clientSecret", "http://" + server.getHostName() + ":" + server.getPort());
      api = new Api(config);
      server.takeRequest();
    }

    sequence.execute(api, server);

    server.close();
  }

  public void mock(Sequence sequence, JSONObject customResponse, Boolean withAuth) throws IOException, InterruptedException {
    mock(sequence, customResponse, withAuth, 200);
  }

  public void mock(Sequence sequence, JSONObject customResponse) throws IOException, InterruptedException {
    mock(sequence, customResponse, true, 200);
  }

  public void mock(Sequence sequence) throws IOException, InterruptedException {
    mock(sequence, null, true, 200);
  }

  private static String tokenBody() {
    Algorithm algorithm = Algorithm.HMAC256("secret");
    String accessToken = JWT.create()
      .withIssuedAt(new Date())
      .withExpiresAt(expiryDateInHours(8))
      .sign(algorithm);

    String idToken = JWT.create()
      .withClaim("preferred_username", "test-username")
      .sign(algorithm);

    return new JSONObject()
      .put("access_token", accessToken)
      .put("id_token", idToken)
      .toString();
  }

  private static Date expiryDateInHours(int hours) {
    Date currentDate = new Date();
    Calendar c = Calendar.getInstance();

    c.setTime(currentDate);
    c.add(Calendar.HOUR, hours);

    return c.getTime();
  }
}
