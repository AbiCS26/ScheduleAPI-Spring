package com.scheduler.scheduleAPI.service;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import com.scheduler.scheduleAPI.storage.DataStorage;

import java.util.Comparator;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class DatastoreOperations {
    private static final Datastore datastore = DataStorage.getInstance();


    public static String storeEvent(Event event) {
        ofy().save().entity(event).now();
        return event.getId();
    }


    public static String storeContact(Contact contact) {
        ofy().save().entity(contact).now();
        return contact.getId();
    }

    public static Event getEventEntityById(String id) {
        return ofy().load().type(Event.class).id(id).now();
    }

    public static List<Event> getAllEventEntities() {
        return ofy().load().type(Event.class).list();
    }

    public static Contact getContactsEntity(String id) {
        return ofy().load().type(Contact.class).id(id).now();
    }

    public static void deleteEventById(String id) {

        ofy().delete().type(Event.class).id(id).now();
    }

    public static List<Event> getEntitiesByTimeRange(String start, String end) {
        return ofy().load().type(Event.class)
                .filter("startsAt >=", Long.parseLong(start))
                .filter("startsAt <=", Long.parseLong(end))
                .list();
    }

    public static List<Event> getEntitiesSortedById() {
        return ofy().load().type(Event.class).orderKey(true).list();
    }

    public static List<Event> getEntitiesSortedByDuration() {
        return ofy().load().type(Event.class).order("duration").list();
    }

    public static List<Event> getEntitiesSortedByCreatedTime() {
        return ofy().load().type(Event.class).order("createdDate").list();
    }

    public static List<Event> getEntitiesSortedByStartTime() {

        return ofy().load().type(Event.class).order("startsAt").list();
    }

    public static List<Event> getEntitiesSortedByNumberOfParticipants() {

        List<Event> list = getAllEventEntities();
        list.sort(getNoOfParticipantsSorter());
        return list;
    }

    public static List<Contact> getEntityByEmail(String email) {
        return ofy().load().type(Contact.class).filter("email =", email).list();
    }


    public static void deleteContactById(String id) {
        ofy().delete().type(Contact.class).id(id).now();
    }

    public static List<Contact> getAllContactEntities() {
        return ofy().load().type(Contact.class).list();
    }

    public static Contact getContactEntityById(String id) {
        System.out.println("In getContactEntityById");
        return ofy().load().type(Contact.class).id(id).now();
    }


    private static Comparator<Event> getNoOfParticipantsSorter() {
        return (o1, o2) -> Integer.compare(o2.getParticipantIds().size(), o1.getParticipantIds().size());
    }

    public static Entity getContactEntityByIdQuery(String id) {
        Key key = datastore.newKeyFactory().setKind("Contact").newKey(id);
        Entity entity = datastore.get(key);
        return entity;
    }
}
