package com.autogeneral.techtest.angservice.web;

import com.autogeneral.techtest.angservice.model.json.ToDoItem;
import com.autogeneral.techtest.angservice.model.json.ToDoItemRequest;
import com.autogeneral.techtest.angservice.model.json.ToDoItemUpdateRequest;
import com.autogeneral.techtest.angservice.model.json.validation.ToDoItemRequestValidator;
import com.autogeneral.techtest.angservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The TodoController RESTful Controller takes requests from the caller and then passes them through to the TodoService,
 * where all the business logic is contained. Results are returned as per the API definition.
 *
 * @see <a href="https://join.autogeneral.com.au/swagger-ui/?url=/swagger.json">Auto & General test API</a>
 */
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    private ToDoItemRequestValidator toDoItemAddRequestValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(toDoItemAddRequestValidator);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ToDoItem> createTodoItem(@Valid @RequestBody ToDoItemRequest request) {

        return todoService.createTodoItem(request);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<ToDoItem> viewTodoItem(@PathVariable(value = "id", required = true) String id) {

        return todoService.viewTodoItem(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH)
    public ResponseEntity<ToDoItem> updateTodoItem(@PathVariable(value = "id", required = true) String id, @Valid @RequestBody ToDoItemUpdateRequest request) {

        return todoService.updateTodoItem(id, request);
    }
}