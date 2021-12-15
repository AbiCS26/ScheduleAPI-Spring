package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestContactService {

    public String storeGuestContact(Contact contact) {
        return DatastoreOperations.storeContact(buildNewGuestContact(contact));
    }

    public Object modifyGuestContact(Contact contact, String id) {
        return DatastoreOperations.storeContact(buildModifiedGuestContact(contact, id));
    }

    public void deleteGuestContact(String id) {
        DatastoreOperations.deleteContactById(id);
    }

    public List<Contact> getAllContacts() {
        return DatastoreOperations.getAllContactEntities();
    }

    public Contact getContactById(String id) {
        return DatastoreOperations.getContactEntityById(id);
    }


    private Contact buildNewGuestContact(Contact contact) {
        return Contact.newBuilder()
                .setId()
                .setName(contact.getName())
                .setEmail(contact.getEmail())
                .setMobileNumber(contact.getMobileNumber())
                .setPassword(contact.getPassword())
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
}
