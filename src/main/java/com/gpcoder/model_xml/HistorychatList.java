package com.gpcoder.model_xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gpcoder.model.Historychat;

@XmlRootElement(name = "historychats")
public class HistorychatList {
    private List<Historychat> Historychats = new ArrayList<>(); 

    @XmlElement(name = "historychat")
    public List<Historychat> getHistorychat() {
        return Historychats;
    }

    public void setHistorychat(List<Historychat> Historychats) {
        this.Historychats = Historychats;
    }
}
