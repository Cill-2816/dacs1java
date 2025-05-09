package com.gpcoder.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import com.gpcoder.Utils.HibernateUtils;
import com.gpcoder.model.Historychat;

public class Server {
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static int clientId = 1;
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server đang chạy...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            String clientName = "Client-" + clientId++;

            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientHandler);
            new Thread(clientHandler).start();

            System.out.println(clientName + " đã kết nối.");
        }
    }

    public static void broadcast(Historychat line, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) { // Không gửi lại cho người gửi
                    client.sendMessage(line);
                }
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket) {
        this.client = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo handler");
        }
    }

    public void sendMessage(Historychat message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
public void run() {
    try {
        // Gửi danh sách historychat cho client ngay sau khi kết nối
        List<Historychat> historyList = getHistory();
        System.out.println("Gửi danh sách " + historyList.size() + " tin nhắn cho client");

        out.writeObject(historyList);
        out.flush();

        System.out.println("Đã gửi xong history.");

        // Bắt đầu nghe các tin nhắn mới
        Historychat line;
        while ((line = (Historychat) in.readObject()) != null) {
            Server.broadcast(line, this);
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Đã ngắt kết nối.");
    } finally {
        try {
            client.close();
        } catch (IOException e) {}
    }
}


    public List<Historychat> getHistory() {
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        return session.createQuery("from Historychat", Historychat.class).list();
    }
    // List<Historychat> list = new ArrayList<>();
    //     Historychat test = new Historychat();
    //     test.setSent_id("Test");
    //     test.setMessage("Hello from server!");
    //     list.add(test);
    //     return list;
    }
}
