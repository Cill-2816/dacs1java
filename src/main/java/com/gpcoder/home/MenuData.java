package com.gpcoder.home;
import java.util.Arrays;
import java.util.List;

public class MenuData {
    public static List<MenuItem> getSampleMenu() {
        return Arrays.asList(
            new MenuItem("Bánh Bột Lọc", "Perfectly seasoned scrambled eggs served with toast", "$17.65", "image/food1.png"),
            new MenuItem("Mì Quảng", "Fresh greens with grilled chicken and vinaigrette", "$13.50", "image/food2.png"),
            new MenuItem("Bánh Khọt", "Juicy beef patty with cheese and pickles", "$11.90", "image/food3.png"),
            new MenuItem("Phở Bò", "Creamy Alfredo sauce with fettuccine", "$14.75", "image/food4.png"),
            new MenuItem("Bánh Xèo", "Toasted sandwich with tuna and veggies", "$9.85", "image/food5.png"),
            new MenuItem("Chả Giò", "A healthy mix of grains, veggies and tofu", "$12.30", "image/food6.png"),
            new MenuItem("Bánh Bao Chiên", "Creamy Alfredo sauce with fettuccine", "$14.75", "image/food7.png"),
            new MenuItem("Cơm Tấm Sườn Bì Chả", "Toasted sandwich with tuna and veggies", "$9.85", "image/food8.png"),
            new MenuItem("Phở Chiên Phồng", "A healthy mix of grains, veggies and tofu", "$12.30", "image/food9.png"),
            new MenuItem("Bánh Khoai Mì Sợi", "Creamy Alfredo sauce with fettuccine", "$14.75", "image/food10.png"),
            new MenuItem("Bánh Đa Cua Hải Phòng", "Toasted sandwich with tuna and veggie", "$9.85", "image/food11.png"),
            new MenuItem("Bánh Mì Xá Xíu", "A healthy mix of grains, veggies and tofu", "$12.30", "image/food12.png")
        );
    }
}

