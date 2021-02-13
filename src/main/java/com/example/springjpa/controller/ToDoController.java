package com.example.springjpa.controller;

import com.example.springjpa.domain.model.Status;
import com.example.springjpa.domain.model.ToDo;
import com.example.springjpa.domain.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/", "/todo"})
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping
    public String listView(@RequestParam(required = false) Status status, Model model) {
        model.addAttribute("todos", toDoService.list(status));
        return "list-view";
    }

    @GetMapping("/{id}")
    public String getView(@PathVariable long id, Model model) {
        model.addAttribute("todo", toDoService.get(id));
        return "get-view";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        model.addAttribute("todo", new ToDo());
        return "create-view";
    }

    @PostMapping("/create")
    public String create(ToDo toDo, Model model) {
        toDo = toDoService.create(toDo);
        return "redirect:/todo/" + toDo.getId();
    }

    @GetMapping("/{id}/edit")
    public String editView(@PathVariable long id, Model model) {
        model.addAttribute("todo", toDoService.get(id));
        return "edit-view";
    }


    @PostMapping("/{id}/edit")
    public String edit(@PathVariable long id, ToDo toDo, Model model) {
        toDo = toDoService.update(toDo);
        return "redirect:/todo/" + toDo.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id, Model model) {
        toDoService.delete(id);
        return "redirect:/todo";
    }

    @GetMapping("/{id}/move/{status}")
    public String move(@PathVariable long id, @PathVariable Status status, Model model) {
        toDoService.moveToState(id, status);
        return "redirect:/todo/" + id;
    }

}
