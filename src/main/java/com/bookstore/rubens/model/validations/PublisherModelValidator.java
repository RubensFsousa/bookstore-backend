package com.bookstore.rubens.model.validations;

import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.exception.IdFoundException;
import com.bookstore.rubens.model.io.request.PublisherRequest;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.repository.BookRepository;
import com.bookstore.rubens.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PublisherModelValidator {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookRepository bookRepository;

    public void validateForCreate(PublisherRequest publisherRequest) {
        validateName(publisherRequest.getName());

    }

    private void validateName(String name) {
        publisherRepository.findByName(name).ifPresent(publisherModel -> {
            throw new BusinessException("Publisher already registered");
        });
    }

    public void validateId(Long id){
        if (publisherRepository.findById(id).isEmpty()){
            throw new IdFoundException(id);
        };
    }

    public void validateRelationship(Long id) {
        List<BookModel> bookModel = publisherRepository.findById(id).get().getBooks();

        if (!bookModel.isEmpty()){
            throw new BusinessException("there are books registered with this publisher");
        }
    }

}