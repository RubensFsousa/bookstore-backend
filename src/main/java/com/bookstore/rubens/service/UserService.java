package com.bookstore.rubens.service;

import com.bookstore.rubens.model.io.request.UserRequest;
import com.bookstore.rubens.model.io.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void create(UserRequest userRequest);

    Page<UserResponse> getAll(Pageable pageable);

    UserResponse getById(Long id);

    UserResponse update(Long id, UserRequest userRequest);

    void deleteById(Long id);
}


