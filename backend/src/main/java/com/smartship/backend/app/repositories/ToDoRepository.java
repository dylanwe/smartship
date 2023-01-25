package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    /**
     * get a list of all to-do's for the user
     *
     * @param userId id of the user
     * @return list of to-do's
     */
    List<ToDo> findAllByUserId(Long userId);

    /**
     * find all to-do's that are supposed to be done from a specific user
     *
     * @param userId id of the user
     * @param dueAt the deadline of a to-do
     * @return list of to-do's that are supposed to be done
     */
    List<ToDo> findAllByUserIdAndDueAtBefore(Long userId, LocalDate dueAt);
}
