package com.example.controllers;

import com.example.dao.TaskDAO;
import com.example.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private final TaskDAO taskDAO;

    @Autowired
    public TasksController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("tasks", taskDAO.getAll());
        return "tasks/index";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task task) {
        return "tasks/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("task") @Valid Task task,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "tasks/new";
        }
        taskDAO.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskDAO.get(id));
        return "tasks/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") @Valid Task task,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "tasks/edit";
        }
        taskDAO.update(id, task);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        taskDAO.delete(id);
        return "redirect:/tasks";
    }
}
