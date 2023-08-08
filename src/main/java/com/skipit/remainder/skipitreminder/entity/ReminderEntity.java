package com.skipit.remainder.skipitreminder.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.skipit.remainder.skipitreminder.model.FrequencyEnum;

@Data
@Entity
@Table(name = "tbl_reminder")
@NoArgsConstructor
public class ReminderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reminder_id")
    private Long id;

    @Column(name = "message", length = 200)
    private String message;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_unit")
    private FrequencyEnum timeUnit;

    @Column(name = "custom_time")
    private String customTime;

    @Column(name = "schedule")
    @Temporal(TemporalType.TIME)
    private LocalTime schedule;

    @Column(name = "active")
    private Boolean isActive;

}
