package com.cpaassdk;

public class Config {
  String baseUrl = null;
  String clientId = null;
  String clientSecret = null;
  String email = null;
  String password = null;
  public String clientCorrelator = null;

  Config(String clientId, String clientSecret, String baseUrl) {
    this.baseUrl = baseUrl;
    this.clientCorrelator = String.format("%s-java", clientId);
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  Config(String clientId, String email, String password, String baseUrl) {
    this.baseUrl = baseUrl;
    this.clientId = clientId;
    this.email = email;
    this.password = password;
    this.clientCorrelator = String.format("%s-java", clientId);
  }
}
