package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Calendar;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelBuilder {
    private final ObjectifyOperations objectifyOperations;
    private final MyUser myUser;

    @Autowired
    public ModelBuilder(ObjectifyOperations objectifyOperations, MyUser myUser) {
        this.objectifyOperations = objectifyOperations;
        this.myUser = myUser;
    }

    public Calendar buildModifiedCalendar(Calendar calendar, String id) {
        String ownerId = objectifyOperations.getContactEntityByEmail(myUser.getCurrentUser().getUsername()).getId();

        return Calendar.newBuilder()
                .setOwnerId(ownerId)
                .setId(id)
                .setDateFormat(Calendar.DateFormat.valueOf(calendar.getDateFormat()))
                .setTimeFormat(Calendar.TimeFormat.valueOf(calendar.getTimeFormat()))
                .setTimezone(calendar.getTimezone())
                .build();
    }

    public Calendar buildNewCalendar(Calendar calendar) {
        String ownerId = objectifyOperations.getContactEntityByEmail(myUser.getCurrentUser().getUsername()).getId();

        return Calendar.newBuilder()
                .setOwnerId(ownerId)
                .setId()
                .setDateFormat(Calendar.DateFormat.valueOf(calendar.getDateFormat()))
                .setTimeFormat(Calendar.TimeFormat.valueOf(calendar.getTimeFormat()))
                .setTimezone(calendar.getTimezone())
                .build();
    }

    public Contact buildNewGuestContact(Contact contact) {
        String ownerId = objectifyOperations.getContactEntityByEmail(myUser.getCurrentUser().getUsername()).getId();

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

    public Contact buildNewOwnerContact(Contact contact) {
        return Contact.newBuilder()
                .setId()
                .setName(contact.getName())
                .setEmail(contact.getEmail())
                .setMobileNumber(contact.getMobileNumber())
                .setPassword(contact.getPassword())
                .setOwnerRole()
                .build();
    }

    public Contact buildModifiedGuestContact(Contact contact, String id) {
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

    public Contact buildModifiedOwnerContact(Contact contact, String id) {
        return Contact.newBuilder()
                .setId(id)
                .setEmail(contact.getEmail())
                .setName(contact.getName())
                .setMobileNumber(contact.getMobileNumber())
                .setPassword(contact.getPassword())
                .setOwnerRole()
                .build();
    }


    public Event buildNewEvent(Event e) {

        return Event.newBuilder()
                .setId()
                .setName(e.getName())
                .setStartsAt(e.getStartsAt())
                .setDuration(e.getDuration())
                .setParticipantIds(e.getParticipantIds())
                .setCalendar(e.getCalendarId())
                .setText(e.getDescription())
                .setCreatedBy(myUser.getCurrentUser().getUsername())
                .setCreatedDate()
                .build();
    }

    public Event buildModifiedEvent(String eventId, String calendarId, Event e) {
        long createdTime = objectifyOperations.getEventById(eventId, calendarId).getCreatedDate();

        return Event.newBuilder()
                .setName(e.getName())
                .setStartsAt(e.getStartsAt())
                .setDuration(e.getDuration())
                .setParticipantIds(e.getParticipantIds())
                .setId(eventId)
                .setCalendar(calendarId)
                .setCreatedBy(myUser.getCurrentUser().getUsername())
                .setCreatedDate(createdTime)
                .setModifiedDate()
                .build();
    }

}
