package com.test.animal.controller;

import com.test.animal.dto.ResponseDto;
import com.test.animal.entity.Animal;
import com.test.animal.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/save")
    public ResponseEntity<List<ResponseDto>> saveAnimal() {
        List<ResponseDto> responseDtoList = animalService.saveAnimals();
        return ResponseEntity.ok(responseDtoList);
    }
}
