package com.bookstore.rubens.model.Mapper;

import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.io.request.BookRequest;
import com.bookstore.rubens.io.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("BookMapper")
@RequiredArgsConstructor
public class BooksMapper {

    private final ModelMapper mapper = new ModelMapper();

    public BookModel toBooksModel(BookRequest book){
        return mapper.map(book, BookModel.class);
    }

    public BookRequest toBooksRequest(BookModel book){
        return mapper.map(book, BookRequest.class);
    }

    public BookResponse toBooksResponse(BookModel book){
        return mapper.map(book, BookResponse.class);
    }

    public List<BookResponse> toBookList(List<BookModel> books){
        return books.stream()
                .map(this::toBooksResponse)
                .collect(Collectors.toList());
    }
}

