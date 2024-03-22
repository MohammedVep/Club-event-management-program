package com.example.clubeventmanagementprogram.model;

import java.time.LocalDate;
import java.time.LocalTime;


public class Event {
    private int id;
    private String eventName;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Event(int id, String eventName, String description, LocalDate date, LocalTime startTime, LocalTime endTime){
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}