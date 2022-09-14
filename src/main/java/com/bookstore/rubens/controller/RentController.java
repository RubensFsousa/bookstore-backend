package com.bookstore.rubens.controller;

import com.bookstore.rubens.io.request.RentRequest;
import com.bookstore.rubens.io.response.RentResponse;
import com.bookstore.rubens.model.validations.Mapper.RentMapper;
import com.bookstore.rubens.service.RentService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "API REST BOOKS")
@RequestMapping("/Rent")
public class RentController {

    private RentService rentService;

    private RentMapper rentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRent(@RequestBody @Valid RentRequest rentRequest) {
        rentService.create(rentRequest);
    }

    @GetMapping
    public ResponseEntity<Page<RentResponse>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RentResponse> getONE(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(rentService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RentResponse> updateRent(@PathVariable(value = "id") Long id, @RequestBody @Valid RentRequest rentRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.update(id, rentRequest));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRent(@PathVariable(value = "id") Long id) {
        rentService.deleteById(id);
    }

}

