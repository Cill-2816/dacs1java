package com.gpcoder.model_xml;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gpcoder.model.MenuItem;

@XmlRootElement(name = "menuitems")
public class MenuItemList {
    private List<MenuItem> MenuItems = new ArrayList<>();

    @XmlElement(name = "menuitem")
    public List<MenuItem> getMenuItem() {
        return MenuItems;
    }

    public void setMenuItem(List<MenuItem> MenuItems) {
        this.MenuItems = MenuItems;
    }
}
