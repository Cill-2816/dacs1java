package com.gpcoder.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="menuitem")
public class MenuItem implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private String price;

    @Column(name="imagePath")
    private String imagePath;

    @Column(name="salesCount")
    private int salesCount; 

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

    public MenuItem() {
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public int getSalesCount() { return salesCount; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(String price) { this.price = price; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setSalesCount(int salesCount) { this.salesCount = salesCount; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MenuItem{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);
        sb.append(", imagePath=").append(imagePath);
        sb.append(", salesCount=").append(salesCount);
        sb.append('}');
        return sb.toString();
    }

}
