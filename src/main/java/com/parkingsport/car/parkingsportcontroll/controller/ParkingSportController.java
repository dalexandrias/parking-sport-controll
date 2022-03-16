package com.parkingsport.car.parkingsportcontroll.controller;

import com.parkingsport.car.parkingsportcontroll.dtos.ParkingSportDto;
import com.parkingsport.car.parkingsportcontroll.model.ParkingSportModel;
import com.parkingsport.car.parkingsportcontroll.services.ParkingSportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-sport")
public class ParkingSportController {

    @Autowired
    ParkingSportService parkingSportService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSport(@RequestBody @Valid ParkingSportDto parkingSportDto){
        if (parkingSportService.existsByLicensePlateCar(parkingSportDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if (parkingSportService.existsByParkingSportNumber(parkingSportDto.getParkingSportNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if (parkingSportService.existsByApartmentAndBlock(parkingSportDto.getApartment(), parkingSportDto.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }

        var parkingSportModel = new ParkingSportModel();
        BeanUtils.copyProperties(parkingSportDto, parkingSportModel);
        parkingSportModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSportService.save(parkingSportModel));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSportModel>> returnAllParkingSport() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSportService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> returnOneParkingSport(@PathVariable(value = "id") Long id) {
        Optional<ParkingSportModel> parkingSportModelOptional = parkingSportService.oneParkingSport(id);
        if (!parkingSportModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Sport Not Found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(parkingSportModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<ParkingSportModel> parkingSportModelOptional = parkingSportService.oneParkingSport(id);
        if (parkingSportModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Sport Not Found!");
        }

        parkingSportService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSport(@PathVariable(value = "id") Long id,
                                                      @RequestBody @Valid ParkingSportDto parkingSportDto) {
        Optional<ParkingSportModel> parkingSportModelOptional = parkingSportService.oneParkingSport(id);
        if (!parkingSportModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Sport Not Found!");
        } else {

            var parkingSportModel = parkingSportModelOptional.get();
            BeanUtils.copyProperties(parkingSportDto, parkingSportModel);
            parkingSportModel.setId(parkingSportModelOptional.get().getId());
            parkingSportModel.setRegistrationDate(parkingSportModelOptional.get().getRegistrationDate());

            return ResponseEntity.status(HttpStatus.OK).body(parkingSportService.save(parkingSportModel));
        }
    }
}
