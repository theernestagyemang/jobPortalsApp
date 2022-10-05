package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.AppUser;
import com.ao.schoolerp.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<AppUser> user = userRepo.findByUsername(username);

        return user.map(MyUserDetails :: new)
                .orElseThrow(()->new UsernameNotFoundException(username+" does not exist"));
    }
}
