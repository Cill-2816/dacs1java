package com.gpcoder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "detail_invoice")
@IdClass(DetailInvoiceId.class)
@XmlRootElement(name = "detail_invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class DetailInvoice {

    @Id
    @Column(name = "invoice_id")
    private int invoiceId;

    @Id
    @Column(name = "item_id")
    private int itemId;

    @ManyToOne
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    private MenuItem item;


    @Column(name = "amount")
    private int amount;

    public DetailInvoice() {}

    public DetailInvoice(int invoiceId, MenuItem item, int amount) {
        this.invoiceId = invoiceId;
        this.item = item;
        this.amount = amount;
    }

    // Getters & Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
