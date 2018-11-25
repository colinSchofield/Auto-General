package com.autogeneral.techtest.angservice.service;

import com.autogeneral.techtest.angservice.model.json.BalanceTestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Stack;

/**
 * The BracketService contains the business logic for matching brackets. The approached taken is the use a Stack. By pushing
 * the brackets onto the stack, detecting a reverse bracket, then popping the last bracket and comparing the type of bracket.
 * If the items are different, the brackets do not match (i.e. fail fast). Furthermore, Whenever the last element in the
 * input is reached, the stack <i>must</i> be empty.
 * <br/>
 * This provides an elegant solution to this matching problem. If we consider Big O notation, this will require O(n) for both
 * the time and the memory required.
 */
@Service
public class BracketService {

    private final Logger log = LoggerFactory.getLogger(BracketService.class);

    /**
     * The bracket types, both forward and reversed
     */
    private static final Character FORWARD_ROUND = '(';
    private static final Character FORWARD_CURLY = '{';
    private static final Character FORWARD_SQUARE = '[';
    private static final Character REVERSE_ROUND = ')';
    private static final Character REVERSE_CURLY = '}';
    private static final Character REVERSE_SQUARE = ']';

    public ResponseEntity<BalanceTestResult> validateBrackets(String input) {

        log.info("About to validate the brackets contained in the string '{}'.", input);
        Assert.notNull(input, "Value of input must not be null.");

        Stack<Character> stack = new Stack<>();
        for (Character c : input.toCharArray()) {

            if (isForwardBracket(c)) {

                log.debug("Pushing {} onto the stack - {} items contained on the stack", c, stack.size());
                stack.push(c);

            } else if (isReverseBracket(c)) {

                log.debug("Reverse bracket {} found", c);
                if (!bracketMatches(stack, c)) {
                    // Fail fast
                    return buildReturnValue(input, false);
                }
            } else {
                log.debug("Ignoring non-bracket character {}.", c);
            }
        }

        if (stack.size() > 0) {
            log.debug("Brackets do not match, as there are still {} unmatched brackets.", stack.size());
            return buildReturnValue(input, false);
        } else {
            return buildReturnValue(input, true);
        }
    }

    private boolean isForwardBracket(Character c) {
        return c.equals(FORWARD_ROUND) || c.equals(FORWARD_CURLY) || c.equals(FORWARD_SQUARE);
    }

    private boolean isReverseBracket(Character c) {
        return c.equals(REVERSE_ROUND) || c.equals(REVERSE_CURLY) || c.equals(REVERSE_SQUARE);
    }

    private boolean bracketMatches(Stack<Character> stack, Character bracket) {

        if (stack.size() == 0) {
            log.debug("The Stack is empty!");
            return false;
        }

        Character reversBracket = stack.pop();

        if (bracket.equals(REVERSE_ROUND)) {
            return reversBracket.equals(FORWARD_ROUND);
        } else if (bracket.equals(REVERSE_CURLY)) {
            return reversBracket.equals(FORWARD_CURLY);
        } else if (bracket.equals(REVERSE_SQUARE)) {
            return reversBracket.equals(FORWARD_SQUARE);
        } else {
            log.warn("Bracket mismatch - brackets were: {}, {}.", bracket, reversBracket);
            return false;
        }
    }

    /** A value is returned as a ResponseEntity (including a Status Code) in JSON format */
    private ResponseEntity<BalanceTestResult> buildReturnValue(String input, Boolean isBalanced) {
        log.info("brackets are balanced? {}", isBalanced);
        BalanceTestResult result = new BalanceTestResult(input, isBalanced);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
