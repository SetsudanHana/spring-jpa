package com.example.springjpa.domain.service;

import com.example.springjpa.domain.model.Status;
import com.example.springjpa.domain.model.ToDo;

import java.util.List;

public interface ToDoService {

    List<ToDo> list(Status status);
    ToDo get(long id);
    ToDo create(ToDo toDo);
    ToDo update(ToDo toDo);
    void delete(long id);
    ToDo moveToState(long id, Status status);

}
