package com.tw.apistackbase.api.todo;

import com.tw.apistackbase.api.todo.response.ResourceWithUrl;
import com.tw.apistackbase.domian.todo.DummyTodoRepository;
import com.tw.apistackbase.domian.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.*;

/**
 * Created by jxzhong on 2017/7/3.
 */
@RestController
@RequestMapping(value = "/todos")
public class TodoResource {
    private DummyTodoRepository todoRepository;

    @Autowired
    public TodoResource(DummyTodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public HttpEntity<Collection<ResourceWithUrl>> getAll() {
        List<ResourceWithUrl> resourceWithUrls = todoRepository.findAll().stream()
                .map(this::toResource)
                .collect(Collectors.toList());
        return new ResponseEntity<>(resourceWithUrls, OK);
    }

    @GetMapping("/{todo-id}")
    public HttpEntity<ResourceWithUrl<Todo>> getTodo(@PathVariable("todo-id") long id) {

        Optional<Todo> todoOptional = todoRepository.findById(id);

        if (!todoOptional.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return respondWithResource(todoOptional.get(), OK);
    }

    @PostMapping(headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl<Todo>> saveTodo(@RequestBody Todo todo) {
        todo.setId((long) (todoRepository.findAll().size() + 1));
        todoRepository.save(todo);

        return respondWithResource(todo, CREATED);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteOneTodo(@PathVariable("todo-id") Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);

        if (todoOptional.isPresent()) {
            todoRepository.delete(todoOptional.get());
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{todo-id}", headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl<Todo>> updateTodo(@PathVariable("todo-id") Long id, @RequestBody Todo newTodo) {
        Optional<Todo> todoOptional = todoRepository.findById(id);

        if (!todoOptional.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        } else if (newTodo == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        todoRepository.delete(todoOptional.get());

        Todo mergedTodo = todoOptional.get().merge(newTodo);
        todoRepository.save(mergedTodo);

        return respondWithResource(mergedTodo, OK);
    }


    private String getHref(Todo todo) {
        return linkTo(methodOn(this.getClass()).getTodo(todo.getId())).withSelfRel().getHref();
    }

    private ResourceWithUrl<Todo> toResource(Todo todo) {
        return new ResourceWithUrl<>(todo, getHref(todo));
    }

    private HttpEntity<ResourceWithUrl<Todo>> respondWithResource(Todo todo, HttpStatus statusCode) {
        ResourceWithUrl<Todo> resourceWithUrl = toResource(todo);

        return new ResponseEntity<>(resourceWithUrl, statusCode);
    }
}
