package com.cpaassdk.resources;

import com.cpaassdk.stubs.NotificationStub;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class NotificationTest {
  @Test
  public void parsesInboundSms() throws IOException {
    Notification notification = new Notification();

    Notification response = notification.parse(NotificationStub.smsInbound());

    assertNotNull(response);
    assertNotNull(response.type);
    assertNotNull(response.message);
    assertNotNull(response.notificationId);
    assertNotNull(response.notificationDateTime);
    assertNotNull(response.messageId);
    assertNotNull(response.senderAddress);
    assertNotNull(response.destinationAddress);
  }

  @Test
  public void parsesOutboundSms() throws IOException {
    Notification notification = new Notification();

    Notification response = notification.parse(NotificationStub.smsOutbound());

    assertNotNull(response);
    assertNotNull(response.type);
    assertNotNull(response.message);
    assertNotNull(response.notificationId);
    assertNotNull(response.notificationDateTime);
    assertNotNull(response.messageId);
    assertNotNull(response.senderAddress);
    assertNotNull(response.destinationAddress);
  }
  @Test
  public void parsesSmsSubscriptionCancellation() throws IOException {
    Notification notification = new Notification();

    Notification response = notification.parse(NotificationStub.smsSubscriptionCancellation());

    assertNotNull(response);
    assertNotNull(response.type);
    assertNotNull(response.notificationId);
    assertNotNull(response.notificationDateTime);
    assertNotNull(response.subscriptionId);
  }
  @Test
  public void parsesSmsEvent() throws IOException {
    Notification notification = new Notification();

    Notification response = notification.parse(NotificationStub.smsEvent());

    assertNotNull(response);
    assertNotNull(response.type);
    assertNotNull(response.notificationId);
    assertNotNull(response.notificationDateTime);
    assertNotNull(response.subscriptionId);
  }
}