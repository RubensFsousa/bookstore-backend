package com.bookstore.rubens.controller;

import com.bookstore.rubens.model.io.request.RentRequest;
import com.bookstore.rubens.model.io.response.RentResponse;
import com.bookstore.rubens.service.RentService;
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
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private RentService rentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRent(@RequestBody @Valid RentRequest rentRequest) {
        rentService.create(rentRequest);
    }

    @GetMapping
    public ResponseEntity<Page<RentResponse>> getAllRents(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RentResponse> getONERent(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(rentService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RentResponse> updateRent(@PathVariable(value = "id") Long id, @RequestBody @Valid RentRequest rentRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.update(id, rentRequest));
    }

    @PutMapping(value = "Devolution/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void devolution(@PathVariable(value = "id") Long id) {
        rentService.devolution(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRent(@PathVariable(value = "id") Long id) {
        rentService.deleteById(id);
    }

}

