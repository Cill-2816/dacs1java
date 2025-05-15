package com.gpcoder.adapter;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return (v == null || v.isEmpty()) ? null : LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return (v == null) ? null : v.toString();   // ISO-8601, ví dụ 2025-05-15T21:20:33.512
    }
}
