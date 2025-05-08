package com.gpcoder.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server đang chạy...");

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Client kết nối: " + client.getInetAddress());

            // Tạo luồng mới để xử lý client
            new Thread(new ClientHandler(client)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket client;

    public ClientHandler(Socket socket) {
        this.client = socket;
    }

    public void run() {
        try (
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
