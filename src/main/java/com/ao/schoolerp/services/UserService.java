package com.ao.schoolerp.services;


import com.ao.schoolerp.entities.AppUser;
import com.ao.schoolerp.entities.UserRole;
import com.ao.schoolerp.repo.UserRepo;
import com.ao.schoolerp.repo.UserRoleRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepo userRoleRepo;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, UserRoleRepo userRoleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepo = userRoleRepo;
    }

    public List<AppUser> findUsers(){
        try {
            return userRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public AppUser addUser(AppUser user){
        //get the user password
        String pw = user.getPassword();
        //hash the password and set it to the user object
        user.setPassword(passwordEncoder.encode(pw));
        try {
            return userRepo.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public AppUser findUserById(Integer id){
        try {
            return userRepo.findById(id).get();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public AppUser findUserByUsername(String username){
        try {
            return userRepo.findByUsername(username).get();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public boolean addRoleToUser(String username, String roleName){
        try {
            AppUser user = userRepo.findByUsername(username).get();
            UserRole role = userRoleRepo.findByName(roleName).get();
            user.getRoles().add(role);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
