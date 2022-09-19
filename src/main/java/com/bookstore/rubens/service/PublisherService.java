package com.bookstore.rubens.service;

import com.bookstore.rubens.model.io.request.PublisherRequest;
import com.bookstore.rubens.model.io.response.PublisherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublisherService {

    void create(PublisherRequest publisherRequest);

    Page<PublisherResponse> getAll(Pageable pageable);

    PublisherResponse getById(Long id);

    PublisherResponse update(Long id, PublisherRequest publisherRequest);

    void deleteById(Long id);
}
