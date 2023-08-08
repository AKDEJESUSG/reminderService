package com.skipit.remainder.skipitreminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skipit.remainder.skipitreminder.entity.ReminderEntity;

public interface ReminderRepository extends JpaRepository<ReminderEntity, Long> {

}
