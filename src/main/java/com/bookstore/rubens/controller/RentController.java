package com.bookstore.rubens.controller;

import com.bookstore.rubens.model.Mapper.RentMapper;
import com.bookstore.rubens.model.RentModel;
import com.bookstore.rubens.io.request.RentRequest;
import com.bookstore.rubens.io.response.RentResponse;
import com.bookstore.rubens.service.impl.RentService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "API REST BOOKS")
@RequestMapping("/Rent")
public class RentController {

    private final RentMapper rentMapper;
    private final RentService rentService;

    @PostMapping
    public ResponseEntity<RentResponse> saveRent(@RequestBody @Valid RentRequest rent) {
        RentModel rentModel = rentMapper.toRentModel(rent);
        RentModel savedRent = rentService.save(rentModel);
        RentResponse rentResponse = rentMapper.toRentResponse(savedRent);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentResponse);
    }

    @GetMapping
    public ResponseEntity<Page<RentResponse>> getALLRents(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.findALL(pageable));
    }

    @GetMapping(value = "/{id}")

    public ResponseEntity<RentResponse> getONERent(@PathVariable(value = "id") Long id) {
        Optional<RentModel> optRent = rentService.findById(id);
        RentResponse rentResponse = rentMapper.toRentResponse(optRent.get());
        return ResponseEntity.status(HttpStatus.OK).body(rentResponse);
    }

    @DeleteMapping(value = "/{id}")

    public ResponseEntity<Object> deleteRent(@PathVariable(value = "id") Long id) {
        Optional<RentModel> rentModelOptional = rentService.findById(id);
        rentService.delete(rentModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Rent delete with success");
    }

    @PutMapping(value = "/{id}")

    public ResponseEntity<RentResponse> updateRent(@PathVariable(value = "id") Long id, @RequestBody @Valid RentRequest rent) {
        RentModel rentModel = rentMapper.toRentModel(rent);
        RentModel savedRent = rentService.update(id, rentModel);
        RentResponse rentResponse = rentMapper.toRentResponse(savedRent);
        return ResponseEntity.status(HttpStatus.OK).body(rentResponse);

    }
}

