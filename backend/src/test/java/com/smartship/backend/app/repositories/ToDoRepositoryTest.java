package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.ToDo;
import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.List;

/**
 * @author lisanne lin
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
public class ToDoRepositoryTest {

    @Autowired
    ToDoRepository toDoRepository;
    @Autowired
    UserRepository userRepository;
    User bruce, frank;

    @BeforeEach
    void setup() {
        bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night!");

        frank = userRepository.save(new User(
                "Frank",
                "Murphy",
                "frank@murphy.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "DIT DIT DIT DIT"));
    }

    @AfterEach
    void teardown() {
        toDoRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void shouldNotFindAnyToDos() {
        List<ToDo> todos = toDoRepository.findAll();
        // check if todos is empty
        assertThat(todos.size()).isEqualTo(0);
    }

    @Test
    void ShouldFindAllByUserIdAndDueAtBefore() {
        // make a couple of todos, some will have a due date in the future, save all those todos
        User bruce = userRepository.save(new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night"));

        // save todos
        toDoRepository.saveAll(List.of(
                new ToDo(
                        "Check engine",
                        false,
                        LocalDate.now().minusDays(3),
                        LocalDate.now().minusDays(7),
                        LocalDate.now(),
                        bruce
                ),
                new ToDo(
                        "Have lunch with captain",
                        false,
                        null,
                        LocalDate.now().minusDays(3),
                        LocalDate.now(),
                        bruce

                ),
                new ToDo(
                        "Update ship data",
                        true,
                        LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(3),
                        LocalDate.now(),
                        bruce
                )
        ));

        // get all todos due today or due before today
        List<ToDo> dueToDos = toDoRepository
                .findAllByUserIdAndDueAtBefore(bruce.getId(), LocalDate.now());

        // assert that we only have 2 todos
        assertThat(dueToDos.size()).isEqualTo(2);
    }

    @Test
    void ShouldFindAllByUserId() {
        User savedFrank = userRepository.save(frank);
        User savedBruce = userRepository.save(bruce);

        toDoRepository.saveAll(List.of(
                new ToDo(
                        "Check engine",
                        false,
                        LocalDate.now().minusDays(3),
                        LocalDate.now().minusDays(7),
                        LocalDate.now(),
                        savedBruce
                ),
                new ToDo(
                        "Have lunch with captain",
                        false,
                        null,
                        LocalDate.now().minusDays(3),
                        LocalDate.now(),
                        savedBruce

                ),
                new ToDo(
                        "Update ship data",
                        true,
                        LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(3),
                        LocalDate.now(),
                        savedFrank
                )
        ));


        List<ToDo> franksTodos = toDoRepository.findAllByUserId(savedFrank.getId());
        List<ToDo> bruceTodos = toDoRepository.findAllByUserId(savedBruce.getId());

        assertThat(franksTodos.size()).isEqualTo(1);
        assertThat(bruceTodos.size()).isEqualTo(2);
    }

    @Test
    void ShouldDeleteToDoById() {
        List<ToDo> todos = toDoRepository.findAll();
        assertThat(todos.size()).isEqualTo(0);

        User savedFrank = userRepository.save(frank);

        ToDo chonka = toDoRepository.save(new ToDo(
                "Update ship data",
                true,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(3),
                LocalDate.now(),
                savedFrank
        ));

        // check if to do is added
        todos = toDoRepository.findAll();
        assertThat(todos.size()).isEqualTo(1);

        // remove added to do by id
        toDoRepository.deleteById(chonka.getId());

        // check if to do is deleted
        todos = toDoRepository.findAll();
        assertThat(todos.size()).isEqualTo(0);


    }
}
