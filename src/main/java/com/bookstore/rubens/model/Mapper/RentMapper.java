package com.bookstore.rubens.model.Mapper;


import com.bookstore.rubens.model.io.request.RentRequest;
import com.bookstore.rubens.model.io.response.RentResponse;
import com.bookstore.rubens.model.RentModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component("RentMapper")
@RequiredArgsConstructor
public class RentMapper {

    private final ModelMapper mapper = new ModelMapper();

    public RentModel toRentModel(RentRequest rent){

        TypeMap<RentRequest, RentModel> typeMap = mapper.getTypeMap(RentRequest.class, RentModel.class);

        if (typeMap == null) {
            mapper.addMappings(new PropertyMap<RentRequest, RentModel>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
        return mapper.map(rent, RentModel.class);
    }

    public RentResponse toRentResponse(RentModel rent){
        return mapper.map(rent, RentResponse.class);
    }

}
