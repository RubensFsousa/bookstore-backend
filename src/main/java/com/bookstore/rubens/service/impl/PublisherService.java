package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.model.Mapper.PublisherMapper;
import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.PublisherModel;
import com.bookstore.rubens.io.response.PublisherResponse;
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
public class PublisherService {

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;


    public PublisherModel save(PublisherModel publisherModel) {
        Optional<PublisherModel> optPublisher = publisherRepository.findByName(publisherModel.getName());

        if (optPublisher.isPresent()){
            throw new BusinessException("Publisher with this name already registered");
        }

        return publisherRepository.save(publisherModel);
    }

    public PublisherModel update(Long id, PublisherModel publisherModel){
        Optional<PublisherModel> optPublisherName = publisherRepository.findByName(publisherModel.getName());
        Optional<PublisherModel> optPublisher = publisherRepository.findById(id);

        if (!optPublisherName.equals(optPublisher)){
            if (optPublisher.isPresent()){
                throw new BusinessException("Publisher already registered with this name");
            }
        }

        if (optPublisher.isEmpty()){
            throw new BusinessException("Publisher not found");
        }
        publisherModel.setId(id);
        return publisherRepository.save(publisherModel);
    }
    public Page<PublisherResponse> findALL(Pageable pageable) {
        return publisherRepository.findAll(pageable)
                .map(publisherMapper::toPublisherResponse);
    }

    public Optional<PublisherModel> findById(Long publisherId) {
        Optional<PublisherModel> optPublisher = publisherRepository.findById(publisherId);

        if (optPublisher.isEmpty()) {
            throw new BusinessException("Publisher not found");
        }
        return publisherRepository.findById(publisherId);
    }

    public void delete(PublisherModel publisherModel) {
        publisherRepository.delete(publisherModel);
    }
}
