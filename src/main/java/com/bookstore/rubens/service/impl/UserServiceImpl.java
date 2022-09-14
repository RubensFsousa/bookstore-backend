package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.exception.IdFoundException;
import com.bookstore.rubens.model.validations.Mapper.UserMapper;
import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.UserModel;
import com.bookstore.rubens.io.request.UserRequest;
import com.bookstore.rubens.io.response.UserResponse;
import com.bookstore.rubens.model.validations.UserModelValidator;
import com.bookstore.rubens.repository.UserRepository;
import com.bookstore.rubens.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserModelValidator userValidator;


    @Override
    public void create(UserRequest userRequest) {
        userValidator.validateForCreate(userRequest);
        userRepository.save(userMapper.toUserModel(userRequest));
    }

    @Override
    public Page<UserResponse> getAll(Pageable pageable){
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse getById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponse).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest){
        Optional<UserModel> UserModelById = userRepository.findById(id);
        Optional<UserModel> RequestUserName = userRepository.findByName(userRequest.getName());

        if (!RequestUserName.equals(UserModelById)){
            if (UserModelById.isPresent()){
                userValidator.validateForCreate(userRequest);
            }
        }

        if (UserModelById.isEmpty()){
            throw new BusinessException("Login not found");
        }

        UserModel userModel = userMapper.toUserModel(userRequest);
        userModel.setId(id);
        userRepository.save(userModel);

        return userMapper.toUserResponse(userModel);
    }

    @Override
    public void deleteById(Long id) {
        userValidator.validateRelationship(id);
        userRepository.deleteById(id);
    }
}
