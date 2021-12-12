package com.scheduler.scheduleAPI.service;

import com.google.cloud.datastore.Entity;
import com.scheduler.scheduleAPI.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    public String storeGuestContact(Contact contact) {
        return DatastoreOperations.storeContact(buildNewGuestContact(contact));
    }

    public Object modifyGuestContact(Contact contact, String id) {
        return DatastoreOperations.storeContact(buildModifiedGuestContact(contact, id));
    }

    private Contact buildNewGuestContact(Contact contact) {
        return Contact.newBuilder()
                .setId()
                .setName(contact.getName())
                .setEmail(contact.getEmail())
                .setMobileNumber(contact.getMobileNumber())
                .setGuestRole()
                .build();
    }

    private Contact buildModifiedGuestContact(Contact contact, String id) {
        return Contact.newBuilder()
                .setId(id)
                .setName(contact.getName())
                .setEmail(contact.getEmail())
                .setMobileNumber(contact.getMobileNumber())
                .setGuestRole()
                .build();
    }

    public void deleteGuestContact(String id) {
        DatastoreOperations.deleteContactById(id);
    }

    public List<Contact> getAllContacts() {
        List<Entity> entityList = DatastoreOperations.getAllContactEntities();
        List<Contact> list = new ArrayList<>();

        for (Entity entity : entityList) {
            list.add(new Contact().convertEntityToContact(entity));
        }
        return list;
    }

    public Contact getContactById(String id) {
        Entity entity = DatastoreOperations.getContactEntityById(id);
        return new Contact().convertEntityToContact(entity);
    }
}
