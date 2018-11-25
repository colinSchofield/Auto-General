package com.autogeneral.techtest.angservice.model.db;

import com.autogeneral.techtest.angservice.model.db.repository.ToDoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/** Ensure that the DynamoDb is functioning correctly -- need to setup a local instance for this Integration test */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DynamoDbIT {

    @Autowired
    ToDoRepository toDoRepository;

    @Test
    public void testCreateTodoItemThenViewAndDelete() {

        Iterable<ToDo> todoList = toDoRepository.findAll();
        assertNotNull(todoList);

        ToDo item = new ToDo("Todo item 1", false, "running out");
        toDoRepository.save(item);
        assertNotNull(item.getId());

        Optional<ToDo> optional = toDoRepository.findById(item.getId());
        assertTrue(optional.isPresent());
        ToDo item2 = optional.get();
        assertEquals(item.getText(), item2.getText());

        toDoRepository.deleteAll();
    }
}