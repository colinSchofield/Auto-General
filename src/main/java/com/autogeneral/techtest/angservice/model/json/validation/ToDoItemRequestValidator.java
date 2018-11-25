package com.autogeneral.techtest.angservice.model.json.validation;

import com.autogeneral.techtest.angservice.model.json.ToDoItemRequest;
import com.autogeneral.techtest.angservice.model.json.ToDoItemUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * perform validation on the TodoItem Add/Update Request
 * <br/>
 * Note: This class shares the responsibility of validating both the create <i>and</i> the update requests
 * */
@Component
public class ToDoItemRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) { return ToDoItemRequest.class.isAssignableFrom(clazz); }

    @Override
    public void validate(Object target, Errors errors) {

        ToDoItemRequest todoRequest = (ToDoItemRequest) target;

        if (target instanceof ToDoItemUpdateRequest) {

            ToDoItemUpdateRequest update = (ToDoItemUpdateRequest) target;

            if (StringUtils.isEmpty(update.getText()) && update.getCompleted() == null) {

                errors.reject("Input.InvalidJson", "Request does not include either an updated text or completion status");
            } else if (!StringUtils.isEmpty(todoRequest.getText()) && todoRequest.getText().length() > 50) {

                errors.reject("Input.InvalidJson", "Must be between 1 and 50 chars long");
            }
        } else if (StringUtils.isEmpty(todoRequest.getText()) || todoRequest.getText().length() > 50) {

            errors.reject("Input.InvalidJson", "Must be between 1 and 50 chars long");
        }
    }
}