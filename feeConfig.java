package com.Online.ParkigManagement.Config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class feeConfig {

    public static final Map<String,Double> fees= new HashMap<>();

    static{
         fees.put("2wheeler",20.0);
         fees.put("3wheeler",30.0);
         fees.put("4wheeler",40.0);
         fees.put("truck",50.0);
    }
    
    public double getfess(String vehicalType){
       return fees.getOrDefault(vehicalType.toLowerCase(),20.0);
    }
    
    


    
}
