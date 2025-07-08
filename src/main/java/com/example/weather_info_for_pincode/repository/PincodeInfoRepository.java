package com.example.weather_info_for_pincode.repository;

import com.example.weather_info_for_pincode.entity.PincodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeInfoRepository extends JpaRepository<PincodeInfo, String> {
}
