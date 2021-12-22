package com.scheduler.scheduleAPI.service;

import com.scheduler.scheduleAPI.model.Calendar;
import com.scheduler.scheduleAPI.validation.PermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    private final ObjectifyOperations objectifyOperations;
    private final ModelBuilder modelBuilder;
    private final PermissionChecker permissionChecker;

    @Autowired
    public CalendarService(ObjectifyOperations objectifyOperations,
                           ModelBuilder modelBuilder,
                           PermissionChecker permissionChecker) {
        this.objectifyOperations = objectifyOperations;
        this.modelBuilder = modelBuilder;
        this.permissionChecker = permissionChecker;
    }

    public List<Calendar> getAllCalendars() {
        return objectifyOperations.getAllEntities(Calendar.class);
    }

    public Calendar getCalendarById(String id) {
        return objectifyOperations.getEntityById(id, Calendar.class);
    }

    public String storeCalendar(Calendar c) {
        Calendar calendar = modelBuilder.buildNewCalendar(c);
        objectifyOperations.storeEntity(calendar);
        return calendar.getId();
    }

    public String modifyCalendar(Calendar c, String id) {
        permissionChecker.hasPermissionForCalendar(id);

        Calendar cal = modelBuilder.buildModifiedCalendar(c, id);
        objectifyOperations.storeEntity(cal);
        return cal.getId();
    }

    public void deleteCalendarById(String id) {
        permissionChecker.hasPermissionForCalendar(id);

        objectifyOperations.deleteEntity(id, Calendar.class);
    }

}
