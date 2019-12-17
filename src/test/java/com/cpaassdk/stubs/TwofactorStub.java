package com.cpaassdk.stubs;

import org.json.JSONObject;

public class TwofactorStub {
  public static JSONObject sendCodeAllRequestParams() {
    return new JSONObject()
      .put("method", "sms")
      .put("expiry", "test-expiry")
      .put("message", "test message {code}")
      .put("destinationAddress", "123")
      .put("length", "10")
      .put("type", "numeric");
  }

  public static JSONObject sendCodeAllParamsBody() {
    return new JSONObject()
      .put("code", new JSONObject()
        .put("method", "sms")
        .put("expiry", "test-expiry")
        .put("message", "test message {code}")
        .put("address", "123")
        .put("format", new JSONObject()
          .put("length", "10")
          .put("type", "numeric")
        )
      );
  }

  public static JSONObject sendCodeRequiredRequestParams() {
    return new JSONObject()
      .put("message", "test message {code}")
      .put("destinationAddress", "123");
  }

  public static JSONObject sendCodeRequiredParamsBody() {
    return new JSONObject()
      .put("code", new JSONObject()
        .put("method", "sms")
        .put("message", "test message {code}")
        .put("address", "123")
        .put("format", new JSONObject())
      );
  }

  public static JSONObject sendCodeValidResponse() {
    return new JSONObject()
      .put("code", new JSONObject()
        .put("resourceURL", "some/random/resource/url/valid-code-id")
      );
  }

  public static JSONObject verifyCodeRequestParams() {
    return new JSONObject()
      .put("verificationCode", "123123")
      .put("codeId", "valid-code-id");
  }

  public static JSONObject verifyCodeParamsBody() {
    return new JSONObject()
      .put("code", new JSONObject()
        .put("verify", "123123")
      );
  }
}
