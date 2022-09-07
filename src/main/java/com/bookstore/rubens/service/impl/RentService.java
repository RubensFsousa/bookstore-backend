package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.model.Mapper.RentMapper;
import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Enum.StatusRent;
import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.model.UserModel;
import com.bookstore.rubens.io.response.RentResponse;
import com.bookstore.rubens.repository.BookRepository;
import com.bookstore.rubens.repository.RentRepository;
import com.bookstore.rubens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RentService {
    private final RentRepository rentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private final RentMapper rentMapper;

    public RentModel save(RentModel rentModel) {
        Optional<UserModel> optLogin = userRepository.findById(rentModel.getUser().getId()) ;
        Optional<BookModel> optBook = bookRepository.findById(rentModel.getBook().getId());
        Integer bookAmount = rentModel.getBook().getAmount();


        if (optLogin.isEmpty()){
            throw new BusinessException("User not found");
        }
        if (optBook.isEmpty()){
            throw new BusinessException("Book not found");
        }

        if (rentModel.getRentDate().isBefore(LocalDate.now())){
            throw new BusinessException("The rent must be made on the present or scheduled date");
        }

        if (rentModel.getRentDate().isBefore(rentModel.getPredictDate())){
            throw new BusinessException("The rental date cannot be later than the return date");
        }

        if (rentModel.getBook().getAmount() <= 0){
            throw new BusinessException("the rent must be made on the present or scheduled date");
        }

        else {
            bookAmount--;
        }
        rentModel.getBook().setAmount(bookAmount);

        return rentRepository.save(rentModel);
    }

    public RentModel update(Long id, RentModel rentsModel){
        Optional<BookModel> optBook = bookRepository.findByName(rentsModel.getBook().getName());
        Optional<UserModel> optLogin = userRepository.findByName(rentsModel.getUser().getName());
        Optional<RentModel> optRent = rentRepository.findById(id);

        if (optBook.isPresent()){
            if (optLogin.isPresent()){
                if (optRent.get().getStatus().equals(StatusRent.IN_PROGRESS)){
                    throw new BusinessException("This rent has already been made and the book has not been delivered");
                }
            }
        }

        if (optRent.isEmpty()){
            throw new BusinessException("Rent not found");
        }
        rentsModel.setId(id);
        return save(rentsModel);
    }

    public Page<RentResponse> findALL(Pageable pageable) {
        return rentRepository.findAll(pageable)
                .map(rentMapper::toRentResponse);
    }
    public Optional<RentModel> findById(Long id) {
        Optional<RentModel> optRent = rentRepository.findById(id);
        if (optRent.isEmpty()) {
            throw new BusinessException("Rental record not found");
        }
        return rentRepository.findById(id);
    }

    public void delete(RentModel rentModel) {
        rentRepository.delete(rentModel);
    }

}
