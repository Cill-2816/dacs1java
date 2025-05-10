package com.gpcoder.model;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="historychat")
public class Historychat implements Serializable {
    @Column(name="sent_id")
    private String sent_id;

    @Column(name="recieve_id")
    private String recieve_id;

    @Column(name="message_type")
    private String message_type;

    @Column(name="message")
    private String message;

    @Column(name="sent_time")
    private LocalDateTime sent_time;

    public Historychat() {
    }

    public Historychat(String message, String message_type, String recieve_id, String sent_id, LocalDateTime sent_time) {
        this.message = message;
        this.message_type = message_type;
        this.recieve_id = recieve_id;
        this.sent_id = sent_id;
        this.sent_time = sent_time;
    }

    public String getSent_id() {
        return sent_id;
    }

    public String getRecieve_id() {
        return recieve_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public String getMessage() {
        return message;
    }

    public void setSent_id(String sent_id) {
        this.sent_id = sent_id;
    }

    public void setRecieve_id(String recieve_id) {
        this.recieve_id = recieve_id;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Historychat [sent_id=" + sent_id + ", recieve_id=" + recieve_id + ", message_type=" + message_type
                + ", message=" + message + "]";
    }

    public LocalDateTime getSent_time() {
        return sent_time;
    }

    public void setSent_time(LocalDateTime sent_time) {
        this.sent_time = sent_time;
    }

}