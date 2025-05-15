package com.gpcoder.model_xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gpcoder.model.Customer;

@XmlRootElement(name = "customers")
public class CustomerList {
    private List<Customer> Customers = new ArrayList<>(); 

    @XmlElement(name = "customer")
    public List<Customer> getDetailInvoice() {
        return Customers;
    }

    public void setDetailInvoice(List<Customer> Customers) {
        this.Customers = Customers;
    }
}
