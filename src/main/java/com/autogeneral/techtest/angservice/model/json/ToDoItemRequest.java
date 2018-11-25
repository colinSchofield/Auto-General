package com.autogeneral.techtest.angservice.model.json;

/** JSON object used as during the POST Create TodoItem request */
public class ToDoItemRequest {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
