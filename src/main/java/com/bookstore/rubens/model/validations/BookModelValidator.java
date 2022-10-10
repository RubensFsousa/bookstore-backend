package com.bookstore.rubens.model.validations;

import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Enum.StatusRent;
import com.bookstore.rubens.model.io.request.BookRequest;
import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.repository.BookRepository;
import com.bookstore.rubens.repository.PublisherRepository;
import com.bookstore.rubens.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookModelValidator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    public void validateForCreate(BookRequest bookRequest){
        validateName(bookRequest.getName());
        validatePublisher(bookRequest.getPublisherId());
    }

    private void validatePublisher(Long publisher) {

        if (publisherRepository.findById(publisher).isEmpty()){
            throw new BusinessException("Publisher not found");
        }
    }

    private void validateName(String name) {
        bookRepository.findByName(name).ifPresent(bookModel -> {
            throw new BusinessException("Book with this name already registered");
        });
    }

    public void validateRelationship(Long id) {
        List<RentModel> rents = bookRepository.findById(id).get().getRents();

        for (RentModel rentModel : rents) {
            if (rentModel.getStatus().equals(StatusRent.LENDO)) {
                throw new BusinessException("there are rentals registered with this book");
            }
        }
    }
}
