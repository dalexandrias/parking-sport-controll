package com.parkingsport.car.parkingsportcontroll.services;

import com.parkingsport.car.parkingsportcontroll.model.ParkingSportModel;
import com.parkingsport.car.parkingsportcontroll.repository.ParkingSportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingSportService {

    @Autowired
    ParkingSportRepository parkingSportRepository;

    @Transactional
    public ParkingSportModel save(ParkingSportModel parkingSportModel) {
        return parkingSportRepository.save(parkingSportModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSportRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSportNumber(String parkingSportNumber) {
        return parkingSportRepository.existsByParkingSportNumber(parkingSportNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSportRepository.existsByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSportModel> getAll() {
        return parkingSportRepository.findAll();
    }

    public Optional<ParkingSportModel> oneParkingSport(Long id) {
        return parkingSportRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        parkingSportRepository.deleteById(id);
    }
}
