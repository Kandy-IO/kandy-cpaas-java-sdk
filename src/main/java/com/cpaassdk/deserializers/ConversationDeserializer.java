package com.cpaassdk.deserializers;

import com.cpaassdk.models.SmsMessage;
import com.cpaassdk.models.SmsThread;
import com.cpaassdk.models.Subscription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationDeserializer extends BaseDeserializer {
  public String sentMessage = null;
  public String senderAddress = null;
  public List<Map<String,Integer>> deliveryInfo = null;
  public String status = null;
  public SmsThread[] smsThreads;
  public SmsThread smsThread = null;
  public SmsMessage[] messages;
  public SmsMessage message = null;
  public Subscription[] subscriptions;
  public Subscription subscription = null;

  @JsonProperty("outboundSMSMessageRequest")
  private void unpackOutboundMessageRequest(Map<String, Object> response) {
    this.senderAddress = (String) response.get("senderAddress");

    Map<String, String> outbound = (Map<String, String>) response.get("outboundSMSTextMessage");
    this.sentMessage = outbound.get("message");

    Map<String, Object> deliveryList = (Map<String, Object>) response.get("deliveryInfoList");
    this.deliveryInfo = (List<Map<String, Integer>>) deliveryList.get("deliveryInfo");
  }

  @JsonProperty("smsThreadList")
  private void unpackSmsThreadList(Map<String, Object> response) {
    List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("smsThread");

    int index = 0;
    this.smsThreads = new SmsThread[list.size()];

    for (Map<String, Object> listItem : list) {
      this.smsThreads[index] = new SmsThread(listItem);
    }
  }

  @JsonProperty("smsThread")
  private void unpackSmsThread(Map<String, Object> response) {
    this.smsThread = new SmsThread(response);
  }

  @JsonProperty("smsMessageList")
  private void unpackSmsMessageList(Map<String, Object> response) {
    List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("smsMessage");

    int index = 0;
    this.messages = new SmsMessage[list.size()];

    for (Map<String, Object> listItem : list) {
      this.messages[index] = new SmsMessage(listItem);
    }
  }

  @JsonProperty("smsMessage")
  private void unpackSmsMessage(Map<String, Object> response) {
    this.message = new SmsMessage(response);
  }

  @JsonProperty("status")
  private void unpackStatus(String status) {
    this.status = status;
  }

  @JsonProperty("subscriptionList")
  private void unpackSubscriptionList(Map<String, Object> response) {
    List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("subscription");

    int index = 0;
    this.subscriptions = new Subscription[list.size()];

    for (Map<String, Object> listItem : list) {
      this.subscriptions[index] = new Subscription(listItem);
    }
  }

  @JsonProperty("subscription")
  private void unpackSubscription(Map<String, Object> response) {
    this.subscription = new Subscription(response);
  }
}

