package com.gpcoder.model;

import java.io.Serializable;
import java.util.Objects;

public class HistorychatId implements Serializable {
    private String sent_id;
    private String recieve_id;

    public HistorychatId() {}

    public HistorychatId(String sent_id, String recieve_id) {
        this.sent_id = sent_id;
        this.recieve_id = recieve_id;
    }

    // Override equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistorychatId)) return false;
        HistorychatId that = (HistorychatId) o;
        return Objects.equals(sent_id, that.sent_id) &&
               Objects.equals(recieve_id, that.recieve_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sent_id, recieve_id);
    }
}
