package com.Online.ParkigManagement.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Online.ParkigManagement.Config.AppConstant;
import com.Online.ParkigManagement.Payload.DriverDetailsDto;
import com.Online.ParkigManagement.Payload.DriverDetailsResponse;
import com.Online.ParkigManagement.Service.DriverDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class DriverDetalisController {
    
    @Autowired
    DriverDetailsService driverDetailsService;
      
    
        @PostMapping("/driverDetails")
        public ResponseEntity<DriverDetailsDto> addDetalis( @RequestBody DriverDetailsDto driverDetailsDto ){
            DriverDetailsDto driverDetailsDto2=driverDetailsService.addDetalis(driverDetailsDto);
            return new ResponseEntity<>(driverDetailsDto2,HttpStatus.OK);
        }
        
        @GetMapping("/Alldetails")
        public ResponseEntity<DriverDetailsResponse> getAlldetails(
        @RequestParam(name="pageNumber",defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(name="pageSize",defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_ID_BY, required = false) String sortBy,
        @RequestParam(name="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir){
            DriverDetailsResponse driverDetailsResponse = driverDetailsService.getAlldetails(pageNumber, pageSize,sortBy,sortDir);
            
            if(driverDetailsResponse != null){
                return new ResponseEntity<>(driverDetailsResponse,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    
        @GetMapping("/detailsById/{driverId}")
        public ResponseEntity<DriverDetailsResponse> getdetailsById(@PathVariable  Long driverId, @RequestParam(name="pageNumber",defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(name="pageSize",defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_ID_BY, required = false) String sortBy,
        @RequestParam(name="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir){
            DriverDetailsResponse driverDetailsResponse=driverDetailsService.getdetailsById(driverId,pageNumber,pageSize,sortBy,sortDir);
        if(driverDetailsResponse != null){
            return new ResponseEntity<>(driverDetailsResponse, HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateData/driverId/{driverId}")
    public ResponseEntity<DriverDetailsDto> updateData(@RequestBody DriverDetailsDto driverDetailsDto,@PathVariable Long driverId){
        DriverDetailsDto updateDataDto=driverDetailsService.updateData(driverDetailsDto,driverId);
        return new ResponseEntity<>(updateDataDto,HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteData/driverId/{driverId}")
    public ResponseEntity<DriverDetailsDto> deleteData(@PathVariable Long driverId){
        DriverDetailsDto DeleteDataDto=driverDetailsService.deleteData(driverId);
        return new ResponseEntity<>(DeleteDataDto,HttpStatus.OK);
    }
    
}
