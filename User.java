package com.Online.ParkigManagement.model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public User(String string, String encode) {
        this.email = string;
        this.password = encode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;
    
    @NotBlank
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Size(min = 6, max =100, message = "Password must be between 6 and 50 characters") 
    @Column(name = "password")
    private String password;

   
    @OneToOne(cascade = CascadeType.ALL)
      @JoinColumn(name = "driver_id", referencedColumnName = "driverId")
      private DriverDetails driverDetails;

      @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
      @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
      )
      private Set<Role> roles = new HashSet<>();


   
}
