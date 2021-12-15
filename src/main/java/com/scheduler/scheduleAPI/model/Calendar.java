package com.scheduler.scheduleAPI.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Calendar {
    @Id
    private String id;
    private String timezone;
    private TimeFormat timeFormat;
    private DateFormat dateFormat;


    private enum DateFormat {
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

    private enum TimeFormat {
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

    public Calendar(Builder builder) {
        this.id = builder.id;
        this.timezone = builder.timezone;
        this.dateFormat = builder.dateFormat;
        this.timeFormat = builder.timeFormat;
    }

    public Calendar() {
    }

    public String getId() {
        return id;
    }

    public String getTimezone() {
        return timezone;
    }


    public class Builder {
        private String id;
        private String timezone;
        private TimeFormat timeFormat;
        private DateFormat dateFormat;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTimezone(String timezone) {
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

