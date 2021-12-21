package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private final ObjectifyOperations objectifyOperations;
    @Autowired
    private final MyUser myUser;

    public ContactService(ObjectifyOperations objectifyOperations, MyUser myUser) {
        this.objectifyOperations = objectifyOperations;
        this.myUser = myUser;
    }


    public String storeGuestContact(Contact c) {
        Contact contact = buildNewGuestContact(c);
        objectifyOperations.storeEntity(contact);
        return contact.getEmail();
    }

    public String storeOwnerContact(Contact c) {
        Contact contact = buildNewOwnerContact(c);
        objectifyOperations.storeEntity(contact);
        return contact.getEmail();
    }

    public String modifyGuestContact(Contact c, String id) {
        Contact contact = buildModifiedGuestContact(c, id);
        objectifyOperations.storeEntity(contact);
        return contact.getEmail();
    }

    public String modifyOwnerContact(Contact c, String id) {
        Contact contact = buildModifiedOwnerContact(c, id);
        objectifyOperations.storeEntity(contact);
        return contact.getEmail();
    }


    public void deleteGuestContactById(String id) {
        objectifyOperations.deleteEntity(id, Contact.class);
    }

    public List<Contact> getAllContacts() {
        return objectifyOperations.getAllEntities(Contact.class);
    }

    public Contact getContactById(String id) {
        return objectifyOperations.getEntityById(id, Contact.class);
    }

    private Contact buildNewGuestContact(Contact contact) {
        String ownerId = objectifyOperations.getContactEntityByEmail(myUser.getCurrentUser().getUsername()).getId();
        System.out.println(ownerId);
        return Contact.newBuilder()
                .setId()
                .setName(contact.getName())
                .setEmail(contact.getEmail())
                .setMobileNumber(contact.getMobileNumber())
                .setPassword(contact.getPassword())
                .setOwnerId(ownerId)
                .setGuestRole()
                .build();
    }

    private Contact buildNewOwnerContact(Contact contact) {
        return Contact.newBuilder()
                .setId()
                .setName(contact.getName())
                .setEmail(contact.getEmail())
                .setMobileNumber(contact.getMobileNumber())
                .setPassword(contact.getPassword())
                .setOwnerRole()
                .build();
    }

    private Contact buildModifiedGuestContact(Contact contact, String id) {
        String ownerId = objectifyOperations.getContactEntityByEmail(myUser.getCurrentUser().getUsername()).getId();
        return Contact.newBuilder()
                .setId(id)
                .setEmail(contact.getEmail())
                .setName(contact.getName())
                .setMobileNumber(contact.getMobileNumber())
                .setPassword(contact.getPassword())
                .setOwnerId(ownerId)
                .setGuestRole()
                .build();
    }

    private Contact buildModifiedOwnerContact(Contact contact, String id) {
        return Contact.newBuilder()
                .setId(id)
                .setEmail(contact.getEmail())
                .setName(contact.getName())
                .setMobileNumber(contact.getMobileNumber())
                .setPassword(contact.getPassword())
                .setOwnerRole()
                .build();
    }
}
