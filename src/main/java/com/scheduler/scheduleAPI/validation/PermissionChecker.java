package com.scheduler.scheduleAPI.validation;

import com.scheduler.scheduleAPI.exception.PermissionException;
import com.scheduler.scheduleAPI.model.Calendar;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import com.scheduler.scheduleAPI.service.MyUser;
import com.scheduler.scheduleAPI.service.ObjectifyOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

@Service
public class PermissionChecker {

    private final MyUser myUser;
    private final ObjectifyOperations objectifyOperations;

    @Autowired
    public PermissionChecker(MyUser myUser, ObjectifyOperations objectifyOperations) {
        this.myUser = myUser;
        this.objectifyOperations = objectifyOperations;
    }

    public boolean hasPermissionForEvent(String eventId, String calendarId) {
        UserDetails userDetails = myUser.getCurrentUser();
        Event event = objectifyOperations.getEventById(eventId, calendarId);

        if (event == null)
            throw new InputMismatchException("Event with ID: " + eventId + " is not present");

        if (!event.getCreatedBy().equals(userDetails.getUsername()))
            throw new PermissionException("User is not allowed to perform actions on this event");

        return true;
    }

    public boolean hasPermissionForContact(String id) {
        UserDetails userDetails = myUser.getCurrentUser();
        Contact ownerContact = objectifyOperations.getContactEntityByEmail(userDetails.getUsername());

        Contact contact = objectifyOperations.getEntityById(id, Contact.class);
        if (contact == null)
            throw new InputMismatchException("Contact with ID: " + id + " is not present");

        if (!contact.getOwnerId().equals(ownerContact.getId()))
            throw new PermissionException("User is not allowed to perform actions on this contact");

        return true;
    }

    public boolean hasPermissionForCalendar(String id) {
        UserDetails userDetails = myUser.getCurrentUser();
        Contact ownerContact = objectifyOperations.getContactEntityByEmail(userDetails.getUsername());
        Calendar calendar = objectifyOperations.getEntityById(id, Calendar.class);

        if (calendar == null)
            throw new InputMismatchException("Calendar with ID: " + id + " is not present");

        if (!calendar.getOwnerId().equals(ownerContact.getId()))
            throw new PermissionException("User is not allowed to perform actions on this Calendar");

        return true;
    }
}
