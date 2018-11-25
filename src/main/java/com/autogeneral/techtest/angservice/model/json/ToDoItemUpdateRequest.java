package com.autogeneral.techtest.angservice.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/** JSON object used as during the Patch (i.e. update) ToDoItem request */
public class ToDoItemUpdateRequest extends ToDoItemRequest {

    private Boolean completed;

    @JsonProperty("isCompleted")
    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
