package com.bookstore.rubens.controller;

import com.bookstore.rubens.model.Mapper.BookMapper;
import com.bookstore.rubens.model.io.request.BookRequest;
import com.bookstore.rubens.model.io.response.BookResponse;
import com.bookstore.rubens.service.BookService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "API REST BOOKS")
@RequestMapping("/bookstore")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody @Valid BookRequest bookRequest) {
        bookService.create(bookRequest);
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponse> getOneBook(@PathVariable(value = "id")Long id){
        return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable(value = "id")Long id, @RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.update(id, bookRequest));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable(value = "id")Long id){
        bookService.deleteById(id);
    }

}
