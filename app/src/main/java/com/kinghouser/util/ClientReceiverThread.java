package com.kinghouser.util;

public class ClientReceiverThread extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                if (!NotificationListenerService.notificationRelay.serverSocket.isBound()) return;
                NotificationListenerService.notificationRelay.clientSocket = NotificationListenerService.notificationRelay.serverSocket.accept();
                System.out.println("Client connected...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
