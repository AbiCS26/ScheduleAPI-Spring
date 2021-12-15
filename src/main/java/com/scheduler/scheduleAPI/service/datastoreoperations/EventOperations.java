package com.scheduler.scheduleAPI.service.datastoreoperations;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;

import java.util.Comparator;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class EventOperations {
    public static String storeEvent(Event event) {
        ofy().save().entity(event).now();
        return event.getId();
    }


    public static Event getEventEntityById(String id) {
        return ofy().load().type(Event.class).id(id).now();
    }

    public static List<Event> getAllEventEntities() {
        return ofy().load().type(Event.class).list();
    }


    public static void deleteEventById(String id) {

        ofy().delete().type(Event.class).id(id).now();
    }

    public static List<Event> getEventEntitiesByTimeRange(String start, String end) {
        return ofy().load().type(Event.class)
                .filter("startsAt >=", Long.parseLong(start))
                .filter("startsAt <=", Long.parseLong(end))
                .list();
    }

    public static List<Event> getEventEntitiesSortedById() {
        return ofy().load().type(Event.class).orderKey(true).list();
    }

    public static List<Event> getEventEntitiesSortedByDuration() {
        return ofy().load().type(Event.class).order("duration").list();
    }

    public static List<Event> getEventEntitiesSortedByCreatedTime() {
        return ofy().load().type(Event.class).order("createdDate").list();
    }

    public static List<Event> getEventEntitiesSortedByStartTime() {

        return ofy().load().type(Event.class).order("startsAt").list();
    }

    public static List<Event> getEventEntitiesSortedByNumberOfParticipants() {

        List<Event> list = getAllEventEntities();
        list.sort(getNoOfParticipantsSorter());
        return list;
    }

    public static List<Contact> getEventEntityByEmail(String email) {
        return ofy().load().type(Contact.class).filter("email =", email).list();
    }


    private static Comparator<Event> getNoOfParticipantsSorter() {
        return (o1, o2) -> Integer.compare(o2.getParticipantIds().size(), o1.getParticipantIds().size());
    }


}
