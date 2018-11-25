package com.autogeneral.techtest.angservice.model.json;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The JSON version of the ToDoItem */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ToDoItem {

    private String id;
    private String text;
    private Boolean completed;
    private String createdAt;
    private String message;

    public ToDoItem() {
        // Need a dummy constructor for Jackson
    }

    public ToDoItem(String id, String text, Boolean completed, String createdAt) {
        this.id = id;
        this.text = text;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("isCompleted")
    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
