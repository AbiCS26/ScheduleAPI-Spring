package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.exception.PermissionException;
import com.scheduler.scheduleAPI.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private final ObjectifyOperations objectifyOperations;
    @Autowired
    private final MyUser myUser;

    public EventService(ObjectifyOperations objectifyOperations, MyUser myUser) {
        this.objectifyOperations = objectifyOperations;
        this.myUser = myUser;
    }

    public void deleteEvent(String eventId, String calendarId) {
        UserDetails userDetails = myUser.getCurrentUser();
        Event event = objectifyOperations.getEventById(eventId, calendarId);

        if (event == null)
            throw new InputMismatchException("Event with ID: " + eventId + " is not present");

        if (event.getCreatedBy().equals(userDetails.getUsername()))
            objectifyOperations.deleteEntity(eventId, Event.class);
        else
            throw new PermissionException("User is not allowed to delete this event");
    }

    public List<Event> getAllEvents() {
        return objectifyOperations.getAllEntities(Event.class);
    }

    public String storeEvent(Event e) {
        Event event = buildNewEvent(e);
        objectifyOperations.storeEntity(event);
        return event.getId();
    }

    public String modifyEvent(String eventId, String calendarId, Event e) {
        Event event = buildModifiedEvent(eventId, calendarId, e);
        objectifyOperations.storeEntity(event);
        return event.getId();
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


    public Event buildNewEvent(Event e) {

        return Event.newBuilder()
                .setId()
                .setName(e.getName())
                .setStartsAt(e.getStartsAt())
                .setDuration(e.getDuration())
                .setParticipantIds(e.getParticipantIds())
                .setCalendar(e.getCalendarId())
                .setText(e.getDescription())
                .setCreatedBy(myUser.getCurrentUser().getUsername())
                .setCreatedDate()
                .build();
    }

    public Event buildModifiedEvent(String eventId, String calendarId, Event e) {
        long createdTime = objectifyOperations.getEventById(eventId, calendarId).getCreatedDate();

        return Event.newBuilder()
                .setName(e.getName())
                .setStartsAt(e.getStartsAt())
                .setDuration(e.getDuration())
                .setParticipantIds(e.getParticipantIds())
                .setId(eventId)
                .setCalendar(calendarId)
                .setCreatedBy(myUser.getCurrentUser().getUsername())
                .setCreatedDate(createdTime)
                .setModifiedDate()
                .build();
    }

    public void addParticipants(String eventId, String calendarId, List<String> participants) {
        objectifyOperations.transactParticipants(eventId, calendarId, participants);
    }
}
