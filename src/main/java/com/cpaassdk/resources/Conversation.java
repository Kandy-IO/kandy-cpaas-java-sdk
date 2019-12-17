package com.cpaassdk.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cpaassdk.Api;
import com.cpaassdk.deserializers.*;

/**
 * CPaaS conversation.
 *
 */
public class Conversation extends ConversationDeserializer {
  private String baseUrl = null;
  private Api api = null;
  public static JSONObject types = null;

  public Conversation(Api api) {
    this.api = api;
    Conversation.types = new JSONObject()
      .put("SMS", "sms");

    this.baseUrl = String.format("/cpaas/smsmessaging/v1/%s", api.userId);
  }

  public Conversation() {}

  /**
   * Send a new outbound message
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> <i>Optional</i> Type of conversation. Possible values - SMS. Check conversation.types for more options
   * @param params.senderAddress <b>String</b> Sender address information, basically the from address. E164 formatted DID number passed as a value, which is owned by the user. If the user wants to let CPaaS uses the default assigned DID number, this field can either has "default" value or the same value as the userId.
   * @param params.destinationAddress <b>Array[String] | String</b>
   * @param params.message <b>String</b> SMS text message
   *
   * @return Conversation
   * @throws IOException Exception raised
   */

  public Conversation createMessage(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();

    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      JSONArray destinationAddress = null;
      if (params.get("destinationAddress") instanceof JSONArray) {
        destinationAddress = (JSONArray) params.get("destinationAddress");
      } else {
        ArrayList<String> list = new ArrayList<String>();
        list.add((String) params.get("destinationAddress"));
        destinationAddress = new JSONArray(list);
      }

      JSONObject body =
        new JSONObject()
          .put("outboundSMSMessageRequest", new JSONObject()
            .put("address", destinationAddress)
            .put("clientCorrelator", this.api.config.clientCorrelator)
            .put("outboundSMSTextMessage", new JSONObject()
              .put("message", extractString(params, "message"))
              )
            );

      options.put("body", body);

      String url = String.format("%s/outbound/%s/requests", this.baseUrl, extractString(params, "senderAddress"));

      JSONObject response = this.api.sendRequest(url, "post", options);

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }

