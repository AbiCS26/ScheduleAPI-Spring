package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.validation.PermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ObjectifyOperations objectifyOperations;
    private final ModelBuilder modelBuilder;
    private final PermissionChecker permissionChecker;

    @Autowired
    public ContactService(ObjectifyOperations objectifyOperations,
                          ModelBuilder modelBuilder,
                          PermissionChecker permissionChecker) {
        this.objectifyOperations = objectifyOperations;
        this.modelBuilder = modelBuilder;
        this.permissionChecker = permissionChecker;
    }


    public String storeGuestContact(Contact c) {
        Contact contact = modelBuilder.buildNewGuestContact(c);
        objectifyOperations.storeEntity(contact);
        return contact.getEmail();
    }

    public String storeOwnerContact(Contact c) {
        Contact contact = modelBuilder.buildNewOwnerContact(c);
        objectifyOperations.storeEntity(contact);
        return contact.getEmail();
    }

    public String modifyGuestContact(Contact c, String id) {
        permissionChecker.hasPermissionForContact(id);

        Contact con = modelBuilder.buildModifiedGuestContact(c, id);
        objectifyOperations.storeEntity(con);
        return con.getEmail();
    }

    public String modifyOwnerContact(Contact c, String id) {
        permissionChecker.hasPermissionForContact(id);

        Contact con = modelBuilder.buildModifiedOwnerContact(c, id);
        objectifyOperations.storeEntity(con);
        return con.getEmail();
    }
    
    public void deleteContactById(String id) {
        permissionChecker.hasPermissionForContact(id);

        objectifyOperations.deleteEntity(id, Contact.class);
    }

    public List<Contact> getAllContacts() {
        return objectifyOperations.getAllEntities(Contact.class);
    }

    public Contact getContactById(String id) {
        return objectifyOperations.getEntityById(id, Contact.class);
    }
}
