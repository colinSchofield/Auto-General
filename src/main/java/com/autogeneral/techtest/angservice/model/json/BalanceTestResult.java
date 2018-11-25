package com.autogeneral.techtest.angservice.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/** The JSON Response object used for validating the brackets */
public class BalanceTestResult {

    private String input;
    private Boolean balanced;

    public BalanceTestResult() {
        // Need a dummy constructor for Jackson
    }

    public BalanceTestResult(String input, Boolean balanced) {
        this.input = input;
        this.balanced = balanced;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @JsonProperty("isBalanced")
    public Boolean getBalanced() {
        return balanced;
    }

    public void setBalanced(Boolean balanced) {
        this.balanced = balanced;
    }
}
