package com.gpcoder.model_xml;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gpcoder.model.Staff;

@XmlRootElement(name = "staffs")
public class StaffList {
    private List<Staff> staff = new ArrayList<>(); // đảm bảo không null

    @XmlElement(name = "staff")
    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }
}
