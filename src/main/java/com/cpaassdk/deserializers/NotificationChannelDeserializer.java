package com.cpaassdk.deserializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class NotificationChannelDeserializer extends BaseDeserializer {
  public String channelId = null;

  @JsonProperty("notificationChannel")
  private void unpackNotificationChannel(Map<String, Object> notificationChannel) {
    this.channelId = (String) notificationChannel.get("callbackURL");
  }
}
