package com.scheduler.scheduleAPI.model;


import com.google.cloud.datastore.Entity;
import com.googlecode.objectify.annotation.Id;
import inputs.IdGenerator;
import inputs.Validation;

import java.util.Calendar;
import java.util.List;

@com.googlecode.objectify.annotation.Entity
public class Event {
    private String name;
    @Id
    private String id;
    private List<Contact> participants;
    private List<String> participantIds;
    private long startsAt;
    private long duration;
    private long createdDate;
    private long modifiedDate;

    public Event(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.startsAt = builder.startsAt;
        this.duration = builder.duration;
        this.createdDate = builder.createdDate;
        this.modifiedDate = builder.modifiedDate;

        if (builder.participants == null)
            this.participantIds = builder.participantIds;
        else
            this.participants = builder.participants;
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
        this.participants = participants;
        return this;
    }

    public Event convertEntityToEvent(Entity entity, List<Contact> participants) {

        this.name = entity.getString("name");
        this.id = entity.getKey().getName();
        this.startsAt = entity.getLong("startsAt");
        this.duration = (int) entity.getLong("duration");
        this.createdDate = entity.getLong("createdDate");
        this.modifiedDate = entity.getLong("modifiedDate");
        this.participants = participants;
        return this;
    }


    public static class Builder {
        private String name;
        private String id;

        private List<Contact> participants;
        private long startsAt;
        private long duration;
        private long createdDate;
        private long modifiedDate;
        private List<String> participantIds;

        public Builder setParticipantIds(List<String> participantIds) {
            Validation.checkParticipantIds(participantIds);
            this.participantIds = participantIds;
            return this;
        }

        public Builder setName(String name) {
            Validation.checkString(name, "Event Name");
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
            Validation.checkNumber(eventTime, "Start Time");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(eventTime);
            this.startsAt = calendar.getTimeInMillis();
            return this;
        }

        public Builder setDuration(long duration) {
            Validation.checkNumber(duration, "Duration");
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

        public Builder setParticipants(List<Contact> participants) {
            Validation.checkParticipantList(participants);

            this.participants = participants;
            return this;
        }

        public Event build() {
            return new Event(this);
        }

    }
}
