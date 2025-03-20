package com.adoptante.googlecontacts.controller;

import com.adoptante.googlecontacts.service.ContactsService;
import com.google.api.services.people.v1.model.Person;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class WebController {

    private final ContactsService googleContactsService;

    public WebController(ContactsService googleContactsService) {
        this.googleContactsService = googleContactsService;
    }

    // Get
    @GetMapping("/contacts")
    public String showContacts(Model model) {
        try {
            List<Person> contacts = googleContactsService.getContacts();
            System.out.println("Fetched contacts: " + contacts.size());
            model.addAttribute("contacts", contacts);
            return "contacts";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to fetch contacts.");
            return "error";
        }
    }

    // Post New Contact
    @PostMapping("/api/contacts/create")
    public String createContact(
            @RequestParam String givenName,
            @RequestParam String familyName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) throws IOException {

        Person newContact = googleContactsService.createContact(givenName, familyName, email, phoneNumber);
        System.out.println("Contact created: " + newContact.getResourceName());

        return "redirect:/contacts";
    }

    // Update Contact
    @PostMapping("/api/contacts/update")
    public String updateContact(
            @RequestParam String resourceName,
            @RequestParam String givenName,
            @RequestParam String familyName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {

        try {

            googleContactsService.updateContact(resourceName, givenName, familyName, email, phoneNumber);
            System.out.println("Contact updated: " + resourceName);
            return "redirect:/contacts";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    // Delete Contact
    @PostMapping("/api/contacts/delete")
    public String deleteContact(@RequestParam String resourceName) {
        try {

            googleContactsService.deleteContact(resourceName);
            System.out.println("Deleted contact: " + resourceName);
            return "redirect:/contacts";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}