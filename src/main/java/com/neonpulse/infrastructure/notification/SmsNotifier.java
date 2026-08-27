package com.neonpulse.infrastructure.notification;

import com.neonpulse.application.port.MessageNotifier;

/**
 * Adaptador de infraestructura para notificaciones por SMS.
 */
public class SmsNotifier implements MessageNotifier {

    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("[SMS -> " + recipient + "]: " + message);
    }
}
