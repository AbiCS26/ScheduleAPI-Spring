package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import com.scheduler.scheduleAPI.service.datastoreoperations.ContactOperations;
import com.scheduler.scheduleAPI.service.datastoreoperations.EventOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class EventService {

    public void deleteEvent(String id) {
        EventOperations.deleteEventById(id);
    }

    public List<Event> getAllEvents() {
        List<Event> eventList = EventOperations.getAllEventEntities();
        List<Event> list = new ArrayList<>();

        for (Event event : eventList) {
            list.add(
                    event.setParticipants(getListOfParticipants(event.getParticipantIds()))
            );
        }
        return list;
    }

    public String storeEvent(Event event) {
        return EventOperations.storeEvent(buildNewEvent(event));
    }

    public String modifyEvent(String id, Event event) {
        return EventOperations.storeEvent(buildModifiedEvent(event, id));
    }

    public Event getEventById(String id) {
        Event event = EventOperations.getEventEntityById(id);
        if (event == null)
            throw new InputMismatchException("Enter valid ID");
        event.setParticipants(getListOfParticipants(event.getParticipantIds()));

        return event;
    }

    public List<Event> getEventByTimeRange(String start, String end) {
        return EventOperations.getEventEntitiesByTimeRange(start, end);
    }

    public List<Event> getEventsSortedById() {
        return EventOperations.getEventEntitiesSortedById();
    }

    public List<Event> getEventsSortedByDuration() {
        return EventOperations.getEventEntitiesSortedByDuration();
    }

    public List<Event> getEventsSortedByCreatedTime() {
        return EventOperations.getEventEntitiesSortedByCreatedTime();
    }

    public List<Event> getEventsSortedByStartTime() {
        return EventOperations.getEventEntitiesSortedByStartTime();
    }

    public List<Event> getEventsSortedByNumberOfParticipants() {
        return EventOperations.getEventEntitiesSortedByNumberOfParticipants();
    }

    public List<Contact> getEventByEmail(String email) {
        return EventOperations.getEventEntityByEmail(email);
    }


    public Event buildNewEvent(Event e) {
        return Event.newBuilder()
                .setName(e.getName())
                .setStartsAt(e.getStartsAt())
                .setDuration(e.getDuration())
                .setParticipantIds(e.getParticipantIds())
                .setCreatedDate()
                .setId()
                .build();
    }

    public Event buildModifiedEvent(Event e, String id) {
        long createdTime = EventOperations.getEventEntityById(id).getCreatedDate();

        return Event.newBuilder()
                .setName(e.getName())
                .setStartsAt(e.getStartsAt())
                .setDuration(e.getDuration())
                .setParticipantIds(e.getParticipantIds())
                .setId(id)
                .setCreatedDate(createdTime)
                .setModifiedDate()
                .build();
    }

    private List<Contact> getListOfParticipants(List<String> participants) {
        List<Contact> list = new ArrayList<>();
        for (String s : participants) {
            list.add(
                    ContactOperations.getContactsEntity(s)
            );
        }
        return list;
    }
}
