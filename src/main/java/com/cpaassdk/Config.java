package com.cpaassdk;

public class Config {
  String baseUrl = "";
  String clientId = "";
  String clientSecret = "";
  public String clientCorrelator = "";

  Config(String clientId, String clientSecret, String baseUrl) {
    this.baseUrl = baseUrl;
    this.clientCorrelator = String.format("%s-java", clientId);
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }
}
