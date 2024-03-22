package com.example.clubeventmanagementprogram.model;

import java.util.List;

public class Club {
    private int id;
    private String clubName;
    private String description;
    private List<String> topics;

    public Club(int id, String name, String description, List<String> topics) {
        this.id = id;
        this.clubName = name;
        this.description = description;
        this.topics = topics;
    }

    public int getId() {
        return id;
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

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
