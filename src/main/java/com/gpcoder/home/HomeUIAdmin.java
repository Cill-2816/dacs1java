package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.gpcoder.accounting.AccountingPanel;
import com.gpcoder.model.MenuItem;
import com.gpcoder.model.Staff;
import com.gpcoder.staffpanel.StaffPanel;

public class HomeUIAdmin extends JFrame {

    /* ---------- network ---------- */
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream  inStream;

    /* ---------- UI comps ---------- */
    private StaffPanel  staffPanel;
    private JPanel      accountingPanel;
    private JPanel      contentPanel;
    private JScrollPane scrollPane;
    private JPanel      contentSwitcher;
    private CardLayout  mainCardLayout;

    /* ---------- data ---------- */
    private List<MenuItem> menuitem;   // bản sao gốc để hiển thị
    private int x;                     // filter idx 0-3 (All/B/L/D)

    public HomeUIAdmin() {
        setTitle("Mr. Chefs – Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440,900);
        setLocationRelativeTo(null);
        buildUI();
        setVisible(true);
    }

    /* ==================================================== */
    /*                  1. BUILD UI                         */
    /* ==================================================== */
    private void buildUI() {

        /* ====== màu giống HomeUI ====== */
        Color defaultFilterColor = new Color(44, 47, 51);      // đậm
        Color hoverFilterColor   = new Color(110, 114, 120);   // nhạt hơn khi rê chuột
        Color pressedFilterColor = hoverFilterColor;           // giữ nguyên khi giữ
        Color selectedColor      = new Color(255, 87, 34);     // cam khi click/chọn

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(24,26,27));

        /* -------- SIDEBAR -------- */
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(30,32,34));
        sidebar.setPreferredSize(new Dimension(220,getHeight()));

        JLabel logo = new JLabel(new ImageIcon(
                new ImageIcon("image/logo.png").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        sidebar.add(logo);

        String[] sideItems = {"Menu","Staff (HR)","Accounting"};
        String[] sideIcons = {"image/menu.png","image/staffmanagement.png","image/accounting.png"};
        List<JButton> sideButtons = new ArrayList<>();
        final JButton[] sideSelected = {null};

        for(int i=0;i<sideItems.length;i++){
            JButton btn = new JButton(sideItems[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
            btn.setBackground(i==0?selectedColor:new Color(30,32,34));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial",Font.BOLD,18));
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setIconTextGap(15);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setIcon(new ImageIcon(
                    new ImageIcon(sideIcons[i]).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));

            /* hover như HomeUI */
            btn.addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){
                    if(btn!=sideSelected[0]) btn.setBackground(hoverFilterColor);
                }
                public void mouseExited(MouseEvent e){
                    if(btn!=sideSelected[0]) btn.setBackground(new Color(30,32,34));
                }
            });

            final int idx=i;
            btn.addActionListener(e -> {
                if (sideSelected[0] != null)
                    sideSelected[0].setBackground(new Color(30,32,34));
                btn.setBackground(selectedColor);
                sideSelected[0] = btn;

                /* ★ lazy-load panel nếu cần */
                switch (sideItems[idx]) {
                    case "Staff (HR)":
                        if (staffPanel == null)                  // chưa có -> tạo trống
                            initStaffPanel(new ArrayList<Staff>());
                        break;
                    case "Accounting":
                        if (accountingPanel == null)            // cần dữ liệu menu
                            initAccountingPanel(menuitem != null ? menuitem
                                                                : new ArrayList<MenuItem>());
                        break;
                    default: /* Menu đã có sẵn */
                }

                mainCardLayout.show(contentSwitcher, sideItems[idx]);
            });

            sideButtons.add(btn);
            sidebar.add(btn);
        }
        sideSelected[0]=sideButtons.get(0);  // mặc định: Menu

        /* ------- profile/logout/chat ở dưới ------- */
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(buildBottomBtn("Internal Chat","image/chat.png"));
        sidebar.add(buildBottomBtn("Profile","image/user_icon.png"));
        sidebar.add(buildBottomBtn("Logout","image/logout.png"));

        /* -------- TOP BAR -------- */
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(36,40,45));
        topBar.setPreferredSize(new Dimension(getWidth(),60));
        JLabel userLbl = new JLabel("Admin");
        userLbl.setForeground(Color.WHITE);
        userLbl.setFont(new Font("Arial",Font.PLAIN,14));
        userLbl.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        topBar.add(userLbl,BorderLayout.EAST);

