package com.cpaassdk;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

import com.cpaassdk.Config;

public class ConfigTest extends TestHelper {
  @Test
  public void constructor() throws IOException, InterruptedException {
    Config config = new Config("clientId", "clientSecret", "baseUrl");

    assertEquals("clientId", config.clientId);
    assertEquals("clientSecret", config.clientSecret);
    assertEquals("baseUrl", config.baseUrl);
    assertEquals("clientId-java", config.clientCorrelator);
  }
}
