package dev.accessaid.AccessAid.Contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Contact.model.Contact;
import dev.accessaid.AccessAid.Contact.response.ContactResponse;
import dev.accessaid.AccessAid.Contact.service.ContactServiceImpl;
import dev.accessaid.AccessAid.Contact.utils.ContactMapper;
import dev.accessaid.AccessAid.config.documentation.Contact.ContactRequestExample;
import dev.accessaid.AccessAid.config.documentation.Contact.ContactResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@Tag(name = "Contact", description = "Contact form")
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactServiceImpl contactService;

    @Operation(summary = "See a list of contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("")
    public Page<ContactResponse> seeAllContacts(Pageable pageable) {
        Page<Contact> contacts = contactService.getContacts(pageable);
        return ContactMapper.toContactResponses(contacts, pageable);

    }

    @Operation(summary = "See a contact by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ContactResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ContactResponse seeContactById(@PathVariable Integer id) {
        Contact contact = contactService.getContactById(id);
        return ContactMapper.toContactResponse(contact);

    }

    @Operation(summary = "Add contact", description = "Create a new contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created successfully", content = @Content(schema = @Schema(implementation = ContactResponseExample.class))),
            @ApiResponse(responseCode = "400", description = "Error saving profile", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponse addContact(
            @RequestBody @Valid @Schema(implementation = ContactRequestExample.class) Contact contact) {
        Contact newContact = contactService.createContact(contact);
        contactService.sendEmailNotification(contact);
        return ContactMapper.toContactResponse(newContact);

    }

    @Operation(summary = "See contact by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ContactRequestExample.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)
    })
    @GetMapping("/email/{email}")
    public ContactResponse seeContactByEmail(
            @PathVariable @Validated @Email(message = "Invalid email format") String email) {
        Contact contact = contactService.getContactByEmail(email);
        return ContactMapper.toContactResponse(contact);

    }

}
