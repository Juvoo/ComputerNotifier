package com.kinghouser.util;

import android.widget.Toast;
import com.kinghouser.MainActivity;
import com.kinghouser.ui.home.HomeViewModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {

    public static ServerSocket serverSocket = null;
    public static Socket clientSocket = null;
    public static final ArrayList<ClientThread> clientThreads = new ArrayList<>();

    public void run() {
        try {
            serverSocket = new ServerSocket(0);
            HomeViewModel.mText.postValue("Connect with ip address: " + Utils.getMobileIP() + " and port: " + serverSocket.getLocalPort());
            System.out.println("Connect with ip address: " + Utils.getMobileIP() + " and port: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                clientThreads.add(new ClientThread(clientSocket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void relayNotification(android.app.Notification notification) {
        com.kinghouser.util.Notification customNotification = new com.kinghouser.util.Notification("", notification.tickerText.toString());

        for (ClientThread clientThread : clientThreads) {
            clientThread.sendNotification(customNotification);
        }
    }
}

class ClientThread extends Thread {

    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    private final Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.start();
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (clientSocket.isConnected()) {
            }
            /*
            objectInputStream.close();
            ps.close();
            clientSocket.close(
            );
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(Notification notification) {
        try {
            objectOutputStream.writeObject(notification);
            objectOutputStream.flush();

            Toast.makeText(MainActivity.applicationContext, "Sent notification to client", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}