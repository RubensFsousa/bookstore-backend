package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.model.Mapper.BooksMapper;
import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.PublisherModel;
import com.bookstore.rubens.io.response.BookResponse;
import com.bookstore.rubens.repository.BookRepository;
import com.bookstore.rubens.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BooksMapper booksMapper;
    private final PublisherRepository publisherRepository;

    public BookModel save(BookModel bookModel) {
        Optional<PublisherModel> optPublisher = publisherRepository.findById(bookModel.getPublisher().getId());
        Optional<BookModel> optBook = bookRepository.findByName(bookModel.getName());

        if (optPublisher.isEmpty()){
            throw new BusinessException("Publisher not found");
        }
        if(optBook.isPresent()){
            throw new BusinessException("Book already registered with this name");
        }

        return bookRepository.save(bookModel);
    }

    public BookModel update(Long id, BookModel bookModel){
        Optional<BookModel> optBook = bookRepository.findById(id);
        Optional<BookModel> optBookName = bookRepository.findByName(bookModel.getName());

        if (!optBookName.equals(optBook)){
            if (optBook.isPresent()){
                throw new BusinessException("Book already registered with this name");
            }
        }

        if (optBook.isEmpty()){
            throw new BusinessException("Book not found");
        }
        bookModel.setId(id);
        return bookRepository.save(bookModel);
    }



    public Page<BookResponse> findALL(Pageable pageable) {
        return bookRepository.findAll(pageable).map(booksMapper::toBooksResponse);
    }

    public Optional<BookModel> findById(Long bookId) {
        Optional<BookModel> optBook = bookRepository.findById(bookId);
        if(optBook.isEmpty()){
            throw new BusinessException("Book not found");
        }
        return bookRepository.findById(bookId);
    }

    public void delete(BookModel bookModel) {
         bookRepository.delete(bookModel);
    }
}
