package com.gpcoder.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="menuitem")
@XmlRootElement(name = "menuitem")
@XmlAccessorType(XmlAccessType.FIELD)
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
    private double price;

    @Column(name="imagePath")
    private String imagePath;

    @Column(name="salesCount")
    private int salesCount; 

    @Column(name = "breakfast")
    private boolean breakfast;

    @Column(name = "lunch")
    private boolean lunch;

    @Column(name = "dinner")
    private boolean dinner;

    public MenuItem(String name, String description, double price,
                    String imagePath, int salesCount,
                    boolean breakfast, boolean lunch, boolean dinner) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.salesCount = salesCount;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public MenuItem() {
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public int getSalesCount() { return salesCount; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setSalesCount(int salesCount) { this.salesCount = salesCount; }
    public boolean isBreakfast() { return breakfast; }
    public void setBreakfast(boolean breakfast) { this.breakfast = breakfast; }
    public boolean isLunch() { return lunch; }
    public void setLunch(boolean lunch) { this.lunch = lunch; }
    public boolean isDinner() { return dinner; }
    public void setDinner(boolean dinner) { this.dinner = dinner; }

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
        sb.append(", breakfast=").append(breakfast);
        sb.append(", lunch=").append(lunch);
        sb.append(", dinner=").append(dinner);
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

}
