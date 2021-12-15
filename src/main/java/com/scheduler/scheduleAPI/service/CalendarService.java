package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Calendar;
import com.scheduler.scheduleAPI.service.datastoreoperations.CalendarOperations;

import java.util.List;

public class CalendarService {
    public List<Calendar> getAllCalendars() {
        return CalendarOperations.getAllCalendarEntities();
    }

    public Calendar getCalendarById(String id) {
        return CalendarOperations.getCalendarEntityById(id);
    }

    public String storeCalendar(Calendar calendar) {
        return CalendarOperations.storeCalendar(buildNewCalendar(calendar));
    }

    public String modifyCalendar(Calendar calendar, String id) {
        return CalendarOperations.storeCalendar(buildModifiedCalendar(calendar, id));
    }

    public void deleteCalendarById(String id) {
        CalendarOperations.deleteCalendarById(id);
    }


    private Calendar buildModifiedCalendar(Calendar calendar, String id) {
        return Calendar.newBuilder()
                .setId(id)
                .setDateFormat(Calendar.DateFormat.valueOf(calendar.getDateFormat()))
                .setTimeFormat(Calendar.TimeFormat.valueOf(calendar.getTimeFormat()))
                .setTimezone(calendar.getTimezone())
                .build();
    }

    private Calendar buildNewCalendar(Calendar calendar) {
        return Calendar.newBuilder()
                .setId()
                .setDateFormat(Calendar.DateFormat.valueOf(calendar.getDateFormat()))
                .setTimeFormat(Calendar.TimeFormat.valueOf(calendar.getTimeFormat()))
                .setTimezone(calendar.getTimezone())
                .build();
    }

}
