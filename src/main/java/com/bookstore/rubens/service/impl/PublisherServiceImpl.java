package com.bookstore.rubens.service.impl;

import com.bookstore.rubens.exception.IdFoundException;
import com.bookstore.rubens.model.io.request.PublisherRequest;
import com.bookstore.rubens.model.Mapper.PublisherMapper;
import com.bookstore.rubens.exception.BusinessException;
import com.bookstore.rubens.model.PublisherModel;
import com.bookstore.rubens.model.io.response.PublisherResponse;
import com.bookstore.rubens.service.PublisherService;
import com.bookstore.rubens.model.validations.PublisherModelValidator;
import com.bookstore.rubens.repository.PublisherRepository;
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
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private final PublisherRepository publisherRepository;

    @Autowired
    private final PublisherMapper publisherMapper;

    @Autowired
    private final PublisherModelValidator publisherValidator;

    @Override
    public void create(PublisherRequest publisherRequest) {
        publisherValidator.validateForCreate(publisherRequest);
        publisherRepository.save(publisherMapper.toPublisherModel(publisherRequest));
    }

    @Override
    public Page<PublisherResponse> getAll(Pageable pageable) {
        return publisherRepository.findAll(pageable).map(publisherMapper::toPublisherResponse);
    }

    public PublisherResponse getById(Long id) {
        return publisherRepository.findById(id).map(publisherMapper::toPublisherResponse).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public PublisherResponse update(Long id, PublisherRequest publisherRequest){
        Optional<PublisherModel> publisherModelById = publisherRepository.findById(id);
        Optional<PublisherModel> requestPublisherName = publisherRepository.findByName(publisherRequest.getName());

        if (!publisherModelById.equals(requestPublisherName)){
            if (requestPublisherName.isPresent()){
                publisherValidator.validateForCreate(publisherRequest);
            }
        }

        if (publisherModelById.isEmpty()){
            throw new BusinessException("Publisher not found");
        }
        PublisherModel publisherModel = publisherMapper.toPublisherModel(publisherRequest);
        publisherModel.setId(id);
        publisherRepository.save(publisherModel);

        return publisherMapper.toPublisherResponse(publisherModel);
    }

    @Override
    public void deleteById(Long id) {
        publisherValidator.validateRelationship(id);
        publisherRepository.deleteById(id);
    }
}
