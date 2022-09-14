package com.bookstore.rubens.model.validations.Mapper;

import com.bookstore.rubens.io.request.PublisherRequest;
import com.bookstore.rubens.io.response.PublisherResponse;
import com.bookstore.rubens.model.PublisherModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("PublisherMapper")
@RequiredArgsConstructor
public class PublisherMapper {

    private final ModelMapper mapper = new ModelMapper();

    public PublisherModel toPublisherModel(PublisherRequest publisher){
        return mapper.map(publisher, PublisherModel.class);
    }

    public PublisherResponse toPublisherResponse(PublisherModel publisher){
        return mapper.map(publisher, PublisherResponse.class);
    }

}