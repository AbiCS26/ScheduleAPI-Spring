package com.scheduler.scheduleAPI.service;

import com.google.api.client.util.Lists;
import com.google.cloud.datastore.*;
import com.googlecode.objectify.ObjectifyService;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import com.scheduler.scheduleAPI.storage.DataStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

public class DatastoreOperations {
    private static final Datastore datastore = DataStorage.getInstance();
    private static final String KIND_NAME = "Event";

    public static String storeEvent(Event event) {
        ObjectifyService.ofy().save().entity(event).now();
        return event.getId();
//        KeyFactory keyFactory = datastore.newKeyFactory().setKind(KIND_NAME);
//        Key key = keyFactory.newKey(event.getId());
//
//        Entity events = Entity.newBuilder(key)
//                .set("name", event.getName())
//                .set("participants", convertToValueList(event.getParticipantIds()))
//                .set("startsAt", event.getStartsAt())
//                .set("createdDate", event.getCreatedDate())
//                .set("duration", event.getDuration())
//                .set("modifiedDate", event.getModifiedDate())
//                .build();
//
//        datastore.put(events);
//        return key.getName();
    }

//    public static String modifyEvent(Event event) {
//        getEventEntityById(event.getId()).getLong("createdDate");

//        KeyFactory keyFactory = datastore.newKeyFactory().setKind(KIND_NAME);
//        Key key = keyFactory.newKey(event.getId());
//
//
//        Entity events = Entity.newBuilder(key)
//                .set("name", event.getName())
//                .set("participants", convertToValueList(event.getParticipantIds()))
//                .set("startsAt", event.getStartsAt())
//                .set("createdDate", createdDate)
//                .set("duration", event.getDuration())
//                .set("modifiedDate", event.getModifiedDate())
//                .build();
//
//        datastore.put(events);
//        return key.getName();
//}

    public static String storeContact(Contact contact) {
        ObjectifyService.ofy().save().entity(contact).now();
        return contact.getId();
//        KeyFactory keyFactory = datastore.newKeyFactory()
//                .setKind("Contacts");
//        Key key = keyFactory.newKey(contact.getId());
//
//        Entity contacts = Entity.newBuilder(key)
//                .set("name", contact.getName())
//                .set("email", contact.getEmail())
//                .set("mobileNumber", contact.getMobileNumber())
//                .set("role", contact.getRole())
//                .build();
//
//        datastore.put(contacts);
//        return key.getName();
    }

    public static Event getEventEntityById(String id) {
        return ObjectifyService.ofy().load().type(Event.class).id(id).now();

//        Key key = datastore.newKeyFactory().setKind(KIND_NAME).newKey(id);
//        Entity entity = datastore.get(key);
//        if (entity == null)
//            throw new InputMismatchException("Enter valid ID");
//        return entity;
    }

    public static List<Entity> getAllEventEntities() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(KIND_NAME)
                .build();

        return Lists.newArrayList(datastore.run(query));
    }

    public static Contact getContactsEntity(String id) {
        return ObjectifyService.ofy().load().type(Contact.class).id(id).now();

//        Key key = datastore.newKeyFactory().setKind("Contact").newKey(id);
//        return datastore.get(key);
    }

    public static void deleteEventById(String id) {
        Key key = datastore.newKeyFactory().setKind(KIND_NAME).newKey(id);
        Entity entity = datastore.get(key);
        if (entity == null)
            throw new InputMismatchException("Enter valid ID");

        datastore.delete(key);
    }

    public static List<Entity> getEntitiesByTimeRange(String start, String end) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(KIND_NAME)
                .setFilter(StructuredQuery.CompositeFilter
                        .and((StructuredQuery.PropertyFilter.ge("startsAt", start)),
                                (StructuredQuery.PropertyFilter.le("startsAt", end))))
                .build();
        QueryResults<Entity> events = datastore.run(query);

        return Lists.newArrayList(events);
    }

    public static List<Entity> getEntitiesSortedById() {
        List<Entity> list = getAllEventEntities();
        list.sort(getKeysSorter());
        return list;
    }

    public static List<Entity> getEntitiesSortedByDuration() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(KIND_NAME)
                .setOrderBy(StructuredQuery.OrderBy.desc("duration"))
                .build();

        QueryResults<Entity> events = datastore.run(query);
        return Lists.newArrayList(events);
    }

    public static List<Entity> getEntitiesSortedByCreatedTime() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(KIND_NAME)
                .setOrderBy(StructuredQuery.OrderBy.desc("createdDate"))
                .build();

        QueryResults<Entity> events = datastore.run(query);
        return Lists.newArrayList(events);
    }

    public static List<Entity> getEntitiesSortedByStartTime() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(KIND_NAME)
                .setOrderBy(StructuredQuery.OrderBy.desc("startsAt"))
                .build();

        QueryResults<Entity> events = datastore.run(query);
        return Lists.newArrayList(events);
    }

    public static List<Entity> getEntitiesSortedByNumberOfParticipants() {

        List<Entity> list = getAllEventEntities();
        list.sort(getNoOfParticipantsSorter());
        return list;
    }

    public static List<Entity> getEntityByEmail(String email) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Contacts")
                .setFilter(StructuredQuery.PropertyFilter.eq("email", email))
                .build();
        QueryResults<Entity> events = datastore.run(query);

        return Lists.newArrayList(events);
    }

    private static Comparator<Entity> getKeysSorter() {
        return Comparator.comparing(o -> String.valueOf(o.getKey().getName()));
    }

    private static Comparator<Entity> getNoOfParticipantsSorter() {
        return (o1, o2) -> Integer.compare(o2.getList("participants").size(), o1.getList("participants").size());
    }

    private static List<Value<String>> convertToValueList(List<String> list) {
        List<Value<String>> result = new ArrayList<>();
        for (String s : list) {
            result.add(StringValue.of(s));
        }
        return result;
    }

    public static void deleteContactById(String id) {
        Key key = datastore.newKeyFactory().setKind("Contacts").newKey(id);
        Entity entity = datastore.get(key);
        if (entity == null)
            throw new InputMismatchException("Enter valid ID");

        datastore.delete(key);
    }

    public static List<Entity> getAllContactEntities() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Contacts")
                .build();

        return Lists.newArrayList(datastore.run(query));
    }

    public static Entity getContactEntityById(String id) {
        Key key = datastore.newKeyFactory().setKind("Contacts").newKey(id);
        Entity entity = datastore.get(key);
        if (entity == null)
            throw new InputMismatchException("Enter valid ID");
        return entity;
    }
}
