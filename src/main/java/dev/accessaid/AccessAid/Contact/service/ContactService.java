package dev.accessaid.AccessAid.Contact.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Contact.exceptions.ContactNotFoundException;
import dev.accessaid.AccessAid.Contact.exceptions.ContactSaveException;
import dev.accessaid.AccessAid.Contact.exceptions.EmailSendException;
import dev.accessaid.AccessAid.Contact.model.Contact;

public interface ContactService {

    List<Contact> getContacts();

    Page<Contact> getContacts(Pageable pageable);

    Contact getContactById(Integer id) throws ContactNotFoundException;

    Contact createContact(Contact contact) throws ContactSaveException;

    Contact getContactByEmail(String email) throws ContactNotFoundException;

    void sendEmailNotification(Contact contact) throws EmailSendException;
}
