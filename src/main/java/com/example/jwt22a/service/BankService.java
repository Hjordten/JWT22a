package com.example.jwt22a.service;

import com.example.jwt22a.model.Customer;
import com.example.jwt22a.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUser kaldt: user=" + username);
        String userName, password = null;
        List<GrantedAuthority> authorities = null;
        Optional<Customer> customer = customerRepository.findByEmail(username);
        if (customer.isPresent()) {
            userName = customer.get().getEmail();
            password = customer.get().getPwd();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customer.get().getRole()));
        } else {
            throw new UsernameNotFoundException("User details not found for the user:" + username);
        }
        return new User(username,password,authorities);
    }


}
