package com.neonpulse.infrastructure.notification;

import com.neonpulse.application.port.MessageNotifier;

/**
 * Adaptador dummy de notificación para pruebas o registro pasivo.
 */
public class DummyNotifier implements MessageNotifier {

    private String lastRecipient;
    private String lastMessage;

    @Override
    public void sendNotification(String recipient, String message) {
        this.lastRecipient = recipient;
        this.lastMessage = message;
    }

    public String getLastRecipient() {
        return lastRecipient;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
