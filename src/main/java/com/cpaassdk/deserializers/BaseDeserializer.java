package com.cpaassdk.deserializers;

import com.cpaassdk.Util;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

@SuppressWarnings("unchecked")
public class BaseDeserializer extends Util {
  private Boolean error = false;
  private String errorName = null;
  private String errorId = null;
  private String errorMessage = null;

  /**
   * @return the error
   */
  public Boolean hasError() {
    return this.error;
  }

  /**
   * @return the errorId
   */
  public String getErrorId() {
    return this.errorId;
  }

  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return this.errorMessage;
  }

  /**
   * @return the errorName
   */
  public String getErrorName() {
    return this.errorName;
  }

  @JsonProperty("requestError")
  private void unpackRequestError(Map<String, Object> error) {
    Map<String, Object> errorObj = (Map<String, Object>) error.get("serviceException");

    this.errorId = (String) errorObj.get("messageId");
    this.errorName = "Request error";
    this.errorMessage = composeMessage((String) errorObj.get("text"), (List<String>) errorObj.get("variables"));
    this.error = true;
  }

  @JsonProperty("serviceException")
  private void unpackServiceException(Map<String, Object> error) {
    this.errorId = (String) error.get("messageId");
    this.errorName = "Service exception";
    this.errorMessage = composeMessage((String) error.get("text"), (List<String>) error.get("variables"));
    this.error = true;
  }

  private String composeMessage(String template, List<String> chunks) {
    String message = template;

    for (int i = 0; i < chunks.size(); i++) {
      message = message.replace(String.format("%%%d", i + 1), chunks.get(i));
    }

    return message;
  }
}