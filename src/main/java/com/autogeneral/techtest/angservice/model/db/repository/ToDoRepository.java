package com.autogeneral.techtest.angservice.model.db.repository;

import com.autogeneral.techtest.angservice.model.db.ToDo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
