package com.example.domain;


//lombok注解构造实体类

import java.net.Inet4Address;

public class Item {
    private Integer id;
    private String type;
    private String name;
    private String description;

    private String contact_username;

    private Integer contact_user_id;

    public String getContact_username() {
        return contact_username;
    }

    public void setContact_username(String contact_username) {
        this.contact_username = contact_username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getContact_user_id() {
        return contact_user_id;
    }

    public void setContact_user_id(Integer contact_user_id) {
        this.contact_user_id = contact_user_id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contact_username='" + contact_username + '\'' +
                ", contact_user_id=" + contact_user_id +
                '}';
    }
}
