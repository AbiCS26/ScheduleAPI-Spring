package com.scheduler.scheduleAPI.controller.event;

import com.scheduler.scheduleAPI.model.Event;
import com.scheduler.scheduleAPI.response.ResponseHandler;
import com.scheduler.scheduleAPI.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService eventsService;
    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getEvents() {
        return responseHandler.generateResponse(
                "All Events Received",
                HttpStatus.OK,
                eventsService.getAllEvents()
        );
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getEvent(@PathVariable String id) {
        return responseHandler.generateResponse(
                "Event Retrieved by ID",
                HttpStatus.OK,
                eventsService.getEventById(id)
        );
    }

    @GetMapping(value = "/email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getEventByEmail(@PathVariable String email) {
        return responseHandler.generateResponse(
                "Event Retrieved by email",
                HttpStatus.OK,
                eventsService.getEventByEmail(email)
        );
    }

    @GetMapping(value = "/timeRange/{start}/{end}")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getEventByTime(@PathVariable("start") String start, @PathVariable("end") String end) {
        return responseHandler.generateResponse(
                "Event Retrieved by email",
                HttpStatus.OK,
                eventsService.getEventByTimeRange(start, end)
        );
    }

    @GetMapping(value = "/sortById")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity sortEventById() {
        return responseHandler.generateResponse(
                "Event Sorted by ID",
                HttpStatus.OK,
                eventsService.getEventsSortedById()
        );
    }

    @GetMapping(value = "/sortByDuration")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity sortEventByDuration() {
        return responseHandler.generateResponse(
                "Event Sorted by Duration",
                HttpStatus.OK,
                eventsService.getEventsSortedByDuration()
        );
    }

    @GetMapping(value = "/sortByCreatedDate")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity sortEventByCreatedTime() {
        return responseHandler.generateResponse(
                "Event Sorted by Created Time",
                HttpStatus.OK,
                eventsService.getEventsSortedByCreatedTime()
        );
    }

    @GetMapping(value = "/sortByStartTime")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity sortEventByStartTime() {
        return responseHandler.generateResponse(
                "Event Sorted by Start Time",
                HttpStatus.OK,
                eventsService.getEventsSortedByStartTime()
        );
    }

    @GetMapping(value = "/sortByNumberOfParticipants")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity sortEventByNumberOfParticipants() {
        return responseHandler.generateResponse(
                "Event Sorted by number of participants",
                HttpStatus.OK,
                eventsService.getEventsSortedByNumberOfParticipants()
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('event:write')")
    public ResponseEntity postEvent(@RequestBody Event event) {
        return responseHandler.generateResponse(
                "Event stored successfully",
                HttpStatus.CREATED,
                eventsService.storeEvent(event)
        );
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('event:write')")
    public ResponseEntity putEvent(@PathVariable String id, @RequestBody Event event) {
        return responseHandler.generateResponse(
                "Event Modified successfully",
                HttpStatus.OK,
                eventsService.modifyEvent(id, event)
        );
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('event:write')")
    public ResponseEntity deleteEvent(@PathVariable String id) {
        eventsService.deleteEvent(id);

        return responseHandler.generateResponse(
                "Event Deleted successfully",
                HttpStatus.OK
        );
    }

}
