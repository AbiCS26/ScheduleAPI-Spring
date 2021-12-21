package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private final ObjectifyOperations objectifyOperations;

    public CalendarService(ObjectifyOperations objectifyOperations) {
        this.objectifyOperations = objectifyOperations;
    }

    public List<Calendar> getAllCalendars() {
        return objectifyOperations.getAllEntities(Calendar.class);
    }

    public Calendar getCalendarById(String id) {
        return objectifyOperations.getEntityById(id, Calendar.class);
    }

    public String storeCalendar(Calendar c) {
        Calendar calendar = buildNewCalendar(c);
        objectifyOperations.storeEntity(calendar);
        return calendar.getId();
    }

    public String modifyCalendar(Calendar c, String id) {
        Calendar calendar = buildModifiedCalendar(c, id);
        objectifyOperations.storeEntity(calendar);
        return calendar.getId();
    }

    public void deleteCalendarById(String id) {
        objectifyOperations.deleteEntity(id, Calendar.class);
    }


    private Calendar buildModifiedCalendar(Calendar calendar, String id) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Calendar.newBuilder()
                .setOwnerId(userDetail.getUsername())
                .setId(id)
                .setDateFormat(Calendar.DateFormat.valueOf(calendar.getDateFormat()))
                .setTimeFormat(Calendar.TimeFormat.valueOf(calendar.getTimeFormat()))
                .setTimezone(calendar.getTimezone())
                .build();
    }

    private Calendar buildNewCalendar(Calendar calendar) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Calendar.newBuilder()
                .setOwnerId(userDetail.getUsername())
                .setId()
                .setDateFormat(Calendar.DateFormat.valueOf(calendar.getDateFormat()))
                .setTimeFormat(Calendar.TimeFormat.valueOf(calendar.getTimeFormat()))
                .setTimezone(calendar.getTimezone())
                .build();
    }

}
