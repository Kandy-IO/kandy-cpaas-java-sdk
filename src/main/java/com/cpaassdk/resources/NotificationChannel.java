package com.cpaassdk.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.json.JSONObject;

import com.cpaassdk.Api;
import com.cpaassdk.deserializers.*;


public class NotificationChannel extends NotificationChannelDeserializer {
  private String baseUrl = null;
  private Api api = null;

  public NotificationChannel(Api api) {
    this.api = api;

    this.baseUrl = String.format("/cpaas/notificationchannel/v1/%s", api.userId);
  }

  public NotificationChannel createChannel(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();

    JSONObject body =
      new JSONObject()
        .put("notificationChannel", new JSONObject()
          .put("channelData", new JSONObject()
            .put("x-webhookURL", extractString(params, "webhookURL"))
          )
          .put("channelType", "webhooks")
          .put("clientCorrelator", this.api.config.clientCorrelator)
        );

    options.put("body", body);

    String url = String.format("%s/channels", this.baseUrl);

    JSONObject response = this.api.sendRequest(url, "post", options);

    return new ObjectMapper().readValue(response.toString(), NotificationChannel.class);
  }
}
