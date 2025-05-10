package com.gpcoder.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
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
        Object obj;
        while ((obj = in.readObject()) != null) {
            if (obj instanceof String) {
                String command = (String) obj;
                
                if (command.startsWith("GET_HISTORY:")) {
                    String[] username = command.split(":",3);
                    List<Historychat> historyList = getHistory(username[1],username[2]);
                    out.writeObject(historyList);
                    out.flush();
                    System.out.println("Gửi lại danh sách history cho client: " + username);
                    for (Historychat chat : historyList) {
                        System.out.println(chat);
                    }
                } else if (command.startsWith("NEW_MESSAGE:")) {
                    String[] username = command.split(":",4);
                    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                        session.beginTransaction();
                        Historychat chat = new Historychat(username[3],"text",username[2],username[1],LocalDateTime.now());
                        session.save(chat);
                        session.getTransaction().commit();
                        Server.broadcast(chat, this);
                    } catch (Exception e) {
                        System.out.println("Loi khi truy van history: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else if (command.startsWith("NEW_FILE:")) {
                    String[] username = command.split(":",4);
                    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                        session.beginTransaction();
                        Historychat chat = new Historychat(username[3],"file",username[2],username[1],LocalDateTime.now());
                        session.save(chat);
                        session.getTransaction().commit();
                        Server.broadcast(chat, this);
                    } catch (Exception e) {
                        System.out.println("Loi khi truy van history: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Lệnh không hợp lệ từ client: " + command);
                }
            } else {
                System.out.println("Loại dữ liệu không xác định: " + obj.getClass().getName());
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Client đã ngắt kết nối hoặc lỗi: " + e.getMessage());
    } finally {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



    public List<Historychat> getHistory(String username, String username2) {
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        List<Historychat> result = session.createQuery(
            "from Historychat where (sent_id = :sender and recieve_id = :reciever) or (recieve_id = :sender and sent_id = :reciever) order by id asc", Historychat.class)
            .setParameter("sender", username)
            .setParameter("reciever", username2)
            .list();
        return result != null ? result : new ArrayList<>();
    } catch (Exception e) {
        System.out.println("Lỗi khi truy vấn history: " + e.getMessage());
        e.printStackTrace();
        return new ArrayList<>();
    }
}

}
