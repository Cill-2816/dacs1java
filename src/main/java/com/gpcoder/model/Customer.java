package com.gpcoder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "customer")
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    @Id
    @Column(name = "phonenb", length = 12)
    private String phoneNumber;

    @Column(name = "fullname", length = 45)
    private String fullName;

    @Column(name = "rewardpoints")
    private int rewardPoints;

    // Constructors
    public Customer() {}

    public Customer(String phoneNumber, String fullName, int rewardPoints) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.rewardPoints = rewardPoints;
    }

    // Getters & Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
