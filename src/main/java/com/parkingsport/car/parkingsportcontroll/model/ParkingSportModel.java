package com.parkingsport.car.parkingsportcontroll.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_VAGA")
@Getter
@Setter
public class ParkingSportModel implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable = false, unique = true, length = 10)
	private String parkingSportNumber;

	@Column(nullable = false, unique = true, length = 7)
	private String licensePlateCar;

	@Column(nullable = false, length = 70)
	private String brandCar;

	@Column(nullable = false, length = 70)
	private String modelCar;

	@Column(nullable = false, length = 70)
	private String colorCar;

	private LocalDateTime registrationDate;

	@Column(nullable = false, length = 200)
	private String responsibleName;

	@Column(nullable = false, length = 50)
	private String apartment;

	@Column(nullable = false, length = 50)
	private String block;
}
