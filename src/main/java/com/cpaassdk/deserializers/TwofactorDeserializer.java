package com.cpaassdk.deserializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class TwofactorDeserializer extends BaseDeserializer {
  public String codeId = null;
  public boolean verified = false;
  public String verificationMessage = null;

  @JsonProperty("code")
  private void unpackResourceUrl(Map<String,String> code) {
    this.codeId = idFrom(code.get("resourceURL"));
  }

  @JsonProperty("statusCode")
  private void unpackStatusCode(int code) {
    if (code == 204) {
      this.verified = true;
      this.verificationMessage = "Success";
    } else {
      this.verified = false;
      this.verificationMessage = "Code invalid or expired";
    }
  }
}
