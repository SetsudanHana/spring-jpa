package com.example.springjpa.domain.service.impl;

import com.example.springjpa.domain.exception.NotFoundException;
import com.example.springjpa.domain.model.Status;
import com.example.springjpa.domain.model.ToDo;
import com.example.springjpa.domain.service.ToDoService;
import com.example.springjpa.utils.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ToDoServiceImpl implements ToDoService {

    private Map<Long, ToDo> database = new HashMap<>();

    {
        IntStream.range(0, 10)
                .forEach(i -> {
                    ToDo todo = new ToDo();
                    todo.setName("Name " + i);
                    todo.setDescription("Description " + i);
                    todo.setDueDate(new Date());
                    create(todo);
                });
    }

    @Override
    public List<ToDo> list(Status status) {
        return database.values()
                .stream()
                .filter(toDo -> status == null || toDo.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public ToDo get(long id) {
        return Optional.ofNullable(
            database.get(id)
        ).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public ToDo create(ToDo toDo) {
        toDo.setId(IdGenerator.nextId());
        toDo.setStatus(Status.NEW);
        database.put(toDo.getId(), toDo);
        return toDo;
    }

    @Override
    public ToDo update(ToDo toDo) {
        ToDo existing = get(toDo.getId());
        existing.setName(toDo.getName());
        existing.setDescription(toDo.getDescription());
        existing.setDueDate(toDo.getDueDate());
        database.put(existing.getId(), existing);
        return existing;
    }

    @Override
    public void delete(long id) {
        database.remove(id);
    }

    @Override
    public ToDo moveToState(long id, Status status) {
        ToDo existing = get(id);
        existing.setStatus(status);
        database.put(existing.getId(), existing);
        return existing;
    }
}
