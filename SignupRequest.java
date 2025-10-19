package com.Online.ParkigManagement.Security.Requset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
 
    @NotBlank
    @Email
    private String email;
  
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public java.util.Set<String> getRoles() {
            return null;
     
    }

}
