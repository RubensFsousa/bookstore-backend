package com.bookstore.rubens.controller;

import com.bookstore.rubens.model.io.request.PublisherRequest;
import com.bookstore.rubens.model.io.response.PublisherResponse;
import com.bookstore.rubens.model.Mapper.PublisherMapper;
import com.bookstore.rubens.service.PublisherService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "API REST BOOKS")
@RequestMapping("/Publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private PublisherMapper publisherMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPublisher(@RequestBody @Valid PublisherRequest publisherRequest) {
        publisherService.create(publisherRequest);
    }

    @GetMapping
    public ResponseEntity<Page<PublisherResponse>> getAllPublishers(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PublisherResponse> getOne(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(publisherService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PublisherResponse> updatePublisher(@PathVariable(value = "id") Long id, @RequestBody @Valid PublisherRequest publisherRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.update(id, publisherRequest));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable(value = "id") Long id) {
        publisherService.deleteById(id);
    }

}