package com.bookstore.rubens.model.Mapper;

import com.bookstore.rubens.model.PublisherModel;
import com.bookstore.rubens.io.request.PublisherRequest;
import com.bookstore.rubens.io.response.PublisherResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("PublisherMapper")
@RequiredArgsConstructor
public class PublisherMapper {

    private final ModelMapper mapper = new ModelMapper();


    public PublisherModel toPublisherModel(PublisherRequest publisher){
        return mapper.map(publisher, PublisherModel.class);
    }

    public PublisherRequest toPublisherRequest(PublisherModel publisher){
        return mapper.map(publisher, PublisherRequest.class);
    }

    public PublisherResponse toPublisherResponse(PublisherModel publisher){
        return mapper.map(publisher, PublisherResponse.class);
    }

    public List<PublisherResponse> toPublisherList(List<PublisherModel> publishes){
        return publishes.stream()
                .map(this::toPublisherResponse)
                .collect(Collectors.toList());
    }

}

