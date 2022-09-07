package com.bookstore.rubens.controller;

import com.bookstore.rubens.model.Mapper.PublisherMapper;
import com.bookstore.rubens.model.PublisherModel;
import com.bookstore.rubens.io.request.PublisherRequest;
import com.bookstore.rubens.io.response.PublisherResponse;
import com.bookstore.rubens.service.impl.PublisherService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "API REST BOOKS")
@RequestMapping("/Publisher")
public class PublisherController {
    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    @PostMapping
    public ResponseEntity<PublisherResponse> savePublisher(@RequestBody @Valid PublisherRequest publisher) {
        PublisherModel publisherModel = publisherMapper.toPublisherModel(publisher);
        PublisherModel savedPublisher = publisherService.save(publisherModel);
        PublisherResponse publisherResponse = publisherMapper.toPublisherResponse(savedPublisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherResponse);
    }

    @GetMapping
    public ResponseEntity<Page<PublisherResponse>> getALLPublisher(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.findALL(pageable));
    }

    @GetMapping(value = "/{id}")

    public ResponseEntity<PublisherResponse> getONEPublisher(@PathVariable(value = "id") Long id) {
        Optional<PublisherModel> optPublisher = publisherService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(publisherMapper.toPublisherResponse(optPublisher.get()));
    }

    @DeleteMapping(value = "/{id}")

    public ResponseEntity<Object> deletePublisher(@PathVariable(value = "id") Long id) {
        Optional<PublisherModel> publisherModelOptional = publisherService.findById(id);
        publisherService.delete(publisherModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Publisher delete with success");
    }

    @PutMapping(value = "/{id}")

    public ResponseEntity<PublisherResponse> updatePublisher(@PathVariable(value = "id") Long id, @RequestBody @Valid PublisherRequest publisher) {
        PublisherModel publisherModel = publisherMapper.toPublisherModel(publisher);
        PublisherModel savedPublisher = publisherService.update(id,publisherModel);
        PublisherResponse publisherResponse = publisherMapper.toPublisherResponse(savedPublisher);
        return ResponseEntity.status(HttpStatus.OK).body(publisherResponse);
    }
}