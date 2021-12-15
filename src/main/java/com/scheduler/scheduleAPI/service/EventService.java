package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class EventService {

    public void deleteEvent(String id) {
        DatastoreOperations.deleteEventById(id);
    }

    public List<Event> getAllEvents() {
        List<Event> eventList = DatastoreOperations.getAllEventEntities();
        List<Event> list = new ArrayList<>();

        for (Event event : eventList) {
            list.add(
                    event.setParticipants(getListOfParticipants(event.getParticipantIds()))
            );
        }
        return list;
    }

    public String storeEvent(Event event) {
        return DatastoreOperations.storeEvent(buildNewEvent(event));
    }

    public String modifyEvent(String id, Event event) {
        return DatastoreOperations.storeEvent(buildModifiedEvent(event, id));
    }

    public Event getEventById(String id) {
        Event event = DatastoreOperations.getEventEntityById(id);
        if (event == null)
            throw new InputMismatchException("Enter valid ID");
        event.setParticipants(getListOfParticipants(event.getParticipantIds()));

        return event;
    }

    public List<Event> getEventByTimeRange(String start, String end) {
        return DatastoreOperations.getEntitiesByTimeRange(start, end);
    }

    public List<Event> getEventsSortedById() {
        return DatastoreOperations.getEntitiesSortedById();
    }

    public List<Event> getEventsSortedByDuration() {
        return DatastoreOperations.getEntitiesSortedByDuration();
    }

    public List<Event> getEventsSortedByCreatedTime() {
        return DatastoreOperations.getEntitiesSortedByCreatedTime();
    }

    public List<Event> getEventsSortedByStartTime() {
        return DatastoreOperations.getEntitiesSortedByStartTime();
    }

    public List<Event> getEventsSortedByNumberOfParticipants() {
        return DatastoreOperations.getEntitiesSortedByNumberOfParticipants();
    }

    public List<Contact> getEventByEmail(String email) {
        return DatastoreOperations.getEntityByEmail(email);
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
        long createdTime = DatastoreOperations.getEventEntityById(id).getCreatedDate();

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
                    DatastoreOperations.getContactsEntity(s)
            );
        }
        return list;
    }
}
