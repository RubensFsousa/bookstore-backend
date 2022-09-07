package com.bookstore.rubens.controller;

import com.bookstore.rubens.model.Mapper.BooksMapper;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.io.request.BookRequest;
import com.bookstore.rubens.io.response.BookResponse;
import com.bookstore.rubens.service.impl.BookService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "API REST BOOKS")
@RequestMapping("/BookStore")
public class BookController {
    private final BooksMapper booksMapper;
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(@RequestBody @Valid BookRequest book) {
        BookModel bookModel = booksMapper.toBooksModel(book);
        BookModel savedBook = bookService.save(bookModel);
        BookResponse bookResponse = booksMapper.toBooksResponse(savedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> listAll(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findALL(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponse> getONEBook(@PathVariable(value = "id")Long id){
        Optional<BookModel> optBook = bookService.findById(id);
        BookResponse bookResponse = booksMapper.toBooksResponse(optBook.get());
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id")Long id){
        Optional<BookModel> booksModelOptional = bookService.findById(id);
        bookService.delete(booksModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Book delete with success");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable(value = "id")Long id, @RequestBody @Valid BookRequest book) {
        BookModel bookModel = booksMapper.toBooksModel(book);
        BookModel savedBook = bookService.update(id, bookModel);
        BookResponse bookResponse = booksMapper.toBooksResponse(savedBook);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

}
