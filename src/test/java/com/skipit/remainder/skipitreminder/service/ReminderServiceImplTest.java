package com.skipit.remainder.skipitreminder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.skipit.remainder.skipitreminder.entity.ReminderEntity;
import com.skipit.remainder.skipitreminder.exception.ReminderNotFoundException;
import com.skipit.remainder.skipitreminder.exception.ReminderValidationException;
import com.skipit.remainder.skipitreminder.model.FrequencyEnum;
import com.skipit.remainder.skipitreminder.model.ReminderDTO;
import com.skipit.remainder.skipitreminder.repository.ReminderRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Sql(scripts = "/data.sql")
@DisplayName("CRUD Reminder")
class ReminderServiceImplTest {

    @SpyBean
    private ReminderRepository reminderRepository;

    @Autowired
    private ReminderService reminderService;

    @Test
    @DisplayName("Get Reminders - success scenario")
    void test_When_Get_Reminders_Success() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ReminderEntity> result = reminderRepository.findAll(pageable);
        List<ReminderDTO> serviceResult = reminderService.getAllReminders(0, 5);

        verify(reminderRepository, times(2)).findAll(pageable);

        assertEquals(result.getSize(), serviceResult.size());
        assertEquals(5, result.getSize());
    }

    @Test
    @DisplayName("Get reminder by ID - success scenario")
    void test_When_Get_Reminder_by_ID_Success() {
        ReminderDTO result = reminderService.getReminderById(1L);

        verify(reminderRepository, times(1)).findById(anyLong());

        assertNotNull(result);
    }

    @Test()
    @DisplayName("Get reminder by ID - failure scenario")
    void test_When_Get_Reminder_by_ID_Failure() {
        ReminderNotFoundException ex = assertThrows(ReminderNotFoundException.class,
                () -> reminderService.getReminderById(0L));
        
        verify(reminderRepository, times(1)).findById(anyLong());
        
        assertEquals("Reminder id:0 not found", ex.getMessage());
    }

    @Test
    @DisplayName("Create Reminder - success scenario")
    void test_When_Create_Remider_Success() {
        ReminderDTO reminder = getReminderDTO();
        reminder = reminderService.createReminder(reminder);

        verify(reminderRepository, times(1)).save(any());

        assertNotNull(reminder.getId());
        assertNotNull(reminder.getCreated());

    }

    @Test
    @DisplayName("Create Reminder - failure scenario")
    void test_When_Create_Reminder_Validations_Fail() {
        ReminderDTO reminder = getReminderDTO();

        reminder.setTimeUnit(FrequencyEnum.CUSTOM);
        ReminderValidationException exCustom = assertThrows(ReminderValidationException.class,
                () -> reminderService.createReminder(reminder));

        reminder.setTimeUnit(FrequencyEnum.DATE);
        ReminderValidationException exDATE = assertThrows(ReminderValidationException.class,
                () -> reminderService.createReminder(reminder));

        verify(reminderRepository, never()).save(any());
        
        assertEquals(FrequencyEnum.CUSTOM + " Frequency selected, you must add the CustomTime value",
                exCustom.getMessage());
        assertEquals(FrequencyEnum.DATE + " Frequency selected, you must add the CustomTime value",
                exDATE.getMessage());
    }

    @Test
    @DisplayName("Update Reminder - success scenario")
    void test_When_Update_Remider_Success() {
        ReminderDTO reminder = reminderService.getReminderById(1L);
        reminder.setTimeUnit(FrequencyEnum.DATE);
        reminder.setCustomTime(LocalDate.now().toString());
        ReminderDTO updated = reminderService.updateReminder(1L, reminder);

        verify(reminderRepository, times(1)).save(any());

        assertEquals(reminder.getTimeUnit(), updated.getTimeUnit());
        assertNotNull(updated.getUpdated());

    }

    @Test
    @DisplayName("Update Reminder - failure scenario")
    void test_When_Update_Remider_Failure() {
        long reminderID = 2;

        ReminderDTO reminder = reminderService.getReminderById(reminderID);
        reminder.setTimeUnit(FrequencyEnum.CUSTOM);

        ReminderValidationException exCustom = assertThrows(ReminderValidationException.class,
        () -> reminderService.updateReminder(reminderID,reminder));

        reminder.setTimeUnit(FrequencyEnum.DATE);
        ReminderValidationException exDATE = assertThrows(ReminderValidationException.class,
                () -> reminderService.updateReminder(reminderID,reminder));
        
        verify(reminderRepository, never()).save(any());
        
        assertEquals(FrequencyEnum.CUSTOM + " Frequency selected, you must add the CustomTime value",
                exCustom.getMessage());
        assertEquals(FrequencyEnum.DATE + " Frequency selected, you must add the CustomTime value",
                exDATE.getMessage());
    }

    @Test
    @DisplayName("Delete Reminder - success sceneario")
    void test_When_Delete_Reminder_Success(){
        reminderService.deleteReminder(3L);

        ReminderNotFoundException ex = assertThrows(ReminderNotFoundException.class,
                () -> reminderService.getReminderById(3L));

        verify(reminderRepository, times(1)).deleteById(anyLong());

        
        assertEquals("Reminder id:3 not found", ex.getMessage());
    }

    @Test
    @DisplayName("Delete Reminder - failure sceneario")
    void test_When_Delete_Reminder_Failure(){
        ReminderNotFoundException ex = assertThrows(ReminderNotFoundException.class,
                () -> reminderService.getReminderById(0L));

        verify(reminderRepository, never()).deleteById(anyLong());
        
        assertEquals("Reminder id:0 not found", ex.getMessage());
    }

    private ReminderDTO getReminderDTO() {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setCreated(LocalDateTime.now());
        reminderDTO.setIsActive(true);
        reminderDTO.setMessage("Test: Reminder created");
        reminderDTO.setSchedule(LocalTime.of(12, 30));
        reminderDTO.setTimeUnit(FrequencyEnum.DAILY);
        return reminderDTO;
    }
}
