package com.bookstore.rubens.model.validations.Mapper;


import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.io.request.RentRequest;
import com.bookstore.rubens.io.response.RentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("RentMapper")
@RequiredArgsConstructor
public class RentMapper {

    private final ModelMapper mapper = new ModelMapper();

    public RentModel toRentModel(RentRequest rent){
        return mapper.map(rent, RentModel.class);
    }

    public RentRequest toRentRequest(RentModel rent){
        return mapper.map(rent, RentRequest.class);
    }

    public RentResponse toRentResponse(RentModel rent){
        return mapper.map(rent, RentResponse.class);
    }

}
