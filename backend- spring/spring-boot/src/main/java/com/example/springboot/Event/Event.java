package com.example.springboot.Event;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Event")
public class Event {
    @MongoId
    private Integer id;
    private String name;
    private int organizer;

    private List<Integer> categoryList;

    private List<Integer> clientList;

    private String description;

    private int size;

    private String localisation;

    private boolean isFree; //not paid

    private boolean isReservationNecessary;
    private AgeGroup ageGroup;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
    private EventStatus eventStatus;
    private String imageUrl;

    public Event() {
    }

    public Event(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate, EventStatus eventStatus, String imageUrl) {
        this.id = id;
        this.name = name;
        this.organizer = organizer;
        this.categoryList = categoryList;
        this.clientList = clientList;
        this.description = description;
        this.size = size;
        this.localisation = localisation;
        this.isFree = isFree;
        this.isReservationNecessary = isReservationNecessary;
        this.ageGroup = ageGroup;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventStatus = eventStatus;
        this.imageUrl = imageUrl;
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

    public int getOrganizer() {
        return organizer;
    }

    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }

    public List<Integer> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Integer> categoryList) {
        this.categoryList = categoryList;
    }
    public List<Integer> getClientList() {
        return clientList;
    }

    public void setClientList(List<Integer> clientList) {
        this.clientList = clientList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isReservationNecessary() {
        return isReservationNecessary;
    }

    public void setReservationNecessary(boolean reservationNecessary) {
        isReservationNecessary = reservationNecessary;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}