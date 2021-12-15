package com.scheduler.scheduleAPI.controller.contact;

import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.response.ResponseHandler;
import com.scheduler.scheduleAPI.service.GuestContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contacts/guest")
public class GuestContactController {

    @Autowired
    private GuestContactService guestContactService;
    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getContacts() {
        return responseHandler.generateResponse(
                "All Contacts Received",
                HttpStatus.OK,
                guestContactService.getAllContacts()
        );
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GUEST','ROLE_OWNER')")
    public ResponseEntity getContact(@PathVariable String id) {
        return responseHandler.generateResponse(
                "Contact Received",
                HttpStatus.OK,
                guestContactService.getContactById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('contact:write')")
    public ResponseEntity postGuestContact(@RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Contact stored successfully",
                HttpStatus.CREATED,
                guestContactService.storeGuestContact(contact)
        );
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('contact:write')")
    public ResponseEntity postGuestContact(@PathVariable String id, @RequestBody Contact contact) {
        return responseHandler.generateResponse(
                "Contact modified successfully",
                HttpStatus.OK,
                guestContactService.modifyGuestContact(contact, id)
        );
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('contact:write')")
    public ResponseEntity postGuestContact(@PathVariable String id) {
        guestContactService.deleteGuestContact(id);

        return responseHandler.generateResponse(
                "Contact deleted successfully",
                HttpStatus.OK
        );
    }

}
