package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.exception.IdFoundException;
import com.bookstore.rubens.model.io.request.BookRequest;
import com.bookstore.rubens.model.io.response.BookResponse;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Mapper.BookMapper;
import com.bookstore.rubens.service.BookService;
import com.bookstore.rubens.model.validations.BookModelValidator;
import com.bookstore.rubens.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final BookMapper bookMapper;

    @Autowired
    private final BookModelValidator bookValidator;


    @Override
    public void create(BookRequest bookRequest) {
        bookValidator.validateForCreate(bookRequest);
        bookRepository.save(bookMapper.toBooksModel(bookRequest));
    }

    @Override
    public Page<BookResponse> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toBooksResponse);
    }

    @Override
    public BookResponse getById(Long id) {
        return bookRepository.findById(id).map(bookMapper::toBooksResponse).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public BookResponse update(Long id, BookRequest bookRequest){
        Optional<BookModel> optBook = bookRepository.findById(id);
        Optional<BookModel> optBookName = bookRepository.findByName(bookRequest.getName());

        if (!optBookName.equals(optBook)){
            if (optBook.isPresent()){
                bookValidator.validateForCreate(bookRequest);
            }
        }

        if (optBook.isEmpty()){
            throw new BusinessException("Book not found");
        }
        BookModel bookModel = bookMapper.toBooksModel(bookRequest);
        bookModel.setId(id);
        bookRepository.save(bookModel);

        return bookMapper.toBooksResponse(bookModel);
    }

    public void deleteById(Long id) {
        bookValidator.validateRelationship(id);
        bookRepository.deleteById(id);
    }
}
