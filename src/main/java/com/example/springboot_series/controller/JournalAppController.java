//package com.example.springboot_series.controller;
//
//import com.example.springboot_series.entity.JournalEntry;
//import com.example.springboot_series.service.JounalService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/journal")
//public class JournalAppController {
//
//    @Autowired
//    private JounalService jounalService;
//
//    @PostMapping("/create")
//    public JournalEntry createEntry(@RequestBody JournalEntry entry) {
//        return jounalService.saveEntry(entry);
//    }
//
//    // Get All Entries
//    @GetMapping("/get")
//    public List<JournalEntry> getAllEntries() {
//        return jounalService.getAllEntries();
//    }
//
//    // Get Entry by ID
//    @GetMapping("/get/{id}")
//    public Optional<JournalEntry> getEntry(@PathVariable int id) {
//        return jounalService.getEntryById(id);
//    }
//
//    // Update Entry
//    @PutMapping("/put/{id}")
//    public JournalEntry updateEntry(@PathVariable int id, @RequestBody JournalEntry newEntry) {
//        Optional<JournalEntry> oldEntry = jounalService.getEntryById(id);
//
//        if (oldEntry.isPresent()) {
//            JournalEntry entry = oldEntry.get();
//            entry.setTitle(newEntry.getTitle());
//            entry.setContent(newEntry.getContent());
//            return jounalService.saveEntry(entry);
//        }
//        return null;
//    }
//
//    // Delete Entry
//    @DeleteMapping("/delete/{id}")
//    public String deleteEntry(@PathVariable int id) {
//        jounalService.deleteEntry(id);
//        return "Deleted successfully";
//    }
//}
