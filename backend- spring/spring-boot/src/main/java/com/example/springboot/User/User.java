package com.example.springboot.User;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "User")
public class User {
    @MongoId
    private Integer id;
    private String name;
    private String mail;

    private String password;

    private PermissionLevel permissionLevel;
    private List<Integer> subscribedEvents;
    private List<Integer> subscribedCategories;

    public User() {
    }

    public User(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.permissionLevel = permissionLevel;
        this.subscribedEvents = subscribedEvents;
        this.subscribedCategories = subscribedCategories;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public List<Integer> getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(List<Integer> subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }

    public List<Integer> getSubscribedCategories() {
        return subscribedCategories;
    }

    public void setSubscribedCategories(List<Integer> subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
    }
}