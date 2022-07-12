package com.wsdl.sample.web;

import javax.xml.bind.annotation.*;

/**
 * Created by: Tharindu Eranga
 * Date: 09 Jul 2022
 **/
@XmlRootElement(name = "customerResponse", namespace = "tns:wpg")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customer", propOrder = {
        "id",
        "name"
})
public class CustomerResponse {
    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String name;

    public CustomerResponse() {
    }

    public CustomerResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}