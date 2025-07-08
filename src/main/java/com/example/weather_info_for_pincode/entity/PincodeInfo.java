package com.example.weather_info_for_pincode.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PincodeInfo {
    @Id
    private String pincode;
    private double latitude;
    private double longitude;
}
