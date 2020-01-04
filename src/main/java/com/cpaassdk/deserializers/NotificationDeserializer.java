package com.cpaassdk.deserializers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class NotificationDeserializer extends BaseDeserializer {
  public String notificationId = null;
  public String subscriptionId = null;
  public String messageId = null;
  public String type = null;
  public Long notificationDateTime = null;
  public String message = null;
  public String senderAddress = null;
  public String destinationAddress = null;
  public Map<String, String> eventDetails = null;

  @JsonProperty("inboundSMSMessageNotification")
  private void unpackInboundSMSMessageNotification(Map<String, Object> notification) {
    Map<String, String> inboundSMSMessage = (Map<String, String>) notification.get("inboundSMSMessage");

    this.type = "inbound";
    this.message = inboundSMSMessage.get("message");
    this.notificationId = (String) notification.get("id");
    this.notificationDateTime = (Long) notification.get("dateTime");
    this.messageId = inboundSMSMessage.get("messageId");
    this.senderAddress = inboundSMSMessage.get("senderAddress");
    this.destinationAddress = inboundSMSMessage.get("destinationAddress");
  }

  @JsonProperty("outboundSMSMessageNotification")
  private void unpackOutboundSMSMessageNotification(Map<String, Object> notification) {
    Map<String, String> inboundSMSMessage = (Map<String, String>) notification.get("inboundSMSMessage");

    this.type = "outbound";
    this.notificationId = (String) notification.get("id");
    this.notificationDateTime = (Long) notification.get("dateTime");
    this.message = inboundSMSMessage.get("message");
    this.messageId = inboundSMSMessage.get("messageId");
    this.senderAddress = inboundSMSMessage.get("senderAddress");
    this.destinationAddress = inboundSMSMessage.get("destinationAddress");
  }

  @JsonProperty("smsSubscriptionCancellationNotification")
  private void unpackSmsSubscriptionCancellationNotification(Map<String, Object> notification) {
    List<Map<String, String>> links = (List<Map<String, String>>) notification.get("links");

    this.type = "subscriptionCancel";

    this.notificationId = (String) notification.get("id");
    this.notificationDateTime = (Long) notification.get("dateTime");
    this.subscriptionId = idFrom(links.get(0).get("href"));
  }

  @JsonProperty("smsEventNotification")
  private void unpackSmsEventNotification(Map<String, Object> notification) {
    List<Map<String, String>> links = (List<Map<String, String>>) notification.get("links");

    this.type = "event";

    this.notificationId = (String) notification.get("id");
    this.notificationDateTime = (Long) notification.get("dateTime");
    this.subscriptionId = idFrom(links.get(0).get("href"));
    this.eventDetails.put("eventDescription", (String) notification.get("eventDescription"));
    this.eventDetails.put("eventType", (String) notification.get("eventType"));
  }
}
