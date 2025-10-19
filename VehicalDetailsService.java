package com.Online.ParkigManagement.Service;

import com.Online.ParkigManagement.Payload.VehicalDetailsDto;
import com.Online.ParkigManagement.Payload.VehicalDetailsResponse;

public interface VehicalDetailsService {

    VehicalDetailsDto addDetalis(VehicalDetailsDto VehicalDetailsDto);

    VehicalDetailsResponse getAlldetails(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    VehicalDetailsResponse getdetailsById(Long driverId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortDir);

    VehicalDetailsDto updateData(VehicalDetailsDto vehicalDetailsDto, Long vehicalId);

    VehicalDetailsDto deleteData(Long vehicalId);

    VehicalDetailsDto getexitTime(String vehicalNumber, Long driverId);

    VehicalDetailsDto getenteryTime(String vehicalNumber, String vehicalType);

}
