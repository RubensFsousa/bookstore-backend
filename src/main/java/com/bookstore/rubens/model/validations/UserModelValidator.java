package com.bookstore.rubens.model.validations;

import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.io.request.UserRequest;
import com.bookstore.rubens.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserModelValidator {

    @Autowired
    private UserRepository userRepository;

    public void validateForCreate(UserRequest userRequest){
        validateName(userRequest.getName());
    }

    private void validateName(String name) {
        userRepository.findByName(name).ifPresent(userModel -> {
            throw new BusinessException("User already registered with this name");
        });
    }
}
