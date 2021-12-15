package com.scheduler.scheduleAPI.service.datastoreoperations;

import com.scheduler.scheduleAPI.model.Calendar;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CalendarOperations {

    public static List<Calendar> getAllCalendarEntities() {
        return ofy().load().type(Calendar.class).list();
    }

    public static Calendar getCalendarEntityById(String id) {
        return ofy().load().type(Calendar.class).id(id).now();
    }

    public static String storeCalendar(Calendar calendar) {
        ofy().save().entity(calendar).now();
        return calendar.getId();
    }

    public static void deleteCalendarById(String id) {
        ofy().delete().type(Calendar.class).id(id).now();
    }
}
