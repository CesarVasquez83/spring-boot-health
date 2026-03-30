package com.example.demo.web;

import com.example.demo.domain.Task;
import com.example.demo.service.TaskService;
import com.example.demo.web.dto.TaskCreateRequest;
import com.example.demo.web.dto.TaskResponse;
import com.example.demo.web.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;   // incluye CrossOrigin

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService service;

  public TaskController(TaskService service) {
    this.service = service;
  }

  @GetMapping
  public List<TaskResponse> list() {
    return service.list().stream().map(this::toResponse).toList();
  }

  @GetMapping("/{id}")
  public TaskResponse getOne(@PathVariable Long id) {
    return toResponse(service.getById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse create(@Valid @RequestBody TaskCreateRequest req) {
    Task t = new Task();
    t.setTitle(req.title);
    t.setDescription(req.description);
    if (req.status != null) t.setStatus(req.status);
    return toResponse(service.create(t));
  }

  @PutMapping("/{id}")
  public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest req) {
    return toResponse(service.update(id, req));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  private TaskResponse toResponse(Task t) {
    TaskResponse r = new TaskResponse();
    r.id = t.getId();
    r.title = t.getTitle();
    r.description = t.getDescription();
    r.status = t.getStatus();
    r.createdAt = t.getCreatedAt();
    r.updatedAt = t.getUpdatedAt();
    return r;
  }
}
