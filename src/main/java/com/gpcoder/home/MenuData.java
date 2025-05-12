package com.gpcoder.home;
import java.util.Arrays;
import java.util.List;

public class MenuData {
    public static List<MenuItem> getSampleMenu() {
        return Arrays.asList(
            new MenuItem("Bánh Bột Lọc", "Perfectly seasoned scrambled eggs served with toast", "$17.65", "image/food1.png", 20),
            new MenuItem("Mì Quảng", "Fresh greens with grilled chicken and vinaigrette", "$13.50", "image/food2.png", 40),
            new MenuItem("Bánh Khọt", "Juicy beef patty with cheese and pickles", "$11.90", "image/food3.png", 100),
            new MenuItem("Phở Bò", "Creamy Alfredo sauce with fettuccine", "$14.75", "image/food4.png", 120),
            new MenuItem("Bánh Xèo", "Toasted sandwich with tuna and veggies", "$9.85", "image/food5.png", 125),
            new MenuItem("Chả Giò", "A healthy mix of grains, veggies and tofu", "$12.30", "image/food6.png", 55),
            new MenuItem("Bánh Bao Chiên", "Creamy Alfredo sauce with fettuccine", "$14.75", "image/food7.png", 67),
            new MenuItem("Cơm Tấm Sườn Bì Chả", "Toasted sandwich with tuna and veggies", "$9.85", "image/food8.png", 344),
            new MenuItem("Phở Chiên Phồng", "A healthy mix of grains, veggies and tofu", "$12.30", "image/food9.png", 55),
            new MenuItem("Bánh Khoai Mì Sợi", "Creamy Alfredo sauce with fettuccine", "$14.75", "image/food10.png", 56),
            new MenuItem("Bánh Đa Cua Hải Phòng", "Toasted sandwich with tuna and veggie", "$9.85", "image/food11.png", 77),
            new MenuItem("Bánh Mì Xá Xíu", "A healthy mix of grains, veggies and tofu", "$12.30", "image/food12.png", 87)
        );
    }
}

