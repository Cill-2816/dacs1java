package com.gpcoder.model_xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gpcoder.model.Invoice;

@XmlRootElement(name = "invoices")
public class InvoiceList {
    private List<Invoice> Invoices = new ArrayList<>(); 

    @XmlElement(name = "invoice")
    public List<Invoice> getInvoices() {
        return Invoices;
    }

    public void setInvoices(List<Invoice> Invoices) {
        this.Invoices = Invoices;
    }
}
