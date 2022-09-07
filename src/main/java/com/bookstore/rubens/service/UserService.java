package com.bookstore.rubens.service;

import com.bookstore.rubens.io.request.UserRequest;
import com.bookstore.rubens.io.response.UserResponse;
import com.bookstore.rubens.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void create(UserRequest userRequest);

    Page<UserResponse> getAll(Pageable pageable);

    UserResponse getById (Long id);

    void deleteById(Long id);

    UserResponse update(Long id, UserRequest userRequest);
}


