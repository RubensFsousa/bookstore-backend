package com.bookstore.rubens.model.validations;

import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.model.UserModel;
import com.bookstore.rubens.model.io.request.RentRequest;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Enum.StatusRent;
import com.bookstore.rubens.repository.BookRepository;
import com.bookstore.rubens.repository.RentRepository;
import com.bookstore.rubens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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
            throw new BusinessException("Não existem cópias disponíveis deste livro");
        }
    }

    public void validateForCreate(RentRequest rentRequest){
        validateBook(rentRequest.getBookId());
        validateUser(rentRequest.getUserId());
        validateDevolution(rentRequest.getRentDate(), rentRequest.getRentPredict());

    }

    private void validateBook(Long id) {
        if (bookRepository.findById(id).isEmpty()){
            throw new BusinessException("Livro não encontrado");
        }
    }

    private void validateUser(Long id) {
        if (userRepository.findById(id).isEmpty()){
            throw new BusinessException("Leitor não encontrado");
        }
    }

    private void validateDevolution(LocalDate rentDate, LocalDate rentPredict) {
        if (rentDate.isAfter(rentPredict)){
            throw new BusinessException("A data de aluguel não pode ser posterior à data de devolução");
        }
        if (rentDate.isAfter(LocalDate.now())){
            throw new BusinessException("O aluguel deve ser feito na data presente ou anterior");
        }
    }

    public void validateForDelete(Long id) {
        StatusRent status = rentRepository.findById(id).get().getStatus();

        if (status.equals(StatusRent.LENDO)){
            throw new BusinessException("Este aluguel está em progresso");
        }
    }

}
