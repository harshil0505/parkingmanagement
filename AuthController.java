package com.Online.ParkigManagement.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Online.ParkigManagement.Repository.RoleRepository;
import com.Online.ParkigManagement.Repository.UserRepository;
import com.Online.ParkigManagement.Security.JWT.JwtUtils;
import com.Online.ParkigManagement.Security.Requset.LoginRequest;
import com.Online.ParkigManagement.Security.Requset.SignupRequest;
import com.Online.ParkigManagement.Security.Responce.UserInfoResponse;
import com.Online.ParkigManagement.Security.ServiceSecurity.UserDetaileImpl;
import com.Online.ParkigManagement.model.AppRole;
import com.Online.ParkigManagement.model.Role;
import com.Online.ParkigManagement.model.User;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private  UserRepository userRepository;
    
  
    private static final Object jwtCookie = null;
    
     

  
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils JwtUtils;

    @Autowired
    private UserRepository userRepositroy;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
  
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequst){
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequst.getEmail(),
                loginRequst.getPassword()
            )
        );
        }catch(AuthenticationException exception){
            Map<String,Object> map=new HashMap<>();
            map.put("message","Bad credentials");
            return new  ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetaileImpl userDetails = (UserDetaileImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie=JwtUtils.ganrateJwtCookie(userDetails);

    List<String> roles=userDetails.getAuthorities().stream()
        .map(item->item.getAuthority())
        .collect(Collectors.toList());

        UserInfoResponse response=new UserInfoResponse(
            userDetails.getId(),
            userDetails.getEmail(),
            userDetails.getPassword(),
            roles,
            jwtCookie.toString()
        );

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, 
        jwtCookie.toString())
        .body(response);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUIser(@Valid @RequestBody SignupRequest signupRequest){
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
    
    
       User user= new User(signupRequest.getEmail(),
         encoder.encode(signupRequest.getPassword())
         );

         Set<String> strRoles=signupRequest.getRoles();
         Set<Role> roles=new HashSet<>();

         if(strRoles==null){
            Role userRole=roleRepository.findByRoleName(AppRole.Role_Driver)
            .orElseThrow(()->new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          
         }else{
            strRoles.forEach(role->{
                switch(role){
                    case "admin":
                        Role adminRole=roleRepository.findByRoleName(AppRole.Role_ADMIN)
                        .orElseThrow(()->new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                       

                         
                            default:
                            Role userRole=roleRepository.findByRoleName(AppRole.Role_Driver)
                            .orElseThrow(()->new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                }
            });
        }
            user.setRoles(roles);
            userRepositroy.save(user);
         
         
            
            return ResponseEntity.ok("User registered successfully!");
}
  
   @GetMapping("/emailid")
    public String currentUserName(Authentication authentication){
        if (authentication != null)
            return ( (LoginRequest) authentication).getEmail();
        else
            return "";
    }

    @GetMapping("/userDetails")
    public ResponseEntity<?>  getUserDetails(Authentication authentication){
        UserDetaileImpl userDetails = (UserDetaileImpl) authentication.getPrincipal();

        List<String> roles=userDetails.getAuthorities().stream()
        .map(item->item.getAuthority())
        .collect(Collectors.toList());
        UserInfoResponse response=new UserInfoResponse(
            userDetails.getId(),
            userDetails.getEmail(),
            userDetails.getPassword(),
            roles,
            null
        );
        return ResponseEntity.ok().body(response);
    }

        @PostMapping("/signout")
        public ResponseEntity<?> signoutUser(){
            ResponseCookie cookie=JwtUtils.getCleanJwtCookie();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body("You've been signed out!");
        }
        
}   