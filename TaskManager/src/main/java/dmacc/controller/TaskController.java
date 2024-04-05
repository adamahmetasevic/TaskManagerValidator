package dmacc.controller;

import dmacc.model.Task;
import dmacc.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String index() {
        return "index"; // Returns the index.html template
    }

    @GetMapping("/tasklist")
    public String taskList(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasklist"; 
    }

    @GetMapping("/add-task")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task"; // Returns the add-task.html template
    }

    @PostMapping("/save-task")
    public String saveTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasklist"; // Redirects to the task list page after saving the task
    }

    @GetMapping("/edit-task/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
            return "edit-task"; // Returns the edit-task.html template
        } else {
            return "redirect:/tasklist"; // Redirects to the task list page if the task is not found
        }
    }

    @PostMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasklist"; // Redirects to the task list page after deleting the task
    }

}
