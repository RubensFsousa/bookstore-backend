package com.bookstore.rubens.model.validations.Mapper;

import com.bookstore.rubens.io.request.BookRequest;
import com.bookstore.rubens.io.response.BookResponse;
import com.bookstore.rubens.model.BookModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("BookMapper")
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper mapper = new ModelMapper();

    public BookModel toBooksModel(BookRequest book){
        return mapper.map(book, BookModel.class);
    }

    public BookResponse toBooksResponse(BookModel book){
        return mapper.map(book, BookResponse.class);
    }

}