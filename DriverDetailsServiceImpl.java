package com.Online.ParkigManagement.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.Online.ParkigManagement.Payload.DriverDetailsDto;
import com.Online.ParkigManagement.Payload.DriverDetailsResponse;
import com.Online.ParkigManagement.Repository.DriverDetailsRepository;
import com.Online.ParkigManagement.excepasion.ResourceNotFoundException;
import com.Online.ParkigManagement.model.DriverDetails;

@Service
public class DriverDetailsServiceImpl implements DriverDetailsService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DriverDetailsRepository  driverDetailsRepository;

    

    @Override
    public DriverDetailsDto addDetalis(DriverDetailsDto driverDetailsDto) {
      DriverDetails driverDetails=modelMapper.map(driverDetailsDto,DriverDetails.class);
      driverDetails.setDriverId(null);
      
      DriverDetails savedDetails = driverDetailsRepository.save(driverDetails);
            return modelMapper.map(savedDetails, DriverDetailsDto.class);

    }


    @Override
    public  DriverDetailsResponse getAlldetails(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
       Sort sortByAnyOrder=sortDir.equalsIgnoreCase("asc")
       ?Sort.by(sortBy).ascending()
       :Sort.by(sortBy).descending();

       Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAnyOrder);
       Page<DriverDetails>  DriverPage=driverDetailsRepository.findAll(pageDetails);
       List<DriverDetails> driverdetails=DriverPage.getContent();

       List<DriverDetailsDto> driverDetailsDtos=driverdetails.stream()
                 .map(driver -> modelMapper.map(driver, DriverDetailsDto.class))
                .toList();
       DriverDetailsResponse driverDetailsResponse=new DriverDetailsResponse();
       driverDetailsResponse.setContent(driverDetailsDtos);
       driverDetailsResponse.setPageNumber(DriverPage.getNumber());
       driverDetailsResponse.setPageSize(DriverPage.getSize());
       driverDetailsResponse.setTotalElements(DriverPage.getTotalElements());
       driverDetailsResponse.setTotalPages(DriverPage.getTotalPages());
       driverDetailsResponse.setLastPage(DriverPage.isLast());

       return driverDetailsResponse;
    }


  

    @Override
    public DriverDetailsResponse getdetailsById(Long driverId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortDir) {
              
              DriverDetails driverDetails = driverDetailsRepository.findById(driverId)
              .orElseThrow(() -> new ResourceNotFoundException("DriverDetails", "driverId", driverId));
          
          DriverDetailsDto driverDetailsDto = modelMapper.map(driverDetails, DriverDetailsDto.class);
          
          DriverDetailsResponse response = new DriverDetailsResponse();
          response.setContent(List.of(driverDetailsDto));  
          response.setPageNumber(0);
          response.setPageSize(1);
          response.setTotalElements((long) 1);
          response.setTotalPages(1);
          response.setLastPage(true);
          
          return response;
          
            }


    @Override
    public DriverDetailsDto updateData(DriverDetailsDto driverDetailsDto, Long driverId) {
      
      DriverDetails DetailsFromdb=driverDetailsRepository.findById(driverId)
       .orElseThrow(()->new ResourceNotFoundException("DriverDetails","driverId", driverId));

       DriverDetails driverDetails=modelMapper.map(driverDetailsDto,DriverDetails.class);
       DetailsFromdb.setDriverId(driverDetails.getDriverId());
       DetailsFromdb.setDriverName(driverDetails.getDriverName());
       DetailsFromdb.setDriverPhoneNumber(driverDetails.getDriverPhoneNumber());
       DriverDetails saveDetails=driverDetailsRepository.save(DetailsFromdb);

       return modelMapper.map(saveDetails,DriverDetailsDto.class);
    }


    @Override
    public DriverDetailsDto deleteData(Long driverId) {
      DriverDetails DetailsFromdb =driverDetailsRepository.findById(driverId)
           .orElseThrow(()->new ResourceNotFoundException("DriverDetails", "driverId", driverId));

           driverDetailsRepository.delete(DetailsFromdb);
           return modelMapper.map(DetailsFromdb, DriverDetailsDto.class);
    }
            
            
  
    
}
