package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Event;
import com.scheduler.scheduleAPI.validation.PermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;

@Service
public class EventService {

    private final ObjectifyOperations objectifyOperations;
    private final ModelBuilder modelBuilder;
    private final PermissionChecker permissionChecker;

    @Autowired
    public EventService(ObjectifyOperations objectifyOperations,
                        ModelBuilder modelBuilder,
                        PermissionChecker permissionChecker) {
        this.objectifyOperations = objectifyOperations;
        this.modelBuilder = modelBuilder;
        this.permissionChecker = permissionChecker;
    }

    public void deleteEvent(String eventId, String calendarId) {
        permissionChecker.hasPermissionForEvent(eventId, calendarId);

        objectifyOperations.deleteEntity(eventId, Event.class);
    }

    public List<Event> getAllEvents() {
        return objectifyOperations.getAllEntities(Event.class);
    }

    public String storeEvent(Event e) {
        Event event = modelBuilder.buildNewEvent(e);
        objectifyOperations.storeEntity(event);
        return event.getId();
    }

    public String modifyEvent(String eventId, String calendarId, Event e) {
        permissionChecker.hasPermissionForEvent(eventId, calendarId);

        Event event = modelBuilder.buildModifiedEvent(eventId, calendarId, e);
        objectifyOperations.storeEntity(event);
        return event.getId();
    }

    public void addParticipants(String eventId, String calendarId, List<String> participants) {
        permissionChecker.hasPermissionForEvent(eventId, calendarId);

        objectifyOperations.transactParticipants(eventId, calendarId, participants);
    }

    public Event getEventById(String eventId, String calendarId) {
        Event event = objectifyOperations.getEventById(eventId, calendarId);
        if (event == null)
            throw new InputMismatchException("Event with ID: " + eventId + " is not present");
        return event;
    }

    public List<Event> getEventByTimeRange(String start, String end) {
        return objectifyOperations.getEventEntitiesByTimeRange(start, end);
    }

    public List<Event> getEventsSortedById() {
        return objectifyOperations.getEventEntitiesSortedById();
    }

    public List<Event> getEventsSortedByDuration() {
        return objectifyOperations.getEventEntitiesSortedByDuration();
    }

    public List<Event> getEventsSortedByCreatedTime() {
        return objectifyOperations.getEventEntitiesSortedByCreatedTime();
    }

    public List<Event> getEventsSortedByStartTime() {
        return objectifyOperations.getEventEntitiesSortedByStartTime();
    }

    public List<Event> getEventsSortedByNumberOfParticipants() {
        return objectifyOperations.getEventEntitiesSortedByNumberOfParticipants();
    }

    public List<Event> getEventByEmail(String email) {
        return objectifyOperations.getEventEntityByEmail(email);
    }
}
