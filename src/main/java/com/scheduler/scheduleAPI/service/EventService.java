package com.scheduler.scheduleAPI.service;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Value;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    public void deleteEvent(String id) {
        DatastoreOperations.deleteEventById(id);
    }

    public List<Event> getAllEvents() {
        List<Entity> entityList = DatastoreOperations.getAllEventEntities();
        List<Event> list = new ArrayList<>();

        for (Entity entity : entityList) {
            list.add(
                    new Event().convertEntityToEvent(
                            entity, getListOfParticipants(entity.getList("participants")))
            );
        }
        return list;
    }

    private List<Contact> getListOfParticipants(List<Value<String>> participants) {
        List<Contact> list = new ArrayList<>();
        for (Value value : participants) {
            list.add(
                    DatastoreOperations.getContactsEntity(value.get().toString())
            );
        }
        return list;
    }

    private List<Contact> getListOfParticipants1(List<String> participants) {
        List<Contact> list = new ArrayList<>();
        for (String s : participants) {
            list.add(
                    DatastoreOperations.getContactsEntity(s)
            );
        }
        System.out.println(list.get(0).getName());
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
        event.setParticipants(getListOfParticipants1(event.getParticipantIds()));
        System.out.println(event.getName());
        System.out.println(event.getCreatedDate());
        System.out.println(event.getParticipantIds());
        //        return new Event().convertEntityToEvent(entity, getListOfParticipants(entity.getList("participants")));
        return event;
    }

    public List<Event> getEventByTimeRange(String start, String end) {
        return convertToEventList(DatastoreOperations.getEntitiesByTimeRange(start, end));
    }

    public List<Event> getEventsSortedById() {

        return convertToEventList(DatastoreOperations.getEntitiesSortedById());
    }

    public List<Event> getEventsSortedByDuration() {
        return convertToEventList(DatastoreOperations.getEntitiesSortedByDuration());

    }

    public List<Event> getEventsSortedByCreatedTime() {
        return convertToEventList(DatastoreOperations.getEntitiesSortedByCreatedTime());

    }

    public List<Event> getEventsSortedByStartTime() {
        return convertToEventList(DatastoreOperations.getEntitiesSortedByStartTime());

    }

    public List<Event> getEventsSortedByNumberOfParticipants() {
        return convertToEventList(DatastoreOperations.getEntitiesSortedByNumberOfParticipants());

    }

    public List<Entity> getEventByEmail(String email) {
        return DatastoreOperations.getEntityByEmail(email);
    }

    private List<Event> convertToEventList(List<Entity> entityList) {
        List<Event> eventList = new ArrayList<>();
        for (Entity e : entityList) {
            eventList.add(new Event().convertEntityToEvent(e, getListOfParticipants(e.getList("participants"))));
        }
        return eventList;
    }

//    private List<Event> convertToEventList(List<Entity> entityList, List<Contact> participants) {
//        List<Event> eventList = new ArrayList<>();
//        for (Entity e : entityList) {
//            eventList.add(new Event().convertEntityToEvent(e));
//        }
//        return eventList;
//    }

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

    private List<Contact> buildNewParticipants(List<Contact> participants) {
        List<Contact> contactList = new ArrayList<>();
        for (Contact contact : participants) {
            contactList.add(
                    Contact.newBuilder()
                            .setName(contact.getName())
                            .setEmail(contact.getEmail())
                            .setMobileNumber(contact.getMobileNumber())
                            .build()
            );
        }
        return contactList;
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
}
