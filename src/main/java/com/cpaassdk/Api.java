package com.cpaassdk;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;
import okhttp3.*;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class Api {
  public static final MediaType JSON = MediaType.get("application/json");
  public Config config;
  public String userId;
  private String accessToken = null;
  private DecodedJWT accessTokenParsed;
  private DecodedJWT idTokenParsed;
  private String idToken = null;
  final String VERSION = "1.0.0";

  Api(Config config) throws IOException {
    this.config = config;

    this.authToken();
  }

  JSONObject sendRequest(String path, String verb, JSONObject options, boolean withToken) throws IOException {
    Request request = null;
    OkHttpClient client = new OkHttpClient();
    String url = buildUrl(path, options);
    RequestBody body = buildBody(options);
    Request.Builder requestBuilder = new Request.Builder()
    .url(url);

    requestBuilder = addHeaders(requestBuilder, options, withToken);
    switch (verb) {
      case "get":
        request = requestBuilder.build();
        break;
      case "post":
        request = requestBuilder.post(body).build();
        break;
      case "put":
        request = requestBuilder.put(body).build();
        break;
      case "delete":
        request = requestBuilder.delete(body).build();
        break;
      default:
        break;
    }

    try (Response response = client.newCall(request).execute()) {
      return handleResponse(response);
    }
  }

  public JSONObject sendRequest(String path) throws IOException {
    return sendRequest(path, "get", new JSONObject(), true);
  }

  public JSONObject sendRequest(String path, String verb) throws IOException {
    return sendRequest(path, verb, new JSONObject(), true);
  }

  public JSONObject sendRequest(String path, String verb, JSONObject options) throws IOException {
    return sendRequest(path, verb, options, true);
  }

  JSONObject handleResponse(Response response) throws IOException {
    String responseBodyString = response.body().string();

    if (responseBodyString.isEmpty()) {
      return new JSONObject()
        .put("statusCode", response.code());
    }

    return new JSONObject(responseBodyString);
  }

  private String buildUrl(String path, JSONObject options) {
    HttpUrl.Builder urlBuilder = HttpUrl.parse(this.config.baseUrl + path).newBuilder();
    JSONObject queryParams = options.has("query") ? (JSONObject) options.get("query") : null;

    if (queryParams != null) {
      for (String key : queryParams.keySet()) {
        urlBuilder.addQueryParameter(key, (String) queryParams.get(key));
      }
    }

    return urlBuilder.build().toString();
  }

  private RequestBody buildBody(JSONObject options) {
    RequestBody body = null;

    if (options.has("body")) {
      if (options.has("headers") &&
          ((JSONObject) options.get("headers")).has("Content-Type") &&
          ((JSONObject) options.get("headers")).get("Content-Type").equals("application/x-www-form-urlencoded")) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        JSONObject bodyObj = (JSONObject) options.get("body");

        for (String key : bodyObj.keySet()) {
          formBuilder.add(key, (String) bodyObj.get(key));
        }

        body = formBuilder.build();
      } else {
        Charset charset = Charset.forName("UTF-8");
        byte[] bytes = options.get("body").toString().getBytes(charset);
        body = RequestBody.create(bytes, JSON);
      }
    }

    return body;
  }

  private Request.Builder addHeaders(Request.Builder request, JSONObject options, boolean withToken) throws IOException {
    request.addHeader("X-Cpaas-Agent", "java-sdk/" + VERSION)
      .addHeader("Accept", "*/*");

    JSONObject headers = options.has("headers") ? (JSONObject) options.get("headers") : null;

    if (headers != null) {
      for (String key : headers.keySet()) {
        request.addHeader(key, (String) headers.get(key));
      }
    }

    if (withToken) {
      String authToken = authToken();

      request.addHeader("Authorization", "Bearer " + authToken);
    }

    return request;
  }

  private String authToken() throws IOException {
    if (tokenExpired()) {
      JSONObject tokens = getTokens();

      setTokens(tokens);
    }

    return this.accessToken;
  }

  private void setTokens(JSONObject tokens) {
    if (tokens.length() == 0) {
      this.accessToken = null;
      this.idToken = null;
      this.accessTokenParsed = null;
    } else {
      this.accessToken = (String) tokens.get("access_token");
      this.idToken = (String) tokens.get("id_token");

      this.accessTokenParsed = JWT.decode(accessToken);
      this.idTokenParsed =  JWT.decode(idToken);

      Map<String, Claim> claims = this.idTokenParsed.getClaims();

      this.userId = claims.get("preferred_username").asString();
    }
  }

  private JSONObject getTokens() throws IOException {
    JSONObject options = new JSONObject();
    JSONObject body = new JSONObject();
    JSONObject headers = new JSONObject();

    body.put("grant_type", "client_credentials");
    body.put("client_id", this.config.clientId);
    body.put("client_secret", this.config.clientSecret);
    body.put("scope", "openid");

    headers.put("Content-Type", "application/x-www-form-urlencoded");

    options.put("body", body);
    options.put("headers", headers);

    return sendRequest("/cpaas/auth/v1/token", "post", options, false);
  }

  private boolean tokenExpired() {
    if (this.accessToken == null) {
      return true;
    }

    Date now = new Date();
    long minBuffer = (this.accessTokenParsed.getExpiresAt().getTime() - this.accessTokenParsed.getIssuedAt().getTime()) / 2;
    long expiresIn = this.accessTokenParsed.getExpiresAt().getTime() - now.getTime() - minBuffer;

    return expiresIn < 0;
  }
}
