package dev.accessaid.AccessAid.Contact.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.accessaid.AccessAid.Contact.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByEmail(String email);

}
