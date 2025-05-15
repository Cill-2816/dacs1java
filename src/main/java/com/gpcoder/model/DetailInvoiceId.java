package com.gpcoder.model;

import java.io.Serializable;
import java.util.Objects;

public class DetailInvoiceId implements Serializable {
    private int invoiceId;
    private int itemId;

    public DetailInvoiceId() {}

    public DetailInvoiceId(int invoiceId, int itemId) {
        this.invoiceId = invoiceId;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailInvoiceId)) return false;
        DetailInvoiceId that = (DetailInvoiceId) o;
        return invoiceId == that.invoiceId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, itemId);
    }
}
