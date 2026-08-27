package com.neonpulse.application.port;

/**
 * Puerto de notificación de la capa de aplicación.
 */
public interface MessageNotifier {
    void sendNotification(String recipient, String message);
}
