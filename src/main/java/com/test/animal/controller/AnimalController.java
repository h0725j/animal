package com.test.animal.controller;

import com.test.animal.dto.AnimalResponseDto;
import com.test.animal.dto.ShelterResponseDto;
import com.test.animal.service.AnimalService;
import com.test.animal.service.ShelterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @Autowired
    private ShelterService shelterService;


    @GetMapping("/api/animal/save")
    public ResponseEntity<List<AnimalResponseDto>> saveAnimal() {
        List<AnimalResponseDto> animalResponseDtoList = animalService.saveAnimals();
        return ResponseEntity.ok(animalResponseDtoList);
    }

    @GetMapping("/api/shelter/save")
    public ResponseEntity<List<ShelterResponseDto>> saveShelter() {
        List<ShelterResponseDto> shelterResponseDtoList = shelterService.saveShelter();
        return ResponseEntity.ok(shelterResponseDtoList);
    }

}
