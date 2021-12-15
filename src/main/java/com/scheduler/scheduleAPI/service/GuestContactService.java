package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.service.datastoreoperations.ContactOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestContactService {

    public String storeGuestContact(Contact contact) {
        return ContactOperations.storeContact(buildNewGuestContact(contact));
    }

    public String modifyGuestContact(Contact contact, String id) {
        return ContactOperations.storeContact(buildModifiedGuestContact(contact, id));
    }

    public void deleteGuestContactById(String id) {
        ContactOperations.deleteContactById(id);
    }

    public List<Contact> getAllContacts() {
        return ContactOperations.getAllContactEntities();
    }

    public Contact getContactById(String id) {
        return ContactOperations.getContactEntityById(id);
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
