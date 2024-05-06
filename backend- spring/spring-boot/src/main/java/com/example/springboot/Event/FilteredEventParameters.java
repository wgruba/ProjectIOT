package com.example.springboot.Event;

import java.util.List;

public class FilteredEventParameters {
    private String name;
    private List<Integer> categoryList;
    private String localisation;
    private String startDate;
    private String endDate;
    private Boolean isFinished;
    private Integer reservation;
    private Integer isFree;
    private AgeGroup ageGroup;

    public FilteredEventParameters(String name, List<Integer> categoryList, String localisation, String startDate, String endDate, Boolean isFinished, Integer reservation, Integer isFree, AgeGroup ageGroup) {
        this.name = name;
        this.categoryList = categoryList;
        this.localisation = localisation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinished = isFinished;
        this.reservation = reservation;
        this.isFree = isFree;
        this.ageGroup = ageGroup;
    }

    public FilteredEventParameters() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Integer> categoryList) {
        this.categoryList = categoryList;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Integer getReservation() {
        return reservation;
    }

    public void setReservation(Integer reservation) {
        this.reservation = reservation;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }
}
