package com.bookstore.rubens.model.Mapper;


import com.bookstore.rubens.model.io.request.UserRequest;
import com.bookstore.rubens.model.io.response.UserResponse;
import com.bookstore.rubens.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("UserMapper")
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper = new ModelMapper();

    public UserModel toUserModel(UserRequest rent){
        return mapper.map(rent, UserModel.class);
    }

    public UserResponse toUserResponse(UserModel rent){
        return mapper.map(rent, UserResponse.class);
    }

}
