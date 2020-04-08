package com.cpaassdk.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.json.JSONObject;

import com.cpaassdk.deserializers.*;

/**
 * CPaaS notification helper methods
 */
public class Notification extends NotificationDeserializer {
  /**
   * Parse inbound sms notification received in webhook. It parses the notification and returns
   * simplified version of the response.
   *
   * @param notification <b>JSONObject</b> received in the subscription webhook.
   *
   * @return Notification
   * @throws IOException raises exception
   */
  public Notification parse(JSONObject notification) throws IOException {
    return new ObjectMapper().readValue(notification.toString(), Notification.class);
  }
}
