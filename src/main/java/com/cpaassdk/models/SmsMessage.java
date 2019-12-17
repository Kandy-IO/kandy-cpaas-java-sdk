package com.cpaassdk.models;

import java.util.Map;

public class SmsMessage {
  public String type = null;
  public String messageId = null;
  public String message = null;
  public String status = null;
  // public Integer dateTime;

  public SmsMessage(Map<String, Object> thread) {
    this.type = (String) thread.get("type");
    this.messageId = (String) thread.get("messageId");
    this.message = (String) thread.get("message");
    this.status = (String) thread.get("status");
    this.status = (String) thread.get("status");
    // this.dateTime = (Integer) thread.get("dateTime");
  }
}
