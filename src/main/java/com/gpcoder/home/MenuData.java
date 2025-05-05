package com.gpcoder.home;
import java.util.Arrays;
import java.util.List;

public class MenuData {
    public static List<MenuItem> getSampleMenu() {
        return Arrays.asList(
            new MenuItem("Southwest Scramble Bowl", "Perfectly seasoned scrambled eggs served with toast.", "$17.65", "image/food1.png"),
            new MenuItem("Grilled Chicken Salad", "Fresh greens with grilled chicken and vinaigrette.", "$13.50", "image/food2.png"),
            new MenuItem("Classic Burger", "Juicy beef patty with cheese and pickles.", "$11.90", "image/food3.png"),
            new MenuItem("Pasta Alfredo", "Creamy Alfredo sauce with fettuccine.", "$14.75", "image/food4.png"),
            new MenuItem("Tuna Sandwich", "Toasted sandwich with tuna and veggies.", "$9.85", "image/food5.png"),
            new MenuItem("Vegan Bowl", "A healthy mix of grains, veggies and tofu.", "$12.30", "image/food6.png")
        );
    }
}

