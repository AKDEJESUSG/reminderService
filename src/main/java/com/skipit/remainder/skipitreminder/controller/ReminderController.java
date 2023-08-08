package com.skipit.remainder.skipitreminder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skipit.remainder.skipitreminder.model.ReminderDTO;
import com.skipit.remainder.skipitreminder.service.ReminderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reminders")
public class ReminderController {

    @Autowired
    ReminderService reminderSrv;

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<ReminderDTO>> getAllReminders(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<ReminderDTO> reminders = reminderSrv.getAllReminders(page, size);
        return ResponseEntity.ok(reminders);
    }

    @GetMapping("/{id}")
    public ReminderDTO getReminder(@PathVariable(name = "id") Long id) {
        return reminderSrv.getReminderById(id);
    }

    @PostMapping(value = { "", "/" })
    public ResponseEntity<ReminderDTO> createReminder(@Valid @RequestBody ReminderDTO reminder) {
        ReminderDTO reminderEntity = reminderSrv.createReminder(reminder);
        return ResponseEntity.ok(reminderEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderDTO> updateReminder(@PathVariable(name = "id") Long id,
            @RequestBody ReminderDTO reminder) {
        ReminderDTO updatedReminderDTO = reminderSrv.updateReminder(id, reminder);
        return ResponseEntity.ok(updatedReminderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReminder(@PathVariable("id") Long id) {
        reminderSrv.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}
