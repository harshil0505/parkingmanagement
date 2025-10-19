package com.Online.ParkigManagement.Security.Responce;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
       
         private Long id;
         private String email;
         private String Possword;

    

         public UserInfoResponse(Long id2, String email2, String password, List<String> roles, String string) {
        
        this.id = id2;
        this.email = email2;
        this.Possword = password;
        
         }
}