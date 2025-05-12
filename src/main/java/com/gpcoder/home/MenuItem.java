package com.gpcoder.home;

public class MenuItem {
    private String name;
    private String description;
    private String price;
    private String imagePath;
    private int salesCount; // 👉 Thêm dòng này

    public MenuItem(String name, String description, String price, String imagePath) {
        this(name, description, price, imagePath, 0); // mặc định sales = 0
    }

    public MenuItem(String name, String description, String price, String imagePath, int salesCount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.salesCount = salesCount;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    // Các getter/setter còn lại giữ nguyên
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(String price) { this.price = price; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
