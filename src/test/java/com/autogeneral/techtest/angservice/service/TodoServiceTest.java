package com.autogeneral.techtest.angservice.service;

import com.autogeneral.techtest.angservice.model.db.ToDo;
import com.autogeneral.techtest.angservice.model.db.repository.ToDoRepository;
import com.autogeneral.techtest.angservice.model.json.ToDoItem;
import com.autogeneral.techtest.angservice.model.json.ToDoItemRequest;
import com.autogeneral.techtest.angservice.model.json.ToDoItemUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @Autowired
    @InjectMocks
    private TodoService todoService;

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    ToDo toDo;

    @Before
    public void setup() {
    }

    @Test
    public void testCreateTodoItem() {

        ToDoItemRequest request = new ToDoItemRequest();
        request.setText("Todo Item 1");
        ResponseEntity<ToDoItem> response = todoService.createTodoItem(request);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void subsequentCreates() {

        ToDoItemRequest request = new ToDoItemRequest();
        request.setText("Todo Item 1");
        ResponseEntity<ToDoItem> response = todoService.createTodoItem(request);
        String id = response.getBody().getId();

        ToDoItemRequest request2 = new ToDoItemRequest();
        request.setText("Todo Item 2");
        ResponseEntity<ToDoItem> response2 = todoService.createTodoItem(request);
    }

    @Test
    public void createThenView() {

        ToDoItemRequest request = new ToDoItemRequest();
        request.setText("Todo Item 1");
        ResponseEntity<ToDoItem> response = todoService.createTodoItem(request);
        String id = response.getBody().getId();

        Optional<ToDo> optional = Optional.of(toDo);
        when(toDoRepository.findById(null)).thenReturn(optional);
        ResponseEntity<ToDoItem> view = todoService.viewTodoItem(id);
        assertEquals(view.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateWithNullCompleted() {

        ToDoItemRequest request = new ToDoItemRequest();
        request.setText("Todo Item 1");
        ResponseEntity<ToDoItem> response = todoService.createTodoItem(request);
        String id = response.getBody().getId();

        ToDoItemUpdateRequest updateRequest = new ToDoItemUpdateRequest();
        updateRequest.setCompleted(null);
        updateRequest.setText("Todo Item 1 - Edit");

        Optional<ToDo> optional = Optional.of(toDo);
        when(toDoRepository.findById(null)).thenReturn(optional);
        ResponseEntity<ToDoItem> response2 = todoService.updateTodoItem(id, updateRequest);
        assertEquals(response2.getStatusCode(), HttpStatus.OK);
        assertEquals(response2.getBody().getId(), id);
        assertEquals(false, response2.getBody().getCompleted());
    }

    @Test
    public void viewItemNotFound() {

        ResponseEntity<ToDoItem> response = todoService.viewTodoItem("Not Selected");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}