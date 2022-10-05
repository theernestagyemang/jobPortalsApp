/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class UserService {

    @Autowired
    public UserRepository repository;


    public List<Users> getAllUsers() {
        List<Users> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public Page<Users> findAll(Integer page) {
        return repository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "entryDate")));
    }

    public Page<Users> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public boolean updateUsers(Users t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUsers(List<Users> t) {
        repository.saveAll(t);
    }

    public void deleteUsers(Users t) {
        repository.delete(t);
    }

    public boolean deleteById(Long t) {
        try {
            repository.deleteById(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Users> findByUsername(String userName) {
        Optional<Users> d = repository.findByUsername(userName);
        return d;
    }

    public Optional<Users> findByEmail(String userName) {
        Optional<Users> d = repository.findByEmail(userName);
        return d;
    }

    public Optional<Users> findById(Long id) {
        return repository.findById(id);
    }

    public Optional findByResetToken(String resetToken) {
        return repository.findByResetToken(resetToken);
    }

    public Optional findByActivationToken(String resetToken) {
        return repository.findByActivationToken(resetToken);
    }

    public List<Users> findByUserEmail(String username) {
        return repository.findByUserEmail(username);
    }


    public Page<Users> findByUserType(String userType, Integer page, Integer max) {
        return repository.findByUserType(userType, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }


}
