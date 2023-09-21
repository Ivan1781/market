package com.market.api.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.market.api.models.UserEntity;
import com.market.api.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(
            ()->new UsernameNotFoundException("Username not found"));
        return new User(
            userEntity.getUserName(), 
            userEntity.getPassword(), 
            userEntity.getRoles().stream().map(
            x-> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList()));
    }
    
}
