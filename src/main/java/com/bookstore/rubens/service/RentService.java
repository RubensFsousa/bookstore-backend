package com.bookstore.rubens.service;

import com.bookstore.rubens.io.request.RentRequest;
import com.bookstore.rubens.io.response.RentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentService {

    void create(RentRequest rentRequest);

    Page<RentResponse> getAll(Pageable pageable);

    RentResponse getById(Long id);

    RentResponse update(Long id, RentRequest rentRequest);

    void deleteById(Long id);
}
