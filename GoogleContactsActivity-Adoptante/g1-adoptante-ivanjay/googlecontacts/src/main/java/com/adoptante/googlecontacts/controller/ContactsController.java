package com.adoptante.googlecontacts.controller;

import com.adoptante.googlecontacts.service.ContactsService;
import com.google.api.services.people.v1.model.Person;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {

    private final ContactsService googleContactsService;

    public ContactsController(ContactsService googleContactsService) {
        this.googleContactsService = googleContactsService;
    }

    @GetMapping
    public List<Person> getContacts() throws IOException {
        List<Person> contacts = googleContactsService.getContacts();
        System.out.println("Fetched Contacts: " + contacts);
        return contacts;
    }
}
