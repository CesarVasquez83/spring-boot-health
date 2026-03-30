package com.example.demo.service;

import com.example.demo.domain.Task;
import com.example.demo.domain.User;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.web.dto.TaskUpdateRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

  private final TaskRepository repo;
  private final UserRepository userRepository;

  public TaskService(TaskRepository repo, UserRepository userRepository) {
    this.repo = repo;
    this.userRepository = userRepository;
  }

  private User getCurrentUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public List<Task> list() {
    return repo.findByUser(getCurrentUser());
  }

  public Task create(Task task) {
    task.setUser(getCurrentUser());
    return repo.save(task);
  }

  public Task getById(Long id) {
    Task task = repo.findById(id)
        .orElseThrow(() -> new TaskNotFoundException(id));
    if (!task.getUser().getId().equals(getCurrentUser().getId())) {
      throw new TaskNotFoundException(id);
    }
    return task;
  }

  @Transactional
  public Task update(Long id, TaskUpdateRequest req) {
    Task t = getById(id);
    if (req.title != null && !req.title.isBlank()) t.setTitle(req.title);
    if (req.description != null) t.setDescription(req.description);
    if (req.status != null) t.setStatus(req.status);
    return repo.save(t);
  }

  @Transactional
  public void delete(Long id) {
    Task t = getById(id);
    repo.delete(t);
  }
}


