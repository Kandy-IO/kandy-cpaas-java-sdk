package com.cpaassdk.stubs;

import org.json.JSONObject;

public class NotificationStub {
  public static JSONObject smsInbound() {
    return new JSONObject("{\r\n  \"inboundSMSMessageNotification\": {\r\n    \"inboundSMSMessage\": {\r\n      \"dateTime\": 1525895987000,\r\n      \"destinationAddress\": \"+16137001234\",\r\n      \"message\": \"hi\",\r\n      \"messageId\": \"O957s10JReNV\",\r\n      \"senderAddress\": \"+16139998877\"\r\n    },\r\n    \"dateTime\": 1525895987000,\r\n    \"id\": \"441fc36e-aab7-45dd-905c-4aaec7a7464d\"\r\n  }\r\n}");
  }

  public static JSONObject smsOutbound() {
    return new JSONObject("{\r\n  \"outboundSMSMessageNotification\": {\r\n    \"outboundSMSMessage\": {\r\n      \"dateTime\": 1525895987000,\r\n      \"destinationAddress\": \"+16139998877\",\r\n      \"message\": \"hi\",\r\n      \"messageId\": \"olr3j20Cdx87\",\r\n      \"senderAddress\": \"+16137001234\"\r\n    },\r\n    \"dateTime\": 1525895987000,\r\n    \"id\": \"441fc36e-aab7-45dd-905c-4aaec7a7464d\"\r\n  }\r\n}");
  }

  public static JSONObject smsSubscriptionCancellation() {
    return new JSONObject("{\r\n  \"smsSubscriptionCancellationNotification\": {\r\n    \"link\": [\r\n      {\r\n        \"href\": \"/cpaas/smsmessaging/v1/e33c51d7-6585-4aee-88ae-005dfae1fd3b/inbound/subscriptions/f179f10b-e846-4370-af20-db5f7dc0f985\",\r\n        \"rel\": \"Subscription\"\r\n      }\r\n    ],\r\n    \"dateTime\": 1525895987000,\r\n    \"id\": \"441fc36e-aab7-45dd-905c-4aaec7a7464d\"\r\n  }\r\n}");
  }

  public static JSONObject smsEvent() {
    return new JSONObject("{\r\n  \"smsEventNotification\": {\r\n    \"eventDescription\": \"A message has been deleted.\",\r\n    \"eventType\": \"MessageDeleted\",\r\n    \"link\": [\r\n      {\r\n        \"href\": \"/cpaas/smsmessaging/v1/92ef716d-42c7-4706-a123-b36cac9a2f97/remoteAddresses/+12013000113/localAddresses/+12282202950/messages/SM5C24C4AB0001020821100077367A8A\",\r\n        \"rel\": \"smsMessage\"\r\n      }\r\n    ],\r\n    \"id\": \"8c30d6c7-d15e-41a0-800b-e7dc401403fb\",\r\n    \"dateTime\": 1545995973646\r\n  }\r\n}");
  }
}