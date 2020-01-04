package com.cpaassdk;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;

import com.cpaassdk.Api;
import com.cpaassdk.Config;

public class ApiTest extends TestHelper {
  @Test
  public void constructor() throws IOException, InterruptedException {
    Sequence sequence = (api, server) -> {
      Config config = new Config("clientId", "clientSecret", "http://" + server.getHostName() + ":" + server.getPort());

      try {
        Api testApi = new Api(config);

        assertNotNull(testApi);
        assertNotNull(testApi.userId);
        assertNotNull(testApi.config);
      } catch (IOException e) {
        e.printStackTrace();
      }
    };

    mock(sequence, new JSONObject(tokenBody()));
  }

  @Test
  public void sendRequest() throws IOException, InterruptedException {
    Properties prop = new Properties();
    InputStream input = new FileInputStream("gradle.properties");

    prop.load(input);
    String version = prop.getProperty("VERSION");

    JSONObject responseBody = new JSONObject()
      .put("response-key", "response-value");

    Sequence getSequence = (api, server) -> {
      try {
        JSONObject response = api.sendRequest("/some-path", "get", new JSONObject(), true);
        RecordedRequest request = server.takeRequest();

        assertEquals(request.getHeader("X-Cpaas-Agent"), "java-sdk/" + (String) version);
        assertEquals(request.getHeader("Accept"), "*/*");
        assertEquals(request.getPath(), "/some-path");
        assertEquals(response.toString(), responseBody.toString());
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    Sequence postSequence = (api, server) -> {
      JSONObject requestBody = new JSONObject()
        .put("headers", new JSONObject()
          .put("header-key", "header-value")
        )
        .put("body", new JSONObject()
          .put("request-key", "request-value")
        );

      try {
        JSONObject response = api.sendRequest("/some-path", "post", requestBody, true);
        RecordedRequest request = server.takeRequest();

        assertEquals(request.getHeader("X-Cpaas-Agent"), "java-sdk/" + (String) version);
        assertEquals(request.getHeader("Accept"), "*/*");
        assertEquals(request.getHeader("header-key"), "header-value");
        assertEquals(request.getPath(), "/some-path");
        assertEquals(request.getBody().readUtf8(), requestBody.get("body").toString());
        assertEquals(response.toString(), responseBody.toString());
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(getSequence, responseBody);
    mock(postSequence, responseBody);
  }
}
