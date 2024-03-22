package com.example.clubeventmanagementprogram.service;

import com.example.clubeventmanagementprogram.model.Event;

import java.util.List;

public interface EventService {
    void addEvent(Event event);
    void updateEvent(Event event);
    void deleteEvent(int id);
    Event getEventById(int id);
    List<Event> getAllEvents();
}
