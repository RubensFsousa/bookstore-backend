package com.bookstore.rubens.model.Mapper;

import com.bookstore.rubens.model.io.request.BookRequest;
import com.bookstore.rubens.model.io.response.BookResponse;
import com.bookstore.rubens.model.BookModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;


@Component("BookMapper")
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper mapper = new ModelMapper();

    public BookModel toBooksModel(BookRequest book){
        mapper.addMappings(new PropertyMap<BookRequest, BookModel>() {
            @Override
            protected void configure(){
                skip(destination.getId());
            }
        });
        return mapper.map(book, BookModel.class);
    }

    public BookRequest toBooksRequest(BookModel book){
        return mapper.map(book, BookRequest.class);
    }

    public BookResponse toBooksResponse(BookModel book){
        return mapper.map(book, BookResponse.class);
    }

}