package com.skipit.remainder.skipitreminder.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.BeanUtils;

import com.skipit.remainder.skipitreminder.entity.ReminderEntity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderDTO {

    private Long id;

    @NotNull(message = "Message would not be null")
    private String message;

    private LocalDateTime created;

    private LocalDateTime updated;

    @NotNull(message = "timeUnit would not be null")
    private FrequencyEnum timeUnit;

    private String customTime;

    @NotNull(message = "schedule would not be null")
    private LocalTime schedule;

    @NotNull(message = "isActive would not be null")
    private Boolean isActive;

    public ReminderDTO(ReminderEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
