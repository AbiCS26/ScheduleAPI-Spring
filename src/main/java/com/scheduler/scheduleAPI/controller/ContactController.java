package com.scheduler.scheduleAPI.controller;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.response.ResponseHandler;
import com.scheduler.scheduleAPI.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping(value = "/guest")
    public ResponseEntity getContacts() {
        return responseHandler.generateResponse(
                "All Contacts Received",
                HttpStatus.OK,
                contactService.getAllContacts()
        );
    }

    @GetMapping(value = "/guest/{id}")
    public ResponseEntity getContact(@PathVariable String id) {
        return responseHandler.generateResponse(
                "Contact Received",
                HttpStatus.OK,
                contactService.getContactById(id)
        );
    }

    @PostMapping(value = "/guest")
    public ResponseEntity postGuestContact(@RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Contact stored successfully",
                HttpStatus.CREATED,
                contactService.storeGuestContact(contact)
        );
    }

    @PutMapping(value = "/guest/{id}")
    public ResponseEntity postGuestContact(@PathVariable String id, @RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Contact modified successfully",
                HttpStatus.OK,
                contactService.modifyGuestContact(contact, id)
        );
    }

    @DeleteMapping(value = "/guest/{id}")
    public ResponseEntity postGuestContact(@PathVariable String id) {
        contactService.deleteGuestContact(id);

        return responseHandler.generateResponse(
                "Contact deleted successfully",
                HttpStatus.OK
        );
    }

}
