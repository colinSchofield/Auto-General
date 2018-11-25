package com.autogeneral.techtest.angservice.web;

import com.autogeneral.techtest.angservice.model.json.BalanceTestResult;
import com.autogeneral.techtest.angservice.service.BracketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The TaskController RESTful Controller takes requests from the caller and then passes them through to the BracketService,
 * where all the business logic is contained. Results are returned as per the API definition.
 *
 * @see <a href="https://join.autogeneral.com.au/swagger-ui/?url=/swagger.json">Auto & General test API</a>
 */
@RestController
@RequestMapping(value = "/tasks")
public class TasksController {

    @Autowired
    private BracketService bracketService;

    @RequestMapping(value = "validateBrackets", method = RequestMethod.GET)
    public ResponseEntity<BalanceTestResult> validateBrackets(@RequestParam(value = "input", required = true) String input) {

        return bracketService.validateBrackets(input);
    }
}