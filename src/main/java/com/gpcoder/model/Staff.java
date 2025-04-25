package com.gpcoder.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="staff")
public class Staff {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private String id;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="gender")
    private boolean gender;

    @Column(name="phonenb")
    private String phonenb;

    @Column(name="address")
    private String address;

    public Staff() {
    }

    public Staff(String id, String firstname, String lastname, boolean gender, String phonenb, String address) {
        this.address = address;
        this.firstname = firstname;
        this.gender = gender;
        this.id = id;
        this.lastname = lastname;
        this.phonenb = phonenb;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isGender() {
        return gender;
    }

    public String getPhonenb() {
        return phonenb;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setPhonenb(String phonenb) {
        this.phonenb = phonenb;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Staff{");
        sb.append("id=").append(id);
        sb.append(", firstname=").append(firstname);
        sb.append(", lastname=").append(lastname);
        sb.append(", gender=").append(gender);
        sb.append(", phonenb=").append(phonenb);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }


}
