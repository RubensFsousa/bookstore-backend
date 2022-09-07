package com.bookstore.rubens.controller;

import com.bookstore.rubens.model.Mapper.UserMapper;
import com.bookstore.rubens.model.UserModel;
import com.bookstore.rubens.io.request.UserRequest;
import com.bookstore.rubens.io.response.UserResponse;
import com.bookstore.rubens.service.UserService;
import com.bookstore.rubens.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid UserRequest userRequest) {
        userService.create(userRequest);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long id, @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        userService.deleteById(id);
    }
}
