package com.bookstore.rubens.service;

import com.bookstore.rubens.model.io.request.BookRequest;
import com.bookstore.rubens.model.io.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    void create(BookRequest bookRequest);

    Page<BookResponse> getAll(Pageable pageable);

    BookResponse getById(Long id);

    BookResponse update(Long id, BookRequest bookRequest);

    void deleteById(Long id);
}
