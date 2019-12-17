package com.cpaassdk.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.json.JSONObject;

import com.cpaassdk.Api;
import com.cpaassdk.deserializers.*;

/**
* CPaaS provides Authentication API where a two-factor authentication (2FA) flow can be implemented by using that.
* Sections below describe two sample use cases, two-factor authentication via SMS and two-factor authentication via e-mail
*
*/
public class Twofactor extends TwofactorDeserializer {
  private String baseUrl = null;
  private Api api = null;

  public Twofactor(Api api) {
    this.api = api;

    this.baseUrl = String.format("/cpaas/auth/v1/%s", api.userId);
  }

  Twofactor() { }

  /**
   * Create a new authentication code
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.destinationAddress <b>Array[String] | String</b> Destination address of the authentication code being sent. For sms type authentication codes, it should contain a E164 phone number. For e-mail type authentication codes, it should contain a valid e-mail address.
   * @param params.message <b>String</b> Message text sent to the destination, containing the placeholder for the code within the text. CPaaS requires to have *{code}* string within the text in order to generate a code and inject into the text. For email type code, one usage is to have the *{code}* string located within the link in order to get a unique link.
   * @param params.subject <b>String</b> <i>Optional</i> When the method is passed as email then subject becomes a mandatory field to pass. The value passed becomes the subject line of the 2FA code email that is sent out to the destinationAddress.
   * @param params.method <b>String</b> <i>Optional</i> Default - sms. Type of the authentication code delivery method, sms and email are supported types. Possible values: sms, email
   * @param params.expiry <b>String</b> <i>Optional</i> Default - 120. Lifetime duration of the code sent in seconds. This can contain values between 30 and 3600 seconds.
   * @param params.length <b>String</b> <i>Optional</i> Default - 6. Length of the authentication code tha CPaaS should generate for this request. It can contain values between 4 and 10.
   * @param params.type <b>String</b> <i>Optional</i> Default - "numeric". Type of the code that is generated. If not provided, default value is numeric. Possible values: numeric, alphanumeric, alphabetic
   *
   * @return Twofactor
   * @throws IOException Exception raised
   */
  public Twofactor sendCode(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();
    JSONObject body =
      new JSONObject()
        .put("code", new JSONObject()
          .put("method", extractString(params, "method", "sms"))
          .put("expiry", extractString(params, "expiry"))
          .put("message", extractString(params, "message"))
          .put("address", extractString(params, "destinationAddress"))
          .put("subject", extractString(params, "subject"))
          .put("format", new JSONObject()
            .put("length", extractString(params, "length"))
            .put("type", extractString(params, "type"))
          )
        );

    options.put("body", body);

    String url = String.format("%s/codes", this.baseUrl);
    JSONObject response = this.api.sendRequest(url, "post", options);

    return new ObjectMapper().readValue(response.toString(), Twofactor.class);
  }


  /**
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.codeId <b>String</b> ID of the authentication code.
   * @param params.verificationCode <b>String</b> Code that is being verified.
   *
   * @return Twofactor
   * @throws IOException Exception raised
   */
  public Twofactor verifyCode(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();
    JSONObject body =
      new JSONObject()
        .put("code", new JSONObject()
          .put("verify", extractString(params, "verificationCode"))
        );

    options.put("body", body);

    String url = String.format("%s/codes/%s/verify", this.baseUrl, params.get("codeId"));
    JSONObject response = this.api.sendRequest(url, "put", options);

    return new ObjectMapper().readValue(response.toString(), Twofactor.class);
  }


  /**
   * Create a new authentication code
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.codeId <b>String</b> ID of the authentication code.
   * @param params.destinationAddress <b>Array[String] | String</b> Destination address of the authentication code being sent. For sms type authentication codes, it should contain a E164 phone number. For e-mail type authentication codes, it should contain a valid e-mail address.
   * @param params.message <b>String</b> Message text sent to the destination, containing the placeholder for the code within the text. CPaaS requires to have *{code}* string within the text in order to generate a code and inject into the text. For email type code, one usage is to have the *{code}* string located within the link in order to get a unique link.
   * @param params.subject <b>String</b> <i>Optional</i> When the method is passed as email then subject is a mandatory field to pass. The value passed becomes the subject line of the 2FA code email that is sent out to the destinationAddress.
   * @param params.method <b>String</b> <i>Optional</i> Default - "sms". Type of the authentication code delivery method, sms and email are supported types. Possible values: sms, email
   * @param params.expiry <b>String</b> <i>Optional</i> Default - 120. Lifetime duration of the code sent in seconds. This can contain values between 30 and 3600 seconds.
   * @param params.length <b>String</b> <i>Optional</i> Default - 6. Length of the authentication code tha CPaaS should generate for this request. It can contain values between 4 and 10.
   * @param params.type <b>String</b> <i>Optional</i> Default - "numeric". Type of the code that is generated. If not provided, default value is numeric. Possible values: numeric, alphanumeric, alphabetic
   *
   * @return Twofactor
   * @throws IOException Exception raised
   */
  public Twofactor resendCode(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();
    JSONObject body =
      new JSONObject()
        .put("code", new JSONObject()
          .put("method", extractString(params, "method", "sms"))
          .put("expiry", extractString(params, "expiry"))
          .put("message", extractString(params, "message"))
          .put("address", extractString(params, "destinationAddress"))
          .put("subject", extractString(params, "subject"))
          .put("format", new JSONObject()
            .put("length", extractString(params, "length"))
            .put("type", extractString(params, "type"))
          )
        );

    options.put("body", body);

    String url = String.format("%s/codes/%s", this.baseUrl, params.get("codeId"));
    JSONObject response = this.api.sendRequest(url, "put", options);

    return new ObjectMapper().readValue(response.toString(), Twofactor.class);
  }

  /**
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.codeId <b>String</b> ID of the authentication code.
   *
   * @return Twofactor
   * @throws IOException Exception raised
   */
  public Twofactor deleteCode(JSONObject params) throws IOException {
    String url = String.format("%s/codes/%s", this.baseUrl, params.get("codeId"));
    JSONObject response = this.api.sendRequest(url, "delete");

    return new ObjectMapper().readValue(response.toString(), Twofactor.class);
  }
}
