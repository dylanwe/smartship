package com.smartship.backend.app.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.exceptions.UnauthorizedException;
import com.smartship.backend.app.models.ToDo;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.ToDoRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/users/{userId}/todos")
public class ToDoController {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @Autowired
    public ToDoController(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<ToDo>> getAllTodosForUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo,@PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        return ResponseEntity.ok().body(
                user.getToDos()
                        .stream()
                        .sorted(Comparator.comparing(ToDo::isCompleted, Boolean::compareTo))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("due")
    public ResponseEntity<?> getAllTodosForUserBeforeAndToday(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo,@PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        List<ToDo> allByUserIdAndDueAtBefore = toDoRepository.findAllByUserIdAndDueAtBefore(userId, LocalDate.now());

        return ResponseEntity.ok().body(allByUserIdAndDueAtBefore
                .stream()
                .sorted(Comparator.comparing(ToDo::isCompleted, Boolean::compareTo))
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "{toDoId}")
    public ResponseEntity<ToDo> getToDo(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo,@PathVariable Long userId, @PathVariable Long toDoId ) {
        // check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        ToDo toDo = toDoRepository.findById(toDoId)
                .orElseThrow(() -> new NotFoundException(String.format("Todo with id %d wasn't found", toDoId)));

        return ResponseEntity.ok().body(toDo);
    }

    @JsonIgnoreProperties("user")
    @PostMapping
    public ResponseEntity<?> addToDo(@RequestBody ObjectNode body, @PathVariable Long userId, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        String name = body.path("name").asText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate dueDate = LocalDate.parse(body.path("dueDate").asText(), formatter);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d wasn't found", userId)));

        return ResponseEntity.ok().body(toDoRepository.save(new ToDo(null,name, false, null, dueDate, LocalDate.now(), user)));
    }

    @PutMapping(path = "{todoId}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long todoId, @RequestBody ObjectNode body, @PathVariable Long userId, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        Long id = body.path("id").asLong();
        String name = body.path("name").asText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate dueAt = LocalDate.parse(body.path("dueAt").asText(), formatter);
        boolean isCompleted = body.path("isCompleted").asBoolean();

        if (!todoId.equals(id))
            throw new NotAcceptableException("Id's don't match");

        ToDo foundToDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException(String.format("Todo with id %d wasn't found", todoId)));

        foundToDo.setName(name);
        foundToDo.setDueAt(dueAt);
        foundToDo.setCompleted(isCompleted);

        return ResponseEntity.ok().body(toDoRepository.save(foundToDo));
    }

    @DeleteMapping(path ="{todoId}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable Long todoId, @PathVariable Long userId, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        ToDo toDo = toDoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException(String.format("Todo with id %d wasn't found", todoId)));

        toDoRepository.deleteById(todoId);

        return ResponseEntity.ok().body(toDo);
    }
}
