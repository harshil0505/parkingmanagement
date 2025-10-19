package com.Online.ParkigManagement.Security.ServiceSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Online.ParkigManagement.Repository.UserRepository;
import com.Online.ParkigManagement.model.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
public class UserDetaileServiceImpl implements UserDetailsService{

    @Autowired
    private  UserRepository userRepository ;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return UserDetaileImpl.build(user);
        
    }

}
