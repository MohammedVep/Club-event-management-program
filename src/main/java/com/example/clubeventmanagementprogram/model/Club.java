package com.example.clubeventmanagementprogram.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Club implements Serializable {
    private int id;
    private String clubName;
    private String description;
    private String topics;
    private BooleanProperty selected;



    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    private LocalDate dateAdded;

    public Club(int id, String name, String description, String topics) {
        this.id = id;
        this.clubName = name;
        this.description = description;
        this.topics = topics;
        this.selected = new SimpleBooleanProperty(false);
    }

    public int getId() {
        return id;
    }
    public String getTopicsString() {
        return String.join(", ", topics);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
