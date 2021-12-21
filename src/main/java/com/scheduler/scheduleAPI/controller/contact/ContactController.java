package com.scheduler.scheduleAPI.controller.contact;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.response.ResponseHandler;
import com.scheduler.scheduleAPI.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getContacts() {
        return responseHandler.generateResponse(
                "All Contacts Received",
                HttpStatus.OK,
                contactService.getAllContacts()
        );
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getContact(@PathVariable String id) {
        return responseHandler.generateResponse(
                "Contact Received",
                HttpStatus.OK,
                contactService.getContactById(id)
        );
    }

    @PostMapping(value = "/guest")
    @PreAuthorize("hasAuthority('contact:write')")
    public ResponseEntity postGuestContact(@RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Guest Contact stored successfully",
                HttpStatus.CREATED,
                contactService.storeGuestContact(contact)
        );
    }

    @PostMapping(value = "/owner")
    public ResponseEntity postOwnerContact(@RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Owner Contact stored successfully",
                HttpStatus.CREATED,
                contactService.storeOwnerContact(contact)
        );
    }

    @PutMapping(value = "/guest/{id}")
    @PreAuthorize("hasAuthority('contact:write')")
    public ResponseEntity putGuestContact(@PathVariable String id, @RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Contact modified successfully",
                HttpStatus.OK,
                contactService.modifyGuestContact(contact, id)
        );
    }

    @PutMapping(value = "/owner/{id}")
    @PreAuthorize("hasAuthority('contact:write')")
    public ResponseEntity putOwnerContact(@PathVariable String id, @RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Contact modified successfully",
                HttpStatus.OK,
                contactService.modifyOwnerContact(contact, id)
        );
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('contact:write')")
    public ResponseEntity deleteGuestContact(@PathVariable String id) {
        contactService.deleteGuestContactById(id);

        return responseHandler.generateResponse(
                "Contact deleted successfully",
                HttpStatus.OK
        );
    }

}
