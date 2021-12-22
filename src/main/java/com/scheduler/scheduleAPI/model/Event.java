package com.scheduler.scheduleAPI.model;


import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;
import com.scheduler.scheduleAPI.inputs.IdGenerator;
import com.scheduler.scheduleAPI.service.ObjectifyOperations;
import com.scheduler.scheduleAPI.validation.Validator;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;


@Entity
public class Event {
    @Id
    private String id;
    private String createdBy;
    private String name;

    private List<Contact> participants;
    private List<String> participantIds;
    @Index
    private long startsAt;
    @Index
    private long duration;
    @Index
    private long createdDate;
    private long modifiedDate;
    @Ignore
    private String calendarId;
    private Text description;


    public String getCalendarId() {
        return calendarId;
    }

    @Parent
    private Key<com.scheduler.scheduleAPI.model.Calendar> calendar;


    public Event(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.startsAt = builder.startsAt;
        this.duration = builder.duration;
        this.createdDate = builder.createdDate;
        this.modifiedDate = builder.modifiedDate;
        this.participantIds = builder.participantIds;
        this.calendar = builder.calendar;
        this.description = builder.text;
        this.createdBy = builder.createdBy;
    }

    public Event() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public long getStartsAt() {
        return startsAt;
    }

    public long getDuration() {
        return duration;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public List<Contact> getParticipants() {
        return participants;
    }

    public List<String> getParticipantIds() {
        return participantIds;
    }

    public Event setParticipants(List<Contact> participants) {
        Validator.checkParticipantList(participants);

        this.participants = participants;
        return this;
    }

    public Text getDescription() {
        return description;
    }

    public Event setParticipantIds(List<String> participantIds) {
        Validator.checkParticipantIds(participantIds);
        new ObjectifyOperations().checkForParticipants(participantIds);

        this.participantIds = participantIds;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    @OnSave
    void onSaveMethod() {
        Logger logger = Logger.getLogger("EventLogger");
        logger.info("Event saved Successfully");
    }

    @OnLoad
    void onLoadMethod() {
        Logger logger = Logger.getLogger("EventLogger");
        logger.info("Event loaded Successfully");
    }

    public static class Builder {
        private String name;
        private String id;
        private String createdBy;

        private long startsAt;
        private long duration;
        private long createdDate;
        private long modifiedDate;
        private List<String> participantIds;
        private Key<com.scheduler.scheduleAPI.model.Calendar> calendar;
        private Text text;

        public Builder setText(Text text) {
            this.text = text;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setCalendar(String calendar) {
            Validator.checkString(calendar, "Calendar ID");
            this.calendar = Key.create(com.scheduler.scheduleAPI.model.Calendar.class, calendar);
            return this;
        }

        public Builder setParticipantIds(List<String> participantIds) {
            Validator.checkParticipantIds(participantIds);
            new ObjectifyOperations().checkForParticipants(participantIds);
            this.participantIds = participantIds;
            return this;
        }

        public Builder setName(String name) {
            Validator.checkString(name, "Event Name");
            this.name = name;
            return this;
        }

        public Builder setId() {
            this.id = IdGenerator.generateID();
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setStartsAt(long eventTime) {
            Validator.checkNumber(eventTime, "Start Time");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(eventTime);
            this.startsAt = calendar.getTimeInMillis();
            return this;
        }

        public Builder setDuration(long duration) {
            Validator.checkNumber(duration, "Duration");
            this.duration = duration;
            return this;
        }

        public Builder setCreatedDate() {
            this.createdDate = System.currentTimeMillis();
            return this;
        }

        public Builder setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setModifiedDate() {
            this.modifiedDate = System.currentTimeMillis();
            return this;
        }

        public Event build() {
            return new Event(this);
        }

    }
}
