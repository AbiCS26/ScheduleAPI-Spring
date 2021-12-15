package com.scheduler.scheduleAPI.controller.calendar;

import com.scheduler.scheduleAPI.model.Calendar;
import com.scheduler.scheduleAPI.response.ResponseHandler;
import com.scheduler.scheduleAPI.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;
    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getAllCalendars() {
        return responseHandler.generateResponse(
                "All Calendars Received",
                HttpStatus.OK,
                calendarService.getAllCalendars()
        );
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getCalendarById(@PathVariable String id) {
        return responseHandler.generateResponse(
                "Calendar Received",
                HttpStatus.OK,
                calendarService.getCalendarById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('calendar:write')")
    public ResponseEntity postCalendar(@RequestBody Calendar calendar) {
        return responseHandler.generateResponse(
                "Calendar stored successfully",
                HttpStatus.CREATED,
                calendarService.storeCalendar(calendar)
        );
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('calendar:write')")
    public ResponseEntity putCalendar(@PathVariable String id, @RequestBody Calendar calendar) {
        return responseHandler.generateResponse(
                "Calendar modified successfully",
                HttpStatus.OK,
                calendarService.modifyCalendar(calendar, id)
        );
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('calendar:write')")
    public ResponseEntity deleteCalendar(@PathVariable String id) {
        calendarService.deleteCalendarById(id);

        return responseHandler.generateResponse(
                "Calendar deleted successfully",
                HttpStatus.OK
        );
    }

}
