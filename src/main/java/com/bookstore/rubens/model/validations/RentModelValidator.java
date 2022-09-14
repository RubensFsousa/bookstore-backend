package com.bookstore.rubens.model.validations;

import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.io.request.RentRequest;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Enum.StatusRent;
import com.bookstore.rubens.repository.BookRepository;
import com.bookstore.rubens.repository.RentRepository;
import com.bookstore.rubens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RentModelValidator {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public void validateBookRent(BookModel bookModel){
        validateAmount(bookModel.getAmount());

    }

    private void validateAmount(Integer amount) {
        if (amount <= 0){
            throw new BusinessException("There are no copies available for this book");
        }
    }

    public void validateForCreate(RentRequest rentRequest){
        validateBook(rentRequest.getBookId());
        validateUser(rentRequest.getUserId());
        validateDevolution(rentRequest.getRentDate(), rentRequest.getRentPredict());

    }

    private void validateBook(Long id) {
        if (bookRepository.findById(id).isEmpty()){
            throw new BusinessException("Book not found");
        }
    }

    private void validateUser(Long user) {
        if (userRepository.findById(user).isEmpty()){
            throw new BusinessException("User not found");
        }
    }

    private void validateDevolution(LocalDate rentDate, LocalDate rentPredict) {
        if (rentDate.isAfter(rentPredict)){
            throw new BusinessException("The rental date cannot be later than the return date");
        }
        if (rentDate.isAfter(LocalDate.now())){
            throw new BusinessException("The rent must be made on the present or scheduled date");
        }
    }

    public void validateForDelete(Long id) {
        StatusRent status = rentRepository.findById(id).get().getStatus();

        if (status.equals(StatusRent.IN_PROGRESS)){
            throw new BusinessException("this rent is in progress");
        }
    }

}
