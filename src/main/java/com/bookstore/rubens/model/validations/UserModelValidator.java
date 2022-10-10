package com.bookstore.rubens.model.validations;

import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.io.request.UserRequest;
import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
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

    public void validateRelationship(Long id) {
        List<RentModel> rentModel = userRepository.findById(id).get().getRents();

        if (!rentModel.isEmpty()) {
            throw new BusinessException("this user has a rental in progress");
        }
    }

}
