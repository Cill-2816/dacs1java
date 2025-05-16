package com.gpcoder.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.gpcoder.Utils.XMLUtil;
import com.gpcoder.model.Customer;
import com.gpcoder.model.DetailInvoice;
import com.gpcoder.model.Historychat;
import com.gpcoder.model.Invoice;
import com.gpcoder.model.MenuItem;
import com.gpcoder.model.Staff;
import com.gpcoder.model_xml.CustomerList;
import com.gpcoder.model_xml.DetailInvoiceList;
import com.gpcoder.model_xml.HistorychatList;
import com.gpcoder.model_xml.InvoiceList;
import com.gpcoder.model_xml.MenuItemList;
import com.gpcoder.model_xml.StaffList;

public class Server {
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static int clientId = 1;
    
    public static void main(String[] args) throws IOException, JAXBException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server đang chạy...");

            getAllDB();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("🔁 Server đang tắt... Lưu dữ liệu vào database.");
                try {
                    saveAllDB();
                    System.out.println("✅ Dữ liệu đã được lưu thành công.");
                } catch (JAXBException e) {
                    System.err.println("❌ Lỗi khi lưu dữ liệu: " + e.getMessage());
                    e.printStackTrace();
                }
            }));

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

    public static void broadcastFile(Historychat line, byte[] sendfile, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) { // Không gửi lại cho người gửi
                    client.sendMessage(line);
                    client.sendFile(sendfile);
                }
            }
        }
    }

    public static void broadcastFileBlock(
        Historychat meta,
        long fileSize,
        ClientHandler sender,
        ObjectInputStream senderIn) {

        try {
            // đọc toàn bộ file một lần
            byte[] allBytes = new byte[(int) fileSize];
            senderIn.readFully(allBytes);     // dùng DataInputStream wrapper nếu muốn readFully()

            synchronized (clients) {
                for (ClientHandler c : clients) {
                    if (c == sender) continue;

                    c.sendMessage(meta);          // 1. meta
                    c.sendLong(fileSize);         // 2. size
                    c.sendRawBytes(allBytes, 0, allBytes.length); // 3. data
                }
            }
            System.out.printf("Đã chuyển tiếp file %s tới %d client%n",
                            meta.getMessage(), clients.size()-1);

        } catch (IOException ex) {
            System.err.println("Lỗi chuyển tiếp file: " + ex);
        }
    }


    public static void getAllDB() throws JAXBException {
        // Lấy dữ liệu từ DB bằng Hibernate
        List<Staff> staffs = HibernateDAO.getAll(Staff.class);
        List<MenuItem> menuItems = HibernateDAO.getAll(MenuItem.class);
        List<Invoice> invoices = HibernateDAO.getAll(Invoice.class);
        List<Historychat> historychats = HibernateDAO.getAll(Historychat.class);
        List<Customer> customers = HibernateDAO.getAll(Customer.class);
        List<DetailInvoice> detailInvoices = HibernateDAO.getAll(DetailInvoice.class);

        // Bọc các danh sách vào wrapper có @XmlRootElement
        StaffList staffList = new StaffList();
        staffList.setStaff(staffs);

        MenuItemList menuItemList = new MenuItemList();
        menuItemList.setMenuItem(menuItems);

        InvoiceList invoiceList = new InvoiceList();
        invoiceList.setInvoices(invoices);

        HistorychatList historychatList = new HistorychatList();
        historychatList.setHistorychat(historychats);

        CustomerList customerList = new CustomerList();
        customerList.setDetailInvoice(customers);

        DetailInvoiceList detailInvoiceList = new DetailInvoiceList();
        detailInvoiceList.setDetailInvoice(detailInvoices);

        // Ghi ra file XML
        XMLUtil.saveToXml(new File("data/staff.xml"), staffList);
        XMLUtil.saveToXml(new File("data/menuitem.xml"), menuItemList);
        XMLUtil.saveToXml(new File("data/invoice.xml"), invoiceList);
        XMLUtil.saveToXml(new File("data/historychat.xml"), historychatList);
        XMLUtil.saveToXml(new File("data/customer.xml"), customerList);
        XMLUtil.saveToXml(new File("data/detailInvoice.xml"), detailInvoiceList);
    }


    public static void saveAllDB() throws JAXBException {
        
        StaffList staffs = XMLUtil.loadFromXml(new File("data/staff.xml"), StaffList.class);
        MenuItemList mennuitems = XMLUtil.loadFromXml(new File("data/menuitem.xml"), MenuItemList.class);
        InvoiceList invoices = XMLUtil.loadFromXml(new File("data/invoice.xml"), InvoiceList.class);
        HistorychatList historychats = XMLUtil.loadFromXml(new File("data/historychat.xml"), HistorychatList.class);
        CustomerList customers = XMLUtil.loadFromXml(new File("data/customer.xml"), CustomerList.class);
        DetailInvoiceList detailInvoices = XMLUtil.loadFromXml(new File("data/detailInvoice.xml"), DetailInvoiceList.class);

        HibernateDAO.saveAll(staffs.getStaff());
        HibernateDAO.saveAll(mennuitems.getMenuItem());
        HibernateDAO.saveAll(invoices.getInvoices());
        HibernateDAO.saveAll(historychats.getHistorychat());
        HibernateDAO.saveAll(customers.getDetailInvoice());
        HibernateDAO.saveAll(detailInvoices.getDetailInvoice());

        // Khởi tạo List rỗng
        StaffList staffList = new StaffList();
        MenuItemList menuItemList = new MenuItemList();
        InvoiceList invoiceList = new InvoiceList();
        HistorychatList historychatList = new HistorychatList();
        CustomerList customerList = new CustomerList();
        DetailInvoiceList detailInvoiceList = new DetailInvoiceList();

        // Ghi ra file XML để clear XML
        XMLUtil.saveToXml(new File("data/staff.xml"), staffList);
        XMLUtil.saveToXml(new File("data/menuitem.xml"), menuItemList);
        XMLUtil.saveToXml(new File("data/invoice.xml"), invoiceList);
        XMLUtil.saveToXml(new File("data/historychat.xml"), historychatList);
        XMLUtil.saveToXml(new File("data/customer.xml"), customerList);
        XMLUtil.saveToXml(new File("data/detailInvoice.xml"), detailInvoiceList);
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

    public void sendRawBytes(byte[] data, int offset, int length) throws IOException {
        out.write(data, offset, length);
        out.flush();
    }

    public void sendLong(long value) throws IOException {
        out.writeLong(value);
        out.flush();
    }

    public static void appendHistorychatToXml(Historychat chat) throws JAXBException {
        File xmlFile = new File("data/historychat.xml");
        xmlFile.getParentFile().mkdirs();
        HistorychatList historyList;
        if (!xmlFile.exists()) {
            historyList = new HistorychatList();
        } else {
            historyList = XMLUtil.loadFromXml(xmlFile, HistorychatList.class);
        }

        historyList.getHistorychat().add(chat);
        XMLUtil.saveToXml(xmlFile, historyList);
    }

    public void sendFile(byte[] message) {
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
        while (true) {
            obj = in.readObject();
            if (obj instanceof String) {
                String command = (String) obj;
                
                if (command.startsWith("GET_HISTORY:")) {
                    String[] username = command.split(":",3);
                    List<Historychat> historyList = getHistory(username[1],username[2]);
                    out.writeObject(historyList);
                    out.flush();
                    System.out.println("Gửi lại danh sách history cho client: " + username);
                    // for (Historychat chat : historyList) {
                    //     System.out.println(chat);
                    // }
                } else if (command.startsWith("NEW_MESSAGE:")) {
                    String[] username = command.split(":",4);
                    try {
                        // 1. Tạo tin nhắn mới
                        Historychat chat = new Historychat(
                            username[3], "text", username[2], username[1], LocalDateTime.now()
                        );

                        // 2. Lưu vào xml
                        appendHistorychatToXml(chat);   

                        // 3. Gửi message đến các client khác
                        Server.broadcast(chat, this);

                    } catch (Exception e) {
                        System.out.println("❌ Lỗi khi xử lý message 'text': " + e.getMessage());
                        e.printStackTrace();
                    }

                } else if (command.startsWith("NEW_FILE:")) {
                    String[] username = command.split(":",4);
                    try {
                        // 1. Tạo message mới
                        Historychat chat = new Historychat(
                            username[3], "file", username[2], username[1], LocalDateTime.now()
                        );

                        // 2. Lưu vào xml
                        appendHistorychatToXml(chat);   

                        // 3. Gửi đến các client khác
                        long fileSize = in.readLong();
                        Server.broadcastFileBlock(chat, fileSize, this, in);
                        out.flush();

                    } catch (Exception e) {
                        System.out.println("❌ Lỗi khi ghi history vào file XML: " + e.getMessage());
                        e.printStackTrace();
                    }

                } else if (command.startsWith("NEW_IMAGE:")){
                    String[] username = command.split(":",4);
                    try {
                        // 1. Tạo message
                        Historychat chat = new Historychat(username[3], "image", username[2], username[1], LocalDateTime.now());

                        // 2. Lưu vào xml
                        appendHistorychatToXml(chat);   

                        // 3. Gửi đến các client khác
                        long fileSize = in.readLong();
                        Server.broadcastFileBlock(chat, fileSize, this, in);
                        out.flush();

                    } catch (Exception e) {
                        System.out.println("❌ Lỗi khi ghi lịch sử vào file XML: " + e.getMessage());
                        e.printStackTrace();
                    }

                } else if (command.startsWith("GET_MENU:")) {
                    String[] check = command.split(":",4);
                    List<MenuItem> menuList = getmenu(Boolean.parseBoolean(check[1]), Boolean.parseBoolean(check[2]), Boolean.parseBoolean(check[3]));
                    out.writeObject(menuList);
                    out.flush();
                    for(MenuItem a : menuList) {
                        System.out.println(a);
                    }
                    System.out.println("Gui lai danh sach menu cho client: "+ Boolean.parseBoolean(check[1])+ Boolean.parseBoolean(check[2])+ Boolean.parseBoolean(check[3]));
                    // for (MenuItem chat : menuList) {
                    //     System.out.println(chat);
                    // }
                } else if (command.startsWith("GET_STAFF")) {
                    List<Staff> staffs = getStaff();
                    out.writeObject(staffs);
                    out.flush();
                    for(Staff a : staffs) {
                        System.out.println(a);
                    }
                    System.out.println("Gui lai danh sach staff cho client!");
                    // for (MenuItem chat : menuList) {
                    //     System.out.println(chat);
                    // }
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
        try {
            // 1. Load toàn bộ history từ file XML
            File xmlFile = new File("data/historychat.xml");
            HistorychatList historyListWrapper = XMLUtil.loadFromXml(xmlFile, HistorychatList.class);
            List<Historychat> allChats = historyListWrapper.getHistorychat();

            // 2. Lọc theo 2 username
            List<Historychat> filtered = new ArrayList<>();
            for (Historychat chat : allChats) {
                boolean match1 = chat.getSent_id().equals(username) && chat.getRecieve_id().equals(username2);
                boolean match2 = chat.getSent_id().equals(username2) && chat.getRecieve_id().equals(username);
                if (match1 || match2) {
                    filtered.add(chat);
                }
            }

            // 3. Sắp xếp theo ID tăng dần (nếu cần)
            filtered.sort(Comparator.comparing(Historychat::getSent_time));
            return filtered;

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi đọc lịch sử từ file XML: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<MenuItem> getmenu(Boolean breakfast_check, Boolean lunch_check, Boolean dinner_check) {
        try {
            // 1. Load danh sách từ file XML
            File xmlFile = new File("data/menuitem.xml");
            MenuItemList menuListWrapper = XMLUtil.loadFromXml(xmlFile, MenuItemList.class);
            List<MenuItem> allItems = menuListWrapper.getMenuItem();

            // 2. Lọc theo điều kiện
            List<MenuItem> filtered = new ArrayList<>();

            for (MenuItem item : allItems) {
                boolean include = false;

                if (breakfast_check && Boolean.TRUE.equals(item.isBreakfast())) {
                    include = true;
                }
                if (lunch_check && Boolean.TRUE.equals(item.isLunch())) {
                    include = true;
                }
                if (dinner_check && Boolean.TRUE.equals(item.isDinner())) {
                    include = true;
                }

                if (include) {
                    filtered.add(item);
                }
            }

            // 3. Sắp xếp nếu muốn
            filtered.sort(Comparator.comparingInt(MenuItem::getId)); // nếu có getId()

            return filtered;

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi đọc menu từ file XML: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Staff> getStaff() {
        try {
            File xmlFile = new File("data/staff.xml");
            StaffList staffListWrapper = XMLUtil.loadFromXml(xmlFile, StaffList.class);
            List<Staff> allStaffs = staffListWrapper.getStaff();
            return allStaffs;
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi đọc lịch sử từ file XML: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    
}
