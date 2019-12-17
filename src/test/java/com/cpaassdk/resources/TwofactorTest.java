package com.cpaassdk.resources;

import com.cpaassdk.Sequence;
import com.cpaassdk.TestHelper;
import com.cpaassdk.stubs.TwofactorStub;

import java.io.IOException;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import static org.junit.Assert.*;

public class TwofactorTest extends TestHelper {
  @Test
  public void sendCodeWithAllParams() throws IOException, InterruptedException {

    Sequence sequence = (api, server) -> {
      Twofactor twofactor = new Twofactor(api);
      String url = String.format("/cpaas/auth/v1/%s/codes", api.userId);

      try {
        Twofactor response = twofactor.sendCode(TwofactorStub.sendCodeAllRequestParams());

        RecordedRequest request = server.takeRequest();

        JSONAssert.assertEquals(request.getBody().readUtf8(), TwofactorStub.sendCodeAllParamsBody(), true);
        assertEquals(url, request.getPath());
        assertEquals(response.codeId, "valid-code-id");

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(sequence, TwofactorStub.sendCodeValidResponse());
  }

  @Test
  public void sendCodeWithRequiredParams() throws IOException, InterruptedException {
    Sequence sequence = (api, server) -> {
      Twofactor twofactor = new Twofactor(api);
      String url = String.format("/cpaas/auth/v1/%s/codes", api.userId);

      try {
        Twofactor response = twofactor.sendCode(TwofactorStub.sendCodeRequiredRequestParams());

        RecordedRequest request = server.takeRequest();

        JSONAssert.assertEquals(request.getBody().readUtf8(), TwofactorStub.sendCodeRequiredParamsBody(), true);
        assertEquals(url, request.getPath());
        assertEquals(response.codeId, "valid-code-id");

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(sequence, TwofactorStub.sendCodeValidResponse());
  }

  @Test
  public void verifyCode() throws IOException, InterruptedException {
    // When code is valid
    Sequence successSequence = (api, server) -> {
      Twofactor twofactor = new Twofactor(api);
      JSONObject params = TwofactorStub.verifyCodeRequestParams();
      String url = String.format("/cpaas/auth/v1/%s/codes/%s/verify", api.userId, params.get("codeId"));

      try {
        Twofactor response = twofactor.verifyCode(params);

        RecordedRequest request = server.takeRequest();

        JSONAssert.assertEquals(request.getBody().readUtf8(), TwofactorStub.verifyCodeParamsBody(), true);
        assertEquals(url, request.getPath());
        assertEquals("Success", response.verificationMessage);
        assertTrue(response.verified);

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    // When code is invalid
    Sequence invalidSequence = (api, server) -> {
      Twofactor twofactor = new Twofactor(api);
      JSONObject params = TwofactorStub.verifyCodeRequestParams();
      String url = String.format("/cpaas/auth/v1/%s/codes/%s/verify", api.userId, params.get("codeId"));

      try {
        Twofactor response = twofactor.verifyCode(params);

        RecordedRequest request = server.takeRequest();

        JSONAssert.assertEquals(request.getBody().readUtf8(), TwofactorStub.verifyCodeParamsBody(), true);
        assertEquals(url, request.getPath());
        assertEquals("Code invalid or expired", response.verificationMessage);
        assertFalse(response.verified);

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(successSequence, null, true, 204);
    mock(invalidSequence, null, true, 404);
  }

  @Test
  public void resendCode() throws IOException, InterruptedException {
    // With only required params
    Sequence requiredParamsSequence = (api, server) -> {
      Twofactor twofactor = new Twofactor(api);
      String codeId = "test-code-id";
      String url = String.format("/cpaas/auth/v1/%s/codes/%s", api.userId, codeId);

      try {
        JSONObject params = TwofactorStub.sendCodeRequiredRequestParams();
        params.put("codeId", codeId);
        Twofactor response = twofactor.resendCode(params);

        RecordedRequest request = server.takeRequest();

        JSONAssert.assertEquals(request.getBody().readUtf8(), TwofactorStub.sendCodeRequiredParamsBody(), true);
        assertEquals(url, request.getPath());
        assertEquals(response.codeId, "valid-code-id");

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    // With all available params
    Sequence allParamsSequence = (api, server) -> {
      Twofactor twofactor = new Twofactor(api);
      String codeId = "test-code-id";
      String url = String.format("/cpaas/auth/v1/%s/codes/%s", api.userId, codeId);

      try {
        JSONObject params = TwofactorStub.sendCodeAllRequestParams();
        params.put("codeId", codeId);
        Twofactor response = twofactor.resendCode(params);

        RecordedRequest request = server.takeRequest();

        JSONAssert.assertEquals(request.getBody().readUtf8(), TwofactorStub.sendCodeAllParamsBody(), true);
        assertEquals(url, request.getPath());
        assertEquals(response.codeId, "valid-code-id");

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(requiredParamsSequence, TwofactorStub.sendCodeValidResponse());
    mock(allParamsSequence, TwofactorStub.sendCodeValidResponse());
  }
}