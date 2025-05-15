package com.gpcoder.model_xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gpcoder.model.DetailInvoice;

@XmlRootElement(name = "detailinvoices")
public class DetailInvoiceList {
    private List<DetailInvoice> DetailInvoices = new ArrayList<>(); 

    @XmlElement(name = "detailinvoice")
    public List<DetailInvoice> getDetailInvoice() {
        return DetailInvoices;
    }

    public void setDetailInvoice(List<DetailInvoice> DetailInvoices) {
        this.DetailInvoices = DetailInvoices;
    }
}
