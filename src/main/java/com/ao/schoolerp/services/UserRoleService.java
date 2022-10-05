package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.UserRole;
import com.ao.schoolerp.repo.UserRoleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepo roleRepo;

    public UserRoleService(UserRoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<UserRole> findRoles(){
        try {
            return roleRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UserRole addRole(UserRole role){
        try {
            return roleRepo.save(role);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UserRole findRoleById(Integer id){
        try {
            return roleRepo.findById(id).get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public UserRole findByName(String name){
        try {
            return roleRepo.findByName(name).get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
