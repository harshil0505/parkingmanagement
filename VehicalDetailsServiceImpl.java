package com.Online.ParkigManagement.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Online.ParkigManagement.Config.feeConfig;
import com.Online.ParkigManagement.Payload.VehicalDetailsDto;
import com.Online.ParkigManagement.Payload.VehicalDetailsResponse;
import com.Online.ParkigManagement.Repository.DriverDetailsRepository;
import com.Online.ParkigManagement.Repository.VehicalDetailsRepository;
import com.Online.ParkigManagement.excepasion.ResourceNotFoundException;
import com.Online.ParkigManagement.model.DriverDetails;
import com.Online.ParkigManagement.model.VehicalDetails;

import jakarta.transaction.Transactional;

@Service
public class VehicalDetailsServiceImpl implements VehicalDetailsService {

     @Autowired
    ModelMapper modelMapper;

    @Autowired
    DriverDetailsRepository driverDetailsRepository;

    @Autowired
    feeConfig feeConfig;

   

    @Autowired
    VehicalDetailsRepository  vehicalDetailsRepository;

    
    @Override
    public VehicalDetailsDto addDetalis(VehicalDetailsDto vehicalDetailsDto){




      VehicalDetails vehicalDetails=modelMapper.map(vehicalDetailsDto,VehicalDetails.class);
      vehicalDetails.getVehicalId();
      vehicalDetails.setVehicalId(null); 
      vehicalDetails.setEntryTime(LocalDateTime.now());  
      vehicalDetails.setExitTime(null);  
      vehicalDetails.setDurasionOntime(null);
      vehicalDetails.setFee(null);
      
 
    

            if (vehicalDetailsRepository.findByVehicalNumber(vehicalDetails.getVehicalNumber()).isPresent()) {
                throw new ResourceNotFoundException("VehicalDetails", "vehicalNumber", vehicalDetails.getVehicalNumber());
            }
            
           
      
      VehicalDetails savedDetails = vehicalDetailsRepository.save(vehicalDetails);
            return modelMapper.map(savedDetails, VehicalDetailsDto.class);

    }


    @Override
    public  VehicalDetailsResponse getAlldetails(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
       Sort sortByAnyOrder=sortDir.equalsIgnoreCase("asc")
       ?Sort.by(sortBy).ascending()
       :Sort.by(sortBy).descending();

       Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAnyOrder);
       Page<VehicalDetails>  VehicalPage=vehicalDetailsRepository.findAll(pageDetails);
       List<VehicalDetails> vehicalDetails=VehicalPage.getContent();

       List<VehicalDetailsDto> vehicalDetailsDtos=vehicalDetails.stream()
                 .map(driver -> modelMapper.map(driver, VehicalDetailsDto.class))
                .toList();
       VehicalDetailsResponse vehicalDetailsResponse=new VehicalDetailsResponse();
       vehicalDetailsResponse.setContent(vehicalDetailsDtos);
       vehicalDetailsResponse.setPageNumber(VehicalPage.getNumber());
       vehicalDetailsResponse.setPageSize(VehicalPage.getSize());
       vehicalDetailsResponse.setTotalElements(VehicalPage.getTotalElements());
       vehicalDetailsResponse.setTotalPages(VehicalPage.getTotalPages());
       vehicalDetailsResponse.setLastPage(VehicalPage.isLast());

       return vehicalDetailsResponse;
    }


  

    @Override
    public VehicalDetailsResponse getdetailsById(Long vehicalId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortDir) {
              
              VehicalDetails VehicalDetails = vehicalDetailsRepository.findById(vehicalId)
              .orElseThrow(() -> new ResourceNotFoundException("VehicalDetails", "vehicalId", vehicalId));
          
          VehicalDetailsDto VehicalDetailsDto = modelMapper.map(VehicalDetails, VehicalDetailsDto.class);
          
          VehicalDetailsResponse response = new VehicalDetailsResponse();
          response.setContent(List.of(VehicalDetailsDto));  
          response.setPageNumber(0);
          response.setPageSize(1);
          response.setTotalElements((long) 1);
          response.setTotalPages(1);
          response.setLastPage(true);
          
          return response;
          
            }


    @Override
    public VehicalDetailsDto updateData(VehicalDetailsDto VehicalDetailsDto, Long vehicalId) {
      
      VehicalDetails DetailsFromdb=vehicalDetailsRepository.findById(vehicalId)
       .orElseThrow(()->new ResourceNotFoundException("VehicalDetails","vehicalId", vehicalId));

       VehicalDetails vehicalDetails=modelMapper.map(VehicalDetailsDto,VehicalDetails.class);
       DetailsFromdb.setVehicalId(vehicalDetails.getVehicalId());
       DetailsFromdb.setVehicalNumber(vehicalDetails.getVehicalNumber());
       DetailsFromdb.setVehicalType(vehicalDetails.getVehicalType());
       DetailsFromdb.setEntryTime(vehicalDetails.getEntryTime());
         DetailsFromdb.setExitTime(vehicalDetails.getExitTime());

       VehicalDetails saveDetails=vehicalDetailsRepository.save(DetailsFromdb);

       return modelMapper.map(saveDetails,VehicalDetailsDto.class);
    }


    @Override
    public VehicalDetailsDto deleteData(Long vehicalId) {
      VehicalDetails DetailsFromdb =vehicalDetailsRepository.findById(vehicalId)
           .orElseThrow(()->new ResourceNotFoundException("VehicalDetails", "vehicalId", vehicalId));

           vehicalDetailsRepository.delete(DetailsFromdb);
           return modelMapper.map(DetailsFromdb, VehicalDetailsDto.class);
    }


    @Override
    public VehicalDetailsDto getenteryTime(String vehicalNumber,String vehicalType) {
       
    VehicalDetailsDto vehicalDetailsDto = new VehicalDetailsDto();
       vehicalDetailsDto.setVehicalNumber(vehicalNumber);
       vehicalDetailsDto.setVehicalType(vehicalType.toLowerCase());
       vehicalDetailsDto.setEntryTime(LocalDateTime.now());
      
        return vehicalDetailsDto;
    }



    @Override
    @Transactional
    public VehicalDetailsDto getexitTime(String vehicalNumber, Long driverId) {
  
    VehicalDetails vehicalDetails = vehicalDetailsRepository.findLatestActiveEntry(vehicalNumber)
        .orElseThrow(() -> new ResourceNotFoundException("VehicalDetails", "vehicalNumber", vehicalNumber));

    DriverDetails driverDetails = driverDetailsRepository.findById(driverId)
        .orElseThrow(() -> new ResourceNotFoundException("DriverDetails", "driverId", driverId));

    
    vehicalDetails.setDriverDetails(driverDetails);

    vehicalDetails.setExitTime(LocalDateTime.now());
    long minutes = Duration.between(vehicalDetails.getEntryTime(), vehicalDetails.getExitTime()).toMinutes();
    vehicalDetails.setDurasionOntime(minutes);

    
    double feePerHour = feeConfig.getfess(vehicalDetails.getVehicalType());
    double fee = Math.ceil(minutes / 60.0) * feePerHour;
    vehicalDetails.setFee(fee);


    VehicalDetails savedDetails = vehicalDetailsRepository.save(vehicalDetails);

   
    VehicalDetailsDto vehicalDetailsDto = modelMapper.map(savedDetails, VehicalDetailsDto.class);
    vehicalDetailsDto.setDriverId(driverId);

    return vehicalDetailsDto;
}
}