//package com.example.springboot_series.service;
//
//import com.example.springboot_series.entity.JournalEntry;
//import com.example.springboot_series.repository.JounalRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class JounalService {
//
//    @Autowired
//    private JounalRepository jounalRepository;
//
//    public JournalEntry saveEntry(JournalEntry entry) {
//        return jounalRepository.save(entry);
//    }
//
//    // Get All
//    public List<JournalEntry> getAllEntries() {
//        return jounalRepository.findAll();
//    }
//
//    // Get by ID
//    public Optional<JournalEntry> getEntryById(int id) {
//        return jounalRepository.findById(id);
//    }
//
//    // Delete
//    public void deleteEntry(int id) {
//        jounalRepository.deleteById(id);
//    }
//}
