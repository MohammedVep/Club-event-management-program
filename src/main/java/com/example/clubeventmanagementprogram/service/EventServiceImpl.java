package com.example.clubeventmanagementprogram.service;
import com.example.clubeventmanagementprogram.dao.EventDAO;
import com.example.clubeventmanagementprogram.model.Event;
import java.util.List;

public class EventServiceImpl implements EventService{

    private EventDAO eventDao;
    @Override
    public void addEvent(Event event) {
        eventDao.createEvent(event);
    }

    @Override
    public void updateEvent(Event event) {
        eventDao.updateEvent(event);
    }

    @Override
    public void deleteEvent(int id) {
        eventDao.deleteEvent(id);
    }

    @Override
    public Event getEventById(int id) {
        return eventDao.getEventById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }
}
