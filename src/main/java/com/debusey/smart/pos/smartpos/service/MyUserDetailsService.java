/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


//import com.debusey.pos.smartpos.repo.RoleRepository;
//import com.debusey.pos.smartpos.repo.RoleRepository;

import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * @author SL002
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

//    @Autowired
//    private RoleRepository roleRepository;


    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String ip = getClientIP();
        System.out.println("ip...." + ip);

        Optional<Users> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            System.out.println("user is present...");
        } else {
            System.out.println("Not present");
        }
        user.orElseThrow(() -> new UsernameNotFoundException("Not found " + userName));

        MyUserDetails details = user.map(MyUserDetails::new).get();

        return details;


    }


}
