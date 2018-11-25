package com.autogeneral.techtest.angservice.service;

import com.autogeneral.techtest.angservice.model.db.ToDo;
import com.autogeneral.techtest.angservice.model.db.repository.ToDoRepository;
import com.autogeneral.techtest.angservice.model.json.ToDoItem;
import com.autogeneral.techtest.angservice.model.json.ToDoItemRequest;
import com.autogeneral.techtest.angservice.model.json.ToDoItemUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

/**
 * The TodoService contains the business logic for managing the TodoItems. The items may be either created, viewed or updated.
 * To implement this, an AWS DynamoDb is used as it provides performance, scalability and resilience, unlike a pure memory HashTable.
 */
@Service
public class TodoService {

    private final Logger log = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private ToDoRepository toDoRepository;

    public ResponseEntity<ToDoItem> createTodoItem(ToDoItemRequest request) {

        log.debug("About to create a Todo item {}", request.getText());
        ToDo itemDb = new ToDo(request.getText(), false, getCurrentDateAsISO8601());
        toDoRepository.save(itemDb);
        ToDoItem itemJson = new ToDoItem(itemDb.getId(), itemDb.getText(), itemDb.getCompleted(), itemDb.getCreatedAt());
        log.debug("Todo item created: { id: {}, created: {} }.", itemDb.getId(), itemDb.getCreatedAt());

        return new ResponseEntity<>(itemJson, HttpStatus.OK);
    }

    public ResponseEntity<ToDoItem> viewTodoItem(String id) {

        log.debug("About to view item {}", id);
        Optional<ToDo> optional = toDoRepository.findById(id);
        if (!optional.isPresent()) {

            log.warn("No Item for key {}.", id);
            ToDoItem response = new ToDoItem();
            response.setMessage("Item with id of " + id + " not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        ToDo itemDb = optional.get();
        ToDoItem itemJson = new ToDoItem(itemDb.getId(), itemDb.getText(), itemDb.getCompleted(), itemDb.getCreatedAt());
        log.debug("Item contents: {}", itemJson);
        return new ResponseEntity<>(itemJson, HttpStatus.OK);
    }

    public ResponseEntity<ToDoItem> updateTodoItem(String id, ToDoItemUpdateRequest request) {

        log.debug("About to update item {}", id);
        Optional<ToDo> optional = toDoRepository.findById(id);
        if (!optional.isPresent()) {

            log.warn("No Item for key {}.", id);
            ToDoItem response = new ToDoItem();
            response.setMessage("Item with id of " + id + " not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        ToDo itemDb = optional.get();
        if (request.getText() != null) {

            itemDb.setText(request.getText());
        }
        if (request.getCompleted() != null) {

            itemDb.setCompleted(request.getCompleted());
        }
        toDoRepository.save(itemDb);
        ToDoItem itemJson = new ToDoItem(itemDb.getId(), itemDb.getText(), itemDb.getCompleted(), itemDb.getCreatedAt());
        log.debug("Item contents: {}", itemJson);
        return new ResponseEntity<>(itemJson, HttpStatus.OK);
    }

    private String getCurrentDateAsISO8601() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        return df.format(new Date());
    }
}
