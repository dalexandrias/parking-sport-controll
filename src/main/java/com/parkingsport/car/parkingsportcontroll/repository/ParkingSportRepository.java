package com.parkingsport.car.parkingsportcontroll.repository;

import com.parkingsport.car.parkingsportcontroll.model.ParkingSportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSportRepository extends JpaRepository<ParkingSportModel, Long> {

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSportNumber(String parkingSportNumber);

    boolean existsByApartmentAndBlock(String apartment, String block);
}
