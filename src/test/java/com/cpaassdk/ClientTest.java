package com.cpaassdk;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;
import org.json.JSONObject;

public class ClientTest extends TestHelper {
  @Test
  public void constructorWithProjectParams() throws IOException, InterruptedException {
    Sequence sequence = (api, server) -> {
      Client client = new Client("clientId", "clientSecret", "http://" + server.getHostName() + ":" + server.getPort());

      assertNotNull(client.config);
      assertNotNull(client.twofactor);
      assertNotNull(client.conversation);
      assertNotNull(client.notification);
    };

    mock(sequence, new JSONObject(tokenBody()));
  }

  @Test
  public void constructorWithAccountParams() throws IOException, InterruptedException {
    Sequence sequence = (api, server) -> {
      Client client = new Client("clientId", "email@test.com", "password", "http://" + server.getHostName() + ":" + server.getPort());

      assertNotNull(client.config);
      assertNotNull(client.twofactor);
      assertNotNull(client.conversation);
      assertNotNull(client.notification);
    };

    mock(sequence, new JSONObject(tokenBody()));
  }
}
