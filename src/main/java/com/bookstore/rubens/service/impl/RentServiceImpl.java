package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.exception.IdFoundException;
import com.bookstore.rubens.io.request.RentRequest;
import com.bookstore.rubens.model.validations.Mapper.RentMapper;
import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.BookModel;
import com.bookstore.rubens.model.Enum.StatusRent;
import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.model.UserModel;
import com.bookstore.rubens.io.response.RentResponse;
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
        BookModel book = (bookRepository.getById(rentRequest.getBookId()));

        rentValidator.validateForCreate(rentRequest);
        rentValidator.validateBookRent(rentMapper.toRentModel(rentRequest).getBook());
        bookRepository.save(book).setAmount(book.getAmount() -1);
        rentRepository.save(rentMapper.toRentModel(rentRequest));
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
                if (optRent.get().getStatus().equals(StatusRent.IN_PROGRESS)){
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
    public void deleteById(Long id) {
        rentValidator.validateForDelete(id);
        rentRepository.deleteById(id);
    }

}
