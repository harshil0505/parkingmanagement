package com.Online.ParkigManagement.Service;

import com.Online.ParkigManagement.Payload.DriverDetailsDto;
import com.Online.ParkigManagement.Payload.DriverDetailsResponse;

public interface DriverDetailsService {

    DriverDetailsDto addDetalis(DriverDetailsDto driverDetailsDto);

    DriverDetailsResponse getAlldetails(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    DriverDetailsResponse getdetailsById(Long driverId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    DriverDetailsDto updateData(DriverDetailsDto driverDetailsDto, Long driverId);

    DriverDetailsDto deleteData(Long driverId);

}
