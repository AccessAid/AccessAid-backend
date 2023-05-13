package dev.accessaid.AccessAid.Contact.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Contact.model.Contact;
import dev.accessaid.AccessAid.Contact.response.ContactResponse;

public class ContactMapper {

    public static ContactResponse toContactResponse(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getMessage(),
                contact.getSentDate());
    }

    public static List<ContactResponse> toProfileResponses(List<Contact> contacts) {
        return contacts.stream().map(ContactMapper::toContactResponse).collect(Collectors.toList());
    }

    public static Page<ContactResponse> toContactResponses(Page<Contact> contacts, Pageable pageable) {
        List<ContactResponse> contactResponses = contacts.stream()
                .map(ContactMapper::toContactResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(contactResponses, pageable, contacts.getTotalElements());
    }

}
