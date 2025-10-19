package com.Online.ParkigManagement.Security.ServiceSecurity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Online.ParkigManagement.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetaileImpl implements UserDetails{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetaileImpl build( User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
        return new UserDetaileImpl(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
     return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
        
    }

   
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o){
        if(this== o) return true;
        if(o== null || getClass() != o.getClass()) return false;
        UserDetaileImpl user = (UserDetaileImpl) o;
        return Objects.equals(id,user.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email); // or whichever fields you want
    }
    

}
