package com.Online.ParkigManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Online.ParkigManagement.Config.AppConstant;
import com.Online.ParkigManagement.Payload.VehicalDetailsDto;
import com.Online.ParkigManagement.Payload.VehicalDetailsResponse;
import com.Online.ParkigManagement.Service.VehicalDetailsService;

@RestController
@RequestMapping("/api")
public class VehicalController {
  @Autowired
    VehicalDetailsService vehicalDetailsService;
      
    
        @PostMapping("/vehicalDetails")
        public ResponseEntity<VehicalDetailsDto> addDetalis( @RequestBody VehicalDetailsDto vehicalDetailsDto ){
            VehicalDetailsDto vehicalDetailsDto2=vehicalDetailsService.addDetalis(vehicalDetailsDto);
            return new ResponseEntity<>(vehicalDetailsDto2,HttpStatus.OK);
        }
        
        @GetMapping("/vehicalAlldetails")
        public ResponseEntity<VehicalDetailsResponse> getAlldetails(
        @RequestParam(name="pageNumber",defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(name="pageSize",defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_ID_BY, required = false) String sortBy,
        @RequestParam(name="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir){
            VehicalDetailsResponse vehicalDetailsResponse = vehicalDetailsService.getAlldetails(pageNumber, pageSize,sortBy,sortDir);
            
            if(vehicalDetailsResponse != null){
                return new ResponseEntity<>(vehicalDetailsResponse,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    
        @GetMapping("/vehicalById/{vehicalId}")
        public ResponseEntity<VehicalDetailsResponse> getdetailsById(@PathVariable  Long driverId, @RequestParam(name="pageNumber",defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(name="pageSize",defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_ID_BY, required = false) String sortBy,
        @RequestParam(name="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir){
            VehicalDetailsResponse vehicalDetailsResponse=vehicalDetailsService.getdetailsById(driverId,pageNumber,pageSize,sortBy,sortDir);
        if(vehicalDetailsResponse != null){
            return new ResponseEntity<>(vehicalDetailsResponse, HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateData/vehicalId/{vehicalId}")
    public ResponseEntity<VehicalDetailsDto> updateData(@RequestBody VehicalDetailsDto vehicalDetailsDto,@PathVariable Long vehicalId){
        VehicalDetailsDto updateDataDto=vehicalDetailsService.updateData(vehicalDetailsDto,vehicalId);
        return new ResponseEntity<>(updateDataDto,HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteData/vehicalId/{vehicalId}")
    public ResponseEntity<VehicalDetailsDto> deleteData(@PathVariable Long vehicalId){
        VehicalDetailsDto DeleteDataDto=vehicalDetailsService.deleteData(vehicalId);
        return new ResponseEntity<>(DeleteDataDto,HttpStatus.OK);
    }
    @PostMapping("/entryTime")
    public ResponseEntity<VehicalDetailsDto> getEntryTime(@RequestBody VehicalDetailsDto request) {
        VehicalDetailsDto entryDetailsDto =
                vehicalDetailsService.getenteryTime(request.getVehicalNumber(), request.getVehicalType());
        return new ResponseEntity<>(entryDetailsDto, HttpStatus.OK);
    }

    @PostMapping("/exitTime/vehicalNumber/{vehicalNumber}/driver/{driverId}")
    public ResponseEntity<VehicalDetailsDto> getexitTime(@PathVariable String vehicalNumber,@PathVariable Long driverId) {
        VehicalDetailsDto exitDetailsDto = vehicalDetailsService.getexitTime(vehicalNumber, driverId);
        return new ResponseEntity<>(exitDetailsDto, HttpStatus.OK);
    }
    
    
}