        /* -------- SEARCH -------- */
        PlaceholderTextField searchField = new PlaceholderTextField("Search a food…");
        searchField.setPreferredSize(new Dimension(400,40));
        searchField.setFont(new Font("Arial",Font.ITALIC,14));
        searchField.setBackground(defaultFilterColor);
        searchField.setForeground(Color.GRAY);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        searchField.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                if("Search a food…".equals(searchField.getText())){
                    searchField.setText(""); searchField.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent e){
                if(searchField.getText().trim().isEmpty()){
                    searchField.setText("Search a food…"); searchField.setForeground(Color.GRAY);
                }
            }
        });
        searchField.addActionListener(e -> showMenu(searchByName(searchField.getText(),x)));

        RoundedButton addBtn = new RoundedButton("",20);
        addBtn.setPreferredSize(new Dimension(50,40));
        addBtn.setBackground(defaultFilterColor);
        addBtn.setIcon(new ImageIcon(
                new ImageIcon("image/add.png").getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH)));
        addBtn.addMouseListener(new HoverBtn(addBtn,defaultFilterColor,hoverFilterColor,pressedFilterColor));

        JPanel searchPanel = new JPanel(new BorderLayout(10,0));
        searchPanel.setBackground(new Color(24,26,27));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(addBtn,      BorderLayout.EAST);

        /* -------- FILTER BUTTONS -------- */
        String[] filters = {"All","Breakfast","Lunch","Dinner"};
        String[] icons   = {"dish.png","breakfast.png","lunch.png","dinner.png"};

            Color selectedColor1 = new Color(255, 87, 34);
            Color defaultColor = new Color(60,63,65);
            Color pressedColor   = new Color(84, 88, 95);   // xám đậm khi giữ
            Color hoverColor = new Color(110,114,120);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,8));
        filterPanel.setBackground(new Color(24,26,27));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10,20,0,20));

        List<RoundedButton> buttons = new ArrayList<>();
        final RoundedButton[] selectedFilterBtn = {null};

        for (int i = 0; i < filters.length; i++) {
        ImageIcon ic = new ImageIcon("image/" + icons[i]);
        ic = new ImageIcon(ic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        RoundedButton fBtn = new RoundedButton(filters[i], 30);
        fBtn.setPreferredSize(new Dimension(170, 60));
        fBtn.setHorizontalAlignment(SwingConstants.LEFT);
        fBtn.setIcon(ic);
        fBtn.setIconTextGap(10);
        fBtn.setBackground(defaultColor);        // ← luôn dùng màu xám cho tất cả
        fBtn.setForeground(Color.WHITE);
        fBtn.setFont(new Font("Arial", Font.BOLD, 16));
        fBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            /* hover + pressed giống HomeUI */
            fBtn.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (fBtn != selectedFilterBtn[0]) {
                            fBtn.setBackground(hoverColor);
                        }
                    }
                    public void mousePressed(MouseEvent e) {               // ★ thêm
                        if (fBtn != selectedFilterBtn[0]) {
                            fBtn.setBackground(pressedColor);
                        }
    }
                    public void mouseExited(MouseEvent e) {
                        if (fBtn != selectedFilterBtn[0]) {
                            fBtn.setBackground(defaultColor);
                        }
                    }
                });

            fBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (selectedFilterBtn[0] != null) {
                            selectedFilterBtn[0].setBackground(defaultColor);
                        }
                        fBtn.setBackground(selectedColor1);
                        selectedFilterBtn[0] = fBtn;
                    }
                });
            
            buttons.add(fBtn);
            filterPanel.add(fBtn);
            
            if (i == 0) {                      // nút đầu tiên là “All”
            selectedFilterBtn[0] = fBtn;   // ghi nhớ nút được chọn
            fBtn.setBackground(selectedColor1); // tô cam ngay từ đầu
        }
        }

        // Đặt mặc định nút "All" được chọn
            selectedFilterBtn[0] = buttons.get(0);

        /* -------- CONTENT GRID -------- */
        contentPanel = new JPanel(new GridLayout(0,3,20,20));
        contentPanel.setBackground(new Color(24,26,27));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());

        /* -------- MENU PANEL -------- */
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(24,26,27));
        JPanel mid = new JPanel(new BorderLayout());
        mid.setBackground(new Color(24,26,27));
        mid.add(filterPanel, BorderLayout.NORTH);
        mid.add(scrollPane,  BorderLayout.CENTER);

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(new Color(24,26,27));
        left.add(searchPanel, BorderLayout.NORTH);
        left.add(mid,         BorderLayout.CENTER);
        menuPanel.add(left,   BorderLayout.CENTER);

        /* -------- CARD SWITCHER -------- */
        mainCardLayout = new CardLayout();
        contentSwitcher = new JPanel(mainCardLayout);
        contentSwitcher.add(menuPanel,"Menu");

        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.add(topBar, BorderLayout.NORTH);
        contentArea.add(contentSwitcher, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);
        setContentPane(mainPanel);

        // Listener cho buttons
        buttons.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x = 0;
                getmenu(true, true, true);
            }
        });

        buttons.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x = 1;
                getmenu(true, false, false);
            }
        });

        buttons.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x = 2;
                getmenu(false, true, false);
            }
        });

        buttons.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x = 3;
                getmenu(false, false, true);
            }
        });
        

        /* -------- network thread -------- */
        new Thread(this::runNetworking).start();
    }

    /* ==================================================== */
    /*      2. NETWORK + DATA (giữ nguyên như trước)        */
    /* ==================================================== */

    private void getmenu(Boolean breakfast, Boolean lunch, Boolean dinner) {
        try {
            if (socket != null && socket.isConnected() && !socket.isOutputShutdown()) {
                outStream.writeObject("GET_MENU:" + Boolean.toString(breakfast) + ":" + Boolean.toString(lunch) + ":" + Boolean.toString(dinner));
                outStream.flush();
            }
        } catch (IOException e) {
            System.err.println("Loi khi gui yeu cau menu: " + e.getMessage());
        }
    }
    
    private void requestStaff() throws IOException {
        outStream.writeObject("GET_STAFF");
        outStream.flush();
    }

    private void runNetworking() {
        try {
            // this.socket = new Socket("26.106.134.18", 12344);
            this.socket = new Socket("localhost", 12344);

            this.outStream = new ObjectOutputStream(socket.getOutputStream());
            this.inStream = new ObjectInputStream(socket.getInputStream());

            if (socket != null && socket.isConnected() && !socket.isOutputShutdown()) {
                getmenu(true, true, true);
                SwingUtilities.invokeLater(() -> {
                    try {
                        requestStaff();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            while(true) {
                Object obj;
                try {
                    obj = inStream.readObject();
                } catch (EOFException eof) {
                    System.err.println("Server đã đóng kết nối.");
                    break; 
                }
                if (obj!=null && obj instanceof List<?>) {
                    List<?> list = (List<?>) obj;

                    if (list.isEmpty()) {
                        System.out.println("List rỗng – không biết kiểu gì, bỏ qua");
                        return;
                    }
                    Object first = list.get(0);
                    if (first instanceof MenuItem) {
                        List<MenuItem> raw = (List<MenuItem>) list;
                        this.menuitem = new ArrayList<>(raw);          // defensive copy
                        SwingUtilities.invokeLater(() -> {
                            showMenu(menuitem);
                            initAccountingPanel(new ArrayList<>(menuitem));
                        });
                        System.out.println("Menu");
                    } else if (first instanceof Staff) {
                        List<Staff> staffList = (List<Staff>) list;
                        SwingUtilities.invokeLater(() -> initStaffPanel(staffList));  
                        System.out.println("Staff");
                    }
                
                }
                
            }
        } catch (Exception e) {
        }
    }

    /* ------------ panels ------------ */
    private void initStaffPanel(List<Staff> data){
        if(staffPanel==null){
            staffPanel = new StaffPanel(data);
            contentSwitcher.add(staffPanel,"Staff (HR)");
        }else staffPanel.updateData(data);
    }
    private void initAccountingPanel(List<MenuItem> data) {
        accountingPanel = new AccountingPanel(data);
        contentSwitcher.add(accountingPanel, "Accounting");
    }

    /* ------------ menu show & search ------------ */
    private void showMenu(List<MenuItem> list){
        contentPanel.removeAll();
        for(MenuItem m:list) contentPanel.add(new AdminItemCard(m));
        contentPanel.revalidate(); contentPanel.repaint();
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    }
    private List<MenuItem> searchMenu(boolean b,boolean l,boolean d){
        if(menuitem==null) return Collections.emptyList();
        List<MenuItem> rs=new ArrayList<>();
        for(MenuItem m:menuitem){
            boolean ok=(b&&Boolean.TRUE.equals(m.isBreakfast()))
                    ||(l&&Boolean.TRUE.equals(m.isLunch()))
                    ||(d&&Boolean.TRUE.equals(m.isDinner()));
            if(ok) rs.add(m);
        }
        return rs;
    }
    public List<MenuItem> searchByName(String kw,int idx){
        kw=kw.toLowerCase();
        List<MenuItem> base;
        switch(idx){
            case 1: base=searchMenu(true,false,false); break;
            case 2: base=searchMenu(false,true,false); break;
            case 3: base=searchMenu(false,false,true); break;
            default:base=searchMenu(true,true,true);  break;
        }
        List<MenuItem> rs=new ArrayList<>();
        for(MenuItem m:base)
            if(m.getName()!=null&&m.getName().toLowerCase().contains(kw)) rs.add(m);
        return rs;
    }

    /* ==================================================== */
    /*                    UTILS                             */
    /* ==================================================== */
    private JButton buildBottomBtn(String text,String iconPath){
        JButton btn=new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,45));
        btn.setBackground(new Color(30,32,34));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial",Font.BOLD,17));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setIcon(new ImageIcon(
                new ImageIcon(iconPath).getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH)));
        /* hover */
        btn.addMouseListener(new HoverBtn(btn,new Color(30,32,34), new Color(60,63,67), new Color(84,88,95)));
        return btn;
    }

    /* lớp hover/pressed đơn giản dùng lại cho nhiều nút */
    private static class HoverBtn extends MouseAdapter{
        private final JComponent cmp;
        private final Color def, hov, prs;
        HoverBtn(JComponent c,Color d,Color h,Color p){
            this.cmp=c; this.def=d; this.hov=h; this.prs=p;
        }
        public void mouseEntered(MouseEvent e){ cmp.setBackground(hov); }
        public void mouseExited (MouseEvent e){ cmp.setBackground(def); }
        public void mousePressed(MouseEvent e){ cmp.setBackground(prs); }
        public void mouseReleased(MouseEvent e){
            cmp.setBackground(cmp.contains(e.getPoint())?hov:def);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(HomeUIAdmin::new);
    }
}
