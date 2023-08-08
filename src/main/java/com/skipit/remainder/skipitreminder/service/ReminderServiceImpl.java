package com.skipit.remainder.skipitreminder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.skipit.remainder.skipitreminder.entity.ReminderEntity;
import com.skipit.remainder.skipitreminder.exception.ReminderNotFoundException;
import com.skipit.remainder.skipitreminder.exception.ReminderValidationException;
import com.skipit.remainder.skipitreminder.model.FrequencyEnum;
import com.skipit.remainder.skipitreminder.model.ReminderDTO;
import com.skipit.remainder.skipitreminder.repository.ReminderRepository;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    ReminderRepository reminderRepo;

    @Override
    public List<ReminderDTO> getAllReminders(int page, int pageSize) {
        Pageable sort = PageRequest.of(page, pageSize);
        Page<ReminderEntity> entity = reminderRepo.findAll(sort);
        return entity.stream()
                .map(ReminderDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ReminderDTO getReminderById(Long id) {
        ReminderEntity entity = reminderRepo.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(String.format("Reminder id:%s not found", id)));
        return new ReminderDTO(entity);
    }

    @Override
    public ReminderDTO createReminder(ReminderDTO reminder) {
        if (!isTimeUnitValid(reminder)) {
            throw new ReminderValidationException(
                    reminder.getTimeUnit() + " Frequency selected, you must add the CustomTime value");
        }
        ReminderEntity entity = new ReminderEntity();
        reminder.setCreated(LocalDateTime.now());
        BeanUtils.copyProperties(reminder, entity);
        entity = reminderRepo.save(entity);
        return new ReminderDTO(entity);
    }

    @Override
    public ReminderDTO updateReminder(Long id, ReminderDTO reminder) {
        if (!isTimeUnitValid(reminder)) {
            throw new ReminderValidationException(
                    reminder.getTimeUnit() + " Frequency selected, you must add the CustomTime value");
        }
        ReminderDTO actual = getReminderById(id);
        ReminderEntity entity = new ReminderEntity();
        actual.setCustomTime(reminder.getCustomTime());
        actual.setIsActive(reminder.getIsActive());
        actual.setMessage(reminder.getMessage());
        actual.setSchedule(reminder.getSchedule());
        actual.setTimeUnit(reminder.getTimeUnit());
        actual.setUpdated(LocalDateTime.now());
        BeanUtils.copyProperties(actual, entity);
        entity = reminderRepo.save(entity);
        return new ReminderDTO(entity);
    }

    @Override
    public void deleteReminder(Long id) {
        this.getReminderById(id);
        reminderRepo.deleteById(id);
    }

    private boolean isTimeUnitValid(ReminderDTO reminder) {
        boolean isvalid = true;
        if ((FrequencyEnum.CUSTOM.compareTo(reminder.getTimeUnit()) == 0
                || FrequencyEnum.DATE.compareTo(reminder.getTimeUnit()) == 0)
                && (reminder.getCustomTime() == null || reminder.getCustomTime().isEmpty())) {
            isvalid = false;
        }
        return isvalid;
    }
}
