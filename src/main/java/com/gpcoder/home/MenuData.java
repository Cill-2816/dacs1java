package com.gpcoder.home;
import java.util.Arrays;
import java.util.List;

public class MenuData {
    public static List<MenuItem> getSampleMenu() {
        return Arrays.asList(
            new MenuItem("Bánh Bột Lọc", "Tapioca Dumplings", "$17.65", "image/food1.png", 20),
            new MenuItem("Mì Quảng", "Quang-Style Noodles", "$13.50", "image/food2.png", 40),
            new MenuItem("Bánh Khọt", "Mini Savory Pancakes", "$11.90", "image/food3.png", 100),
            new MenuItem("Phở Bò", "Beef Noodle Soup", "$14.75", "image/food4.png", 120),
            new MenuItem("Bánh Xèo", "Vietnamese Sizzling Pancake", "$9.85", "image/food5.png", 125),
            new MenuItem("Chả Giò", "Fried Spring Rolls", "$12.30", "image/food6.png", 55),
            new MenuItem("Bánh Bao Chiên", "Fried Steamed Bun", "$14.75", "image/food7.png", 67),
            new MenuItem("Cơm Tấm Sườn Bì Chả", "Broken Rice with Grilled Pork and Egg", "$9.85", "image/food8.png", 344),
            new MenuItem("Phở Chiên Phồng", "Crispy Fried Pho Noodles", "$12.30", "image/food9.png", 55),
            new MenuItem("Bánh Khoai Mì Sợi", "Shredded Cassava Cake", "$14.75", "image/food10.png", 56),
            new MenuItem("Bánh Đa Cua Hải Phòng", "Hai Phong Crab Noodle Soup", "$9.85", "image/food11.png", 77),
            new MenuItem("Bánh Mì Xá Xíu", "Char Siu Pork Baguette", "$12.30", "image/food12.png", 87)
        );
    }
}

