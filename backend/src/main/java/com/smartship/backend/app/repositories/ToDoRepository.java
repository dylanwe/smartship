package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findAllByUserId(Long userId);

    List<ToDo> findAllByUserIdAndDueAtBefore(Long userId, LocalDate dueAt);
}
