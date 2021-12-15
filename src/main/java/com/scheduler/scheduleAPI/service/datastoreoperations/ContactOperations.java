package com.scheduler.scheduleAPI.service.datastoreoperations;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.storage.DataStorage;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ContactOperations {
    private static final Datastore datastore = DataStorage.getInstance();


    public static String storeContact(Contact contact) {
        ofy().save().entity(contact).now();
        return contact.getId();
    }

    public static Contact getContactsEntity(String id) {
        return ofy().load().type(Contact.class).id(id).now();
    }

    public static void deleteContactById(String id) {
        ofy().delete().type(Contact.class).id(id).now();
    }

    public static List<Contact> getAllContactEntities() {

        return ofy().load().type(Contact.class).list();
    }

    public static Contact getContactEntityById(String id) {
        return ofy().load().type(Contact.class).id(id).now();
    }

    public static Entity getContactEntityByIdQuery(String id) {
        Key key = datastore.newKeyFactory().setKind("Contact").newKey(id);
        Entity entity = datastore.get(key);
        return entity;
    }
}
