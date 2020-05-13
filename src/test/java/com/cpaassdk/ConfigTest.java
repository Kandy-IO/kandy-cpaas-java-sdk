package com.cpaassdk;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

public class ConfigTest extends TestHelper {
  @Test
  public void constructorWithProjectCredentials() throws IOException, InterruptedException {
    String clientId = "test-client-id";
    String clientSecret = "test-client-id";
    String baseUrl = "https://test-base-url.com";
    Config config = new Config(clientId, clientSecret, baseUrl);

    assertEquals(clientId, config.clientId);
    assertEquals(clientSecret, config.clientSecret);
    assertEquals(baseUrl, config.baseUrl);
    assertEquals(null, config.email);
    assertEquals(null, config.password);
    assertEquals(clientId + "-java", config.clientCorrelator);
  }

  @Test
  public void constructorWithAccountCredentials() throws IOException, InterruptedException {
    String clientId = "test-client-id";
    String email = "email@test.com";
    String password = "test-password";
    String baseUrl = "https://test-base-url.com";
    Config config = new Config(clientId, email, password, baseUrl);

    assertEquals(clientId, config.clientId);
    assertEquals(null, config.clientSecret);
    assertEquals(email, config.email);
    assertEquals(password, config.password);
    assertEquals(baseUrl, config.baseUrl);
    assertEquals(clientId + "-java", config.clientCorrelator);
  }
}
