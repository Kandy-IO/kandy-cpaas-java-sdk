package com.cpaassdk.resources;

import com.cpaassdk.Sequence;
import com.cpaassdk.TestHelper;
import com.cpaassdk.stubs.ConversationStub;

import java.io.IOException;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.Assert.*;

public class ConversationTest extends TestHelper {
  @Test
  public void createConversation() throws IOException, InterruptedException {

    Sequence sequence = (api, server) -> {
      Conversation conversation = new Conversation(api);
      JSONObject params = ConversationStub.createMessageRequestParams(api);
      String url = String.format("/cpaas/smsmessaging/v1/%s/outbound/%s/requests", api.userId, params.get("senderAddress"));

      try {
        Conversation response = conversation.createMessage(params);

        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
        assertEquals(params.get("message"), response.sentMessage);
        assertEquals(params.get("senderAddress"), response.senderAddress);
        JSONAssert.assertEquals(request.getBody().readUtf8(), ConversationStub.createMessageRequestBody(api), true);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };
    JSONObject validResponse = ConversationStub.createMessageValidResponse();
    mock(sequence, validResponse);
  }

  @Test
  public void getMessagesInThread() throws IOException, InterruptedException {
    // When addresses are not passed.
    Sequence noAddressSequence = (api, server) -> {
      Conversation conversation = new Conversation(api);
      JSONObject params = ConversationStub.defaultParams();
      String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses", api.userId);

      try {
        Conversation response = conversation.getMessagesInThread(params);

        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertTrue(response.smsThreads.length > 0);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    // When only remoteAddress is passed.
    Sequence remoteAddressSequence = (api, server) -> {
      Conversation conversation = new Conversation(api);
      JSONObject params = ConversationStub.defaultParams();

      params.put("remoteAddress", "123123");

      String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses/%s", api.userId, params.get("remoteAddress"));

      try {
        Conversation response = conversation.getMessagesInThread(params);

        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertTrue(response.smsThreads.length > 0);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    // When only remoteAddress and localAddress are passed.
    Sequence remotelocalAddressSequence = (api, server) -> {
      Conversation conversation = new Conversation(api);
      JSONObject params = ConversationStub.defaultParams();

      params.put("remoteAddress", "123123");
      params.put("localAddress", "678678");

      String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses/%s/localAddresses/%s", api.userId, params.get("remoteAddress"), params.get("localAddress"));

      try {
        Conversation response = conversation.getMessagesInThread(params);

        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertNotNull(response.smsThread);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    JSONObject messages = ConversationStub.messagesInThread();
    JSONObject message = ConversationStub.messageInThread();

    mock(noAddressSequence, messages);
    mock(remoteAddressSequence, messages);
    mock(remotelocalAddressSequence, message);
  }

  @Test
  public void deleteMessage() throws IOException, InterruptedException {
    JSONObject params = ConversationStub.defaultParams();
    String localAddress = "123123";
    String remoteAddress = "678678";
    String messageId = "message-id";

    params.put("localAddress", localAddress);
    params.put("remoteAddress", remoteAddress);

    JSONObject paramsWithMessageId = new JSONObject(params.toString());

    paramsWithMessageId.put("messageId", messageId);

    // without messageId in params
    Sequence sequenceWithoutMessageId = (api, server) -> {
      Conversation conversation = new Conversation(api);
      try {
        String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses/%s/localAddresses/%s", api.userId, remoteAddress, localAddress);

        conversation.deleteMessage(params);
        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    // with messageId in params
    Sequence sequenceWithMessageId = (api, server) -> {
      Conversation conversation = new Conversation(api);
      try {
        String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses/%s/localAddresses/%s/messages/%s", api.userId, remoteAddress, localAddress, messageId);

        conversation.deleteMessage(paramsWithMessageId);
        RecordedRequest requestWithMessageId = server.takeRequest();

        assertEquals(url, requestWithMessageId.getPath());
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(sequenceWithoutMessageId, new JSONObject());
    mock(sequenceWithMessageId, new JSONObject());
  }

  @Test
  public void getMessages() throws IOException, InterruptedException {
    JSONObject paramsMessages = ConversationStub.defaultParams();
    String localAddress = "123123";
    String remoteAddress = "678678";
    String messageId = "test-message-id";

    paramsMessages.put("localAddress", localAddress);
    paramsMessages.put("remoteAddress", remoteAddress);

    JSONObject paramsMessage = new JSONObject(paramsMessages.toString());
    paramsMessage.put("messageId", messageId);

    // Get all message without messageId
    Sequence sequenceMessages = (api, server) -> {
      Conversation conversation = new Conversation(api);

      try {
        String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses/%s/localAddresses/%s/messages", api.userId, remoteAddress, localAddress);

        Conversation response = conversation.getMessages(paramsMessages);
        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertTrue(response.messages.length > 0);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    // Get message with messageId
    Sequence sequenceMessage = (api, server) -> {
      Conversation conversation = new Conversation(api);

      try {
        String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses/%s/localAddresses/%s/messages/%s", api.userId, remoteAddress, localAddress, messageId);

        Conversation response = conversation.getMessages(paramsMessage);
        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertNotNull(response.message);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };
    JSONObject messages = ConversationStub.messages();
    JSONObject message = ConversationStub.message();

    mock(sequenceMessages, messages);
    mock(sequenceMessage, message);
  }

  @Test
  public void getStatus() throws IOException, InterruptedException {
    Sequence sequence = (api, server) -> {
      Conversation conversation = new Conversation(api);
      JSONObject params = ConversationStub.defaultParams();
      String localAddress = "123123";
      String remoteAddress = "678678";
      String messageId = "test-message-id";

      params.put("localAddress", localAddress);
      params.put("remoteAddress", remoteAddress);
      params.put("messageId", messageId);


      try {
        String url = String.format("/cpaas/smsmessaging/v1/%s/remoteAddresses/%s/localAddresses/%s/messages/%s/status", api.userId, remoteAddress, localAddress, messageId);

        Conversation response = conversation.getStatus(params);
        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertNotNull(response.status);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(sequence, ConversationStub.messageStatus());
  }

  @Test
  public void getSubscriptions() throws IOException, InterruptedException {
    Sequence sequence = (api, server) -> {
      Conversation conversation = new Conversation(api);
      JSONObject params = ConversationStub.defaultParams();

      try {
        String url = String.format("/cpaas/smsmessaging/v1/%s/inbound/subscriptions", api.userId);

        Conversation response = conversation.getSubscriptions(params);
        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertTrue(response.subscriptions.length > 0);
        assertNotNull(response.subscriptions[0].id);
        assertNotNull(response.subscriptions[0].destinationAddress);
        assertNotNull(response.subscriptions[0].notifyURL);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(sequence, ConversationStub.subscriptions());
  }

  @Test
  public void getSubscription() throws IOException, InterruptedException {
    Sequence sequence = (api, server) -> {
      Conversation conversation = new Conversation(api);
      JSONObject params = ConversationStub.defaultParams();
      String subscriptionId = "test-subscription-id";

      params.put("subscriptionId", subscriptionId);

      try {
        String url = String.format("/cpaas/smsmessaging/v1/%s/inbound/subscriptions/%s", api.userId, subscriptionId);

        Conversation response = conversation.getSubscription(params);
        RecordedRequest request = server.takeRequest();

        assertEquals(url, request.getPath());
        assertNotNull(response.subscription.id);
        assertNotNull(response.subscription.destinationAddress);
        assertNotNull(response.subscription.notifyURL);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    };

    mock(sequence, ConversationStub.subscription());
  }
}
