package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.exception.IdFoundException;
import com.bookstore.rubens.model.io.request.RentRequest;
import com.bookstore.rubens.model.Mapper.RentMapper;
import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Enum.StatusRent;
import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.model.UserModel;
import com.bookstore.rubens.model.io.response.RentResponse;
import com.bookstore.rubens.model.validations.RentModelValidator;
import com.bookstore.rubens.repository.BookRepository;
import com.bookstore.rubens.repository.RentRepository;
import com.bookstore.rubens.repository.UserRepository;
import com.bookstore.rubens.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    @Autowired
    private final RentRepository rentRepository;

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RentMapper rentMapper;

    @Autowired
    private final RentModelValidator rentValidator;

    @Override
    public void create(RentRequest rentRequest) {
        BookModel book = bookRepository.getById(rentRequest.getBookId());
        rentValidator.validateForCreate(rentRequest);
        rentValidator.validateBookRent(book);
        book.setLeaseQuantity(book.getLeaseQuantity() +1);
        book.setAmount(book.getAmount() -1);
        bookRepository.save(book);
        RentModel rentModel = rentMapper.toRentModel(rentRequest);
        rentModel.setStatus(StatusRent.LENDO);
        rentRepository.save(rentModel);

    }

    @Override
    public Page<RentResponse> getAll(Pageable pageable) {
        return rentRepository.findAll(pageable).map(rentMapper::toRentResponse);
    }

    @Override
    public RentResponse getById(Long id) {
        return rentRepository.findById(id).map(rentMapper::toRentResponse).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public RentResponse update(Long id, RentRequest rentRequest){
        Optional<BookModel> optBook = bookRepository.findById(rentRequest.getBookId());
        Optional<UserModel> optLogin = userRepository.findById(rentRequest.getUserId());
        Optional<RentModel> optRent = rentRepository.findById(id);

        if (optBook.isPresent()){
            if (optLogin.isPresent()){
                if (optRent.get().getStatus().equals(StatusRent.LENDO)){
                    throw new BusinessException("This rent has already been made and the book has not been delivered");
                }
            }
        }

        if (optRent.isEmpty()){
            throw new BusinessException("Rent not found");
        }
        RentModel rentModel = rentMapper.toRentModel(rentRequest);
        rentModel.setId(id);
        rentRepository.save(rentModel);

        return rentMapper.toRentResponse(rentModel);
    }

    @Override
    public void devolution(Long id){
        RentModel rentModel = rentRepository.getById(id);
        Optional<RentModel> rent = rentRepository.findById(id);
        Integer copies = rentModel.getBook().getAmount();


        if (rent.isEmpty()){
            throw new IdFoundException(id);
        }

        if (rent.get().getStatus().equals(StatusRent.ATRASADO)) {
            throw new BusinessException("this book has already been delivered");
        }

        if (rent.get().getStatus().equals(StatusRent.ENTREGUE)) {
            throw new BusinessException("this book has already been delivered");
        }

        if (rent.get().getPredictDate().isBefore(LocalDate.now())) {
            rentModel.setStatus(StatusRent.ATRASADO);
        }

        else {
            rentModel.setStatus(StatusRent.ENTREGUE);
        }
        rentModel.setDevolutionDate(LocalDate.now());
        rentModel.getBook().setAmount(copies + 1);

        rentRepository.save(rentModel);
    }

    @Override
    public void deleteById(Long id) {
        RentModel rent = rentRepository.getById(id);
        Integer copies = rent.getBook().getLeaseQuantity();
        rentValidator.validateForDelete(id);
        rentRepository.deleteById(id);
        rent.getBook().setLeaseQuantity(copies -1);


    }

}
