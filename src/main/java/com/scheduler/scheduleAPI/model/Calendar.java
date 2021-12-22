package com.scheduler.scheduleAPI.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.scheduler.scheduleAPI.inputs.IdGenerator;

import java.time.ZoneId;

@Entity
public class Calendar {
    @Id
    private String id;
    private String timezone;
    private String timeFormat;
    private String dateFormat;
    private String ownerId;

    public Calendar(Builder builder) {
        this.id = builder.id;
        this.timezone = builder.timezone;
        this.dateFormat = builder.dateFormat.getFormat();
        this.timeFormat = builder.timeFormat.getFormat();
        this.ownerId = builder.ownerId;
    }

    public enum DateFormat {
        DATE_MONTH_YEAR("dd/MM/yyyy"),
        MONTH_DATE_YEAR("MM/dd/yyyy");

        private final String format;

        DateFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    public enum TimeFormat {
        TWENTY_FOUR_HOURS("HH:mm"),
        TWELVE_HOURS("hh:mm a");

        private final String format;

        TimeFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    public Calendar() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getId() {
        return id;
    }

    public String getTimezone() {
        return timezone;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public static class Builder {
        private String id;
        private String timezone;
        private TimeFormat timeFormat;
        private DateFormat dateFormat;
        private String ownerId;

        public Builder setOwnerId(String ownerId) {
            this.ownerId = ownerId;
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

        public Builder setTimezone(String timezone) {
            ZoneId.of(timezone);
            this.timezone = timezone;
            return this;
        }

        public Builder setTimeFormat(TimeFormat timeFormat) {
            this.timeFormat = timeFormat;
            return this;
        }

        public Builder setDateFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        public Calendar build() {
            return new Calendar(this);
        }

    }
}

