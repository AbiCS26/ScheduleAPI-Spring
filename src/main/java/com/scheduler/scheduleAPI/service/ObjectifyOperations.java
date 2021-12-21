package com.scheduler.scheduleAPI.service;

import com.googlecode.objectify.Key;
import com.scheduler.scheduleAPI.model.Calendar;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Service
public class ObjectifyOperations {

    public <T> List<T> getAllEntities(Class<T> className) {
        return ofy().load().type(className).list();
    }

    public <T> T getEntityById(String id, Class<T> className) {
        return ofy().load().type(className).id(id).now();
    }

    public Event getEventById(String eventId, String calendarId) {
        Key<Calendar> key = Key.create(Calendar.class, calendarId);
        return ofy().load().type(Event.class).parent(key).id(eventId).now();
    }

    public <T> void storeEntity(T object) {
        ofy().save().entity(object).now();
    }

    public <T> void deleteEntity(String id, Class<T> className) {
        ofy().delete().type(className).id(id).now();
    }

    public List<Event> getEventEntitiesByTimeRange(String start, String end) {
        return ofy().load().type(Event.class)
                .filter("startsAt >=", Long.parseLong(start))
                .filter("startsAt <=", Long.parseLong(end))
                .list();
    }

    public List<Event> getEventEntitiesSortedById() {
        return ofy().load().type(Event.class).orderKey(true).list();
    }

    public List<Event> getEventEntitiesSortedByDuration() {
        return ofy().load().type(Event.class).order("duration").list();
    }

    public List<Event> getEventEntitiesSortedByCreatedTime() {
        return ofy().load().type(Event.class).order("createdDate").list();
    }

    public List<Event> getEventEntitiesSortedByStartTime() {

        return ofy().load().type(Event.class).order("startsAt").list();
    }

    public List<Event> getEventEntitiesSortedByNumberOfParticipants() {

        List<Event> list = getAllEntities(Event.class);
        list.sort(getNoOfParticipantsSorter());
        return list;
    }

    public List<Event> getEventEntityByEmail(String email) {
        return ofy().load().type(Event.class).filter("participantIds =", email).list();
    }

    public boolean checkForParticipants(List<String> participantIds) {
        Set<String> contactList =
                ofy().load().type(Contact.class).ids(participantIds).keySet();
        if (participantIds.size() == contactList.size()) {
            return true;
        } else {
            for (String s : contactList) {
                participantIds.remove(s);
            }
            throw new InputMismatchException("Participant does not exist" + participantIds);
        }
    }

    public Collection<Contact> getContactsByIds(List<String> participants) {
        return ofy().load().type(Contact.class).ids(participants).values();
    }

    public Contact getContactEntityByEmail(String email) {
        System.out.println("In getContactEntityByEmail");
        return ofy().load().type(Contact.class).filter("email =", email).first().now();
    }

    public void transactParticipants(String eventId,
                                     String calendarId,
                                     List<String> participants) {
        ofy().transact(() -> {
            Key key = Key.create(Calendar.class, calendarId);
            System.out.println(key);
            Event event = ofy().load().type(Event.class).parent(key).id(eventId).now();

            List<String> participantIds = event.getParticipantIds();
            for (String s : participants) {
                if (!participantIds.contains(s))
                    participantIds.add(s);
                else
                    throw new InputMismatchException("Participant " + s + " already exists");
            }
            event.setParticipantIds(participants);
            ofy().save().entity(event);
        });
    }

    private Comparator<Event> getNoOfParticipantsSorter() {
        return (o1, o2) -> Integer.compare(o2.getParticipantIds().size(), o1.getParticipantIds().size());
    }


}