    return null;
  }

  /**
   * Read all messages in a thread
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> Type of conversation. Possible values - SMS. Check conversation.types for more options
   * @param params.remoteAddress <b>String</b> <i>Optional</i> Remote address information while retrieving the SMS history, basically the destination telephone number that user exchanged SMS before. E164 formatted DID number passed as a value.
   * @param params.localAddress <b>String</b> <i>Optional</i> Local address information while retrieving the SMS history, basically the source telephone number that user exchanged SMS before.
   * @param params.query <b>JSONObject</b> <i>Optional</i>
   * @param params.query.name <b>String</b> <i>Optional</i> Performs search operation on firstName and lastName fields.
   * @param params.query.firstName <b>String</b> <i>Optional</i> Performs search for the firstName field of the directory items.
   * @param params.query.lastName <b>String</b> <i>Optional</i> Performs search for the lastName field of the directory items.
   * @param params.query.userName <b>String</b> <i>Optional</i> Performs search for the userName field of the directory items.
   * @param params.query.phoneNumber <b>String</b> <i>Optional</i> Performs search for the fields containing a phone number, like businessPhoneNumber, homePhoneNumber, mobile, pager, fax.
   * @param params.query.order <b>String</b> <i>Optional</i> Ordering the contact results based on the requested sortBy value, order query parameter should be accompanied by sortBy query parameter.
   * @param params.query.sortBy <b>String</b> <i>Optional</i> SortBy value is used to detect sorting the contact results based on which attribute. If order is not provided with that, ascending order is used.
   * @param params.query.max <b>Integer</b> <i>Optional</i> Maximum number of contact results that has been requested from CPaaS for this query.
   * @param params.query.next <b>String</b> <i>Optional</i> Pointer for the next chunk of contacts, should be gathered from the previous query results.
   *
   * @return Conversation
   * @throws IOException Exception raised
   */

  public Conversation getMessagesInThread(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();

    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      String url = String.format("%s/remoteAddresses", this.baseUrl);
      if (params.has("remoteAddress")) {
        url += String.format("/%s", extractString(params, "remoteAddress"));
      }
      if (params.has("localAddress")) {
        url += String.format("/localAddresses/%s", extractString(params, "localAddress"));
      }

      if (params.has("query")) {
        options.put("query", params.get("query"));
      }

      JSONObject response = this.api.sendRequest(url, "get", options);

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }

    return null;
  }


  /**
   * Delete conversation message
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> Type of conversation. Possible values - SMS. Check conversation.types for more options
   * @param params.localAddress <b>String</b> Local address information while retrieving the SMS history, basically the source telephone number that user exchanged SMS before.
   * @param params.remoteAddress <b>String</b> Remote address information while retrieving the SMS history, basically the destination telephone number that user exchanged SMS before. E164 formatted DID number passed as a value.
   * @param params.messageId <b>String</b> <i>Optional</i> Identification of the SMS message. If messageId is not passed then the SMS thread is deleted with all messages.
   *
   * @return Conversation
   * @throws IOException Exception raised
   */
  public Conversation deleteMessage(JSONObject params) throws IOException {
    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      String url = String.format("%s/remoteAddresses/%s/localAddresses/%s", this.baseUrl, extractString(params, "remoteAddress"), extractString(params, "localAddress"));

      if (params.has("messageId")) {
        url += String.format("/messages/%s", extractString(params, "messageId"));
      }

      JSONObject response = this.api.sendRequest(url, "delete");

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }

    return null;
  }

  /**
   * Gets all messages.
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> Type of conversation. Possible values - SMS. Check conversation.types for more options
   * @param params.localAddress <b>String</b> Local address information while retrieving the SMS history, basically the source telephone number that user exchanged SMS before.
   * @param params.remoteAddress <b>String</b> Remote address information while retrieving the SMS history, basically the destination telephone number that user exchanged SMS before. E164 formatted DID number passed as a value.
   * @param params.query <b>JSONObject</b> <i>Optional</i>
   * @param params.query.max <b>Integer</b> <i>Optional</i> Number of messages that is requested from CPaaS.
   * @param params.query.next <b>String</b> <i>Optional</i> Pointer for the next page to retrieve for the messages, provided by CPaaS in previous GET response.
   * @param params.query.new <b>String</b> <i>Optional</i> Filters the messages or threads having messages that are not received by the user yet
   * @param params.query.lastMessageTime <b>Integer</b> <i>Optional</i> Filters the messages or threads having messages that are sent/received after provided Epoch time
   *
   * @return Conversation
   * @throws IOException Exception raised
   */
  public Conversation getMessages(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();

    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      String url = String.format("%s/remoteAddresses/%s/localAddresses/%s/messages", this.baseUrl, extractString(params, "remoteAddress"), extractString(params, "localAddress"));

      if (params.has("query") && !params.has("messageId")) {
        options.put("query", params.get("query"));
      }

      if (params.has("messageId")) {
        url += String.format("/%s", params.get("messageId"));
      }

      JSONObject response = this.api.sendRequest(url);

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }


    return null;
  }

  /**
   * Read a conversation message status
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> Type of conversation. Possible values - SMS. Check conversation.types for more options
   * @param params.localAddress <b>String</b> Local address information while retrieving the SMS history, basically the source telephone number that user exchanged SMS before.
   * @param params.remoteAddress <b>String</b> Remote address information while retrieving the SMS history, basically the destination telephone number that user exchanged SMS before. E164 formatted DID number passed as a value.
   * @param params.messageId <b>String</b> Identification of the SMS message.
   *
   * @return Conversation
   * @throws IOException Exception raised
   */
  public Conversation getStatus(JSONObject params) throws IOException {
    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      String url = String.format("%s/remoteAddresses/%s/localAddresses/%s/messages/%s/status", this.baseUrl, extractString(params, "remoteAddress"), extractString(params, "localAddress"), extractString(params, "messageId"));

      JSONObject response = this.api.sendRequest(url);

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }


    return null;
  }

  /**
   * Read all active subscriptions
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> Type of conversation. Possible values - SMS. Check conversation.types for more options
   *
   * @return Conversation
   * @throws IOException Exception raised
   */
  public Conversation getSubscriptions(JSONObject params) throws IOException {
    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      String url = String.format("%s/inbound/subscriptions", this.baseUrl);

      JSONObject response = this.api.sendRequest(url);

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }


    return null;
  }

  /**
   * Read active subscription
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> Type of conversation. Possible values - SMS. Check conversation.types for more options
   * @param params.subscriptionId <b>String</b> Resource ID of the subscription
   *
   * @return Conversation
   * @throws IOException Exception raised
   */
  public Conversation getSubscription(JSONObject params) throws IOException {
    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      String url = String.format("%s/inbound/subscriptions/%s", this.baseUrl, extractString(params, "subscriptionId"));

      JSONObject response = this.api.sendRequest(url);

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }


    return null;
  }

  /**
   * Create a new subscription
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.type <b>String</b> Type of conversation. Possible values - SMS. Check conversation.types for more options
   * @param params.webhookURL <b>String</b> The webhook that has been acquired during SMS API subscription, which the incoming notifications supposed to be sent to.
   * @param params.destinationAddress <b>String</b> <i>optional</i> The address that incoming messages are received for this subscription. If does not exist, CPaaS uses the default assigned DID number to subscribe against. It is suggested to provide the intended E164 formatted DID number within this parameter.
   *
   * @return Conversation
   * @throws IOException Exception raised
   */

  public Conversation subscribe(JSONObject params) throws IOException {
    JSONObject options = new JSONObject();

    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      NotificationChannel channel = new NotificationChannel(this.api).createChannel(params);

      if (!channel.hasError()) {
        JSONObject body = new JSONObject()
          .put("subscription", new JSONObject()
            .put("callbackReference", new JSONObject()
              .put("notifyURL", channel.channelId)
            )
            .put("clientCorrelator", this.api.config.clientCorrelator)
            .put("destinationAddress", extractString(params, "destinationAddress"))
          );

        String url = String.format("%s/inbound/subscriptions", this.baseUrl);

        options.put("body", body);

        JSONObject response = this.api.sendRequest(url, "post", options);

        return new ObjectMapper().readValue(response.toString(), Conversation.class);
      }
    }

    return null;
  }

  /**
   * Unsubscription from conversation notification
   *
   * @param params <b>JSONObject</b> to hold all params.
   * @param params.subscriptionId <b>String</b> Resource ID of the subscription
   *
   * @return Conversation
   * @throws IOException Exception raised
   */
  public Conversation unsubscribe(JSONObject params) throws IOException {
    if (params.get("type").equals(Conversation.types.get("SMS"))) {
      String url = String.format("%s/inbound/subscriptions/%s", this.baseUrl, extractString(params, "subscriptionId"));

      JSONObject response = this.api.sendRequest(url, "delete");

      return new ObjectMapper().readValue(response.toString(), Conversation.class);
    }

    return null;
  }
}
