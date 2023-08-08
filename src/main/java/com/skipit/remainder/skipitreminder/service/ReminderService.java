package com.skipit.remainder.skipitreminder.service;

import java.util.List;

import com.skipit.remainder.skipitreminder.model.ReminderDTO;

public interface ReminderService {

    public List<ReminderDTO> getAllReminders(int page, int pageSize);

    public ReminderDTO getReminderById(Long id);

    public ReminderDTO createReminder(ReminderDTO reminder);

    public ReminderDTO updateReminder(Long id, ReminderDTO reminder);

    public void deleteReminder(Long id);
}
