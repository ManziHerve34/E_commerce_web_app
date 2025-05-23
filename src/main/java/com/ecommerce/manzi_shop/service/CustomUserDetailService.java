package com.ecommerce.manzi_shop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.manzi_shop.model.CustomUserDetail;
import com.ecommerce.manzi_shop.model.User;
import com.ecommerce.manzi_shop.repository.UserRepository;

import java.util.Optional;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user= userRepository.findUserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("User check"));

        return user.map(CustomUserDetail::new).get();
    }
}
