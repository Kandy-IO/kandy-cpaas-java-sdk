package com.cpaassdk.models;

import java.util.Map;
import com.cpaassdk.Util;

public class Subscription {
  public String destinationAddress = null;
  public String id = null;
  public String notifyURL = null;

  @SuppressWarnings("unchecked")
  public Subscription(Map<String, Object> subscription) {
    this.destinationAddress = (String) subscription.get("destinationAddress");

    Map<String, String> callbackReference = (Map<String, String>) subscription.get("callbackReference");
    this.notifyURL = callbackReference.get("notifyURL");

    this.id = Util.idFrom((String) subscription.get("resourceURL"));
  }
}
