package dev.accessaid.AccessAid.Contact.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.accessaid.AccessAid.Contact.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByEmail(String email);

}
