package com.cpaassdk.models;

import java.util.Map;

public class SmsThread {
  public String remoteAddress = null;
  public String localAddress = null;
  public long firstMessageTime;
  public long lastMessageTime;
  public long lastPullTime;
  public String text = null;

  @SuppressWarnings("unchecked")
  public SmsThread(Map<String, Object> thread) {
    this.remoteAddress = (String) thread.get("remoteAddress");
    this.localAddress = (String) thread.get("localAddress");

    Map<String, Object> threadDetails = (Map<String, Object>) thread.get("threadDetails");

    this.firstMessageTime = (long) threadDetails.get("firstMessageTime");
    this.lastMessageTime = (long) threadDetails.get("lastMessageTime");
    this.lastPullTime = (long) threadDetails.get("lastPullTime");
    this.text = (String) threadDetails.get("lastText");
  }
}
