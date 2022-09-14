package com.bookstore.rubens.model.validations.Mapper;


import com.bookstore.rubens.model.UserModel;
import com.bookstore.rubens.io.request.UserRequest;
import com.bookstore.rubens.io.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("LogsMapper")
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
