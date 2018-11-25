package com.autogeneral.techtest.angservice.service;

import com.autogeneral.techtest.angservice.model.json.BalanceTestResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class BracketServiceTest {

    BracketService bracketService;

    private static final String SIMPLE_MATCH = "{[()]}";
    private static final String SIMPLE_NON_MATCH = "{[()]}(";
    private static final String SIMPLE_MATCH_EXTRA_CHARACTERS = "{a[b(c)d]e}f";
    private static final String COMPLICATED_MATCH = "[{()()}({[]})]({}[({})])((((((()[])){}))[]{{{({({({{{{{{}}}}}})})})}}}))[][][]";
    private static final String COMPLICATED_NON_MATCH = "[{()()}({[]})]({}[({})])((((((()[])){}))[]{{{({({({{{{{{}}}}}})})})}}}))[][][]{";
    private static final String BLANK_VALUE = "";

    @Before
    public void setup() {

        bracketService = new BracketService();
    }

    @Test
    public void simpleMatch() {

        ResponseEntity<BalanceTestResult> result = bracketService.validateBrackets(SIMPLE_MATCH);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getInput(), SIMPLE_MATCH);
        assertEquals(result.getBody().getBalanced(), true);
    }

    @Test
    public void simpleNonMatch() {

        ResponseEntity<BalanceTestResult> result = bracketService.validateBrackets(SIMPLE_NON_MATCH);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getInput(), SIMPLE_NON_MATCH);
        assertEquals(result.getBody().getBalanced(), false);
    }

    @Test
    public void simpleMatchExtraCharacters() {

        ResponseEntity<BalanceTestResult> result = bracketService.validateBrackets(SIMPLE_MATCH_EXTRA_CHARACTERS);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getInput(), SIMPLE_MATCH_EXTRA_CHARACTERS);
        assertEquals(result.getBody().getBalanced(), true);
    }

    @Test
    public void complicatedMatch() {

        ResponseEntity<BalanceTestResult> result = bracketService.validateBrackets(COMPLICATED_MATCH);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getInput(), COMPLICATED_MATCH);
        assertEquals(result.getBody().getBalanced(), true);
    }

    @Test
    public void complicatedNonMatch() {

        ResponseEntity<BalanceTestResult> result = bracketService.validateBrackets(COMPLICATED_NON_MATCH);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getInput(), COMPLICATED_NON_MATCH);
        assertEquals(result.getBody().getBalanced(), false);
    }

    @Test
    public void blankValueOfInput() {

        ResponseEntity<BalanceTestResult> result = bracketService.validateBrackets(BLANK_VALUE);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getInput(), BLANK_VALUE);
        assertEquals(result.getBody().getBalanced(), true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullValueOfInput() {

        ResponseEntity<BalanceTestResult> result = bracketService.validateBrackets(null);
    }

}