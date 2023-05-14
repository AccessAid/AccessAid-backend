package dev.accessaid.AccessAid.Contact.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Contact.exceptions.ContactNotFoundException;
import dev.accessaid.AccessAid.Contact.exceptions.ContactSaveException;
import dev.accessaid.AccessAid.Contact.exceptions.EmailSendException;
import dev.accessaid.AccessAid.Contact.model.Contact;
import dev.accessaid.AccessAid.Contact.repository.ContactRepository;
import dev.accessaid.AccessAid.Contact.utils.EmailText;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailText emailText;

    @Override
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Page<Contact> getContacts(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }

    @Override
    public Contact getContactById(Integer id) throws ContactNotFoundException {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with ID: " + id));
    }

    @Override
    public Contact createContact(Contact contact) throws ContactSaveException {
        try {
            contact.setSentDate(LocalDateTime.now());
            return contactRepository.save(contact);
        } catch (Exception e) {
            throw new ContactSaveException("Error saving contact: " + e.getMessage());
        }
    }

    @Override
    public Contact getContactByEmail(String email) throws ContactNotFoundException {
        return contactRepository.findByEmail(email)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with email: " + email));
    }

    @Override
    public void sendEmailNotification(Contact contact) throws EmailSendException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailText.getEmailSetToAccessAid());
            message.setSubject(emailText.getEmailSubject());
            message.setText(emailText.getEmailText() +
                    "Name: " + contact.getName() + "\n" +
                    "Email: " + contact.getEmail() + "\n" +
                    "Subject: " + contact.getSubject() + "\n" +
                    "Message: " + contact.getMessage());

            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendException("Error sending email notification: " + e.getMessage());
        }
    }

}
