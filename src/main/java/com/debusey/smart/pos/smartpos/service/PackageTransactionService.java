package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.PackageSubscribe;
import com.debusey.smart.pos.smartpos.entity.PackageTransaction;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.PackageTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageTransactionService {
    @Autowired
    private PackageTransactionRepo packageTransactionRepo;

    public boolean addPackageTransaction(PackageTransaction packageTransaction) {
        try {
//            courseTransactionRepo.save(courseTransaction);
            packageTransactionRepo.save(packageTransaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<PackageTransaction> findByUser(Users user) {
        return packageTransactionRepo.findByUsers(user);
    }

    public boolean findByUserAndStatus(Users users) {
        try {
            return packageTransactionRepo.findByUsersAndStatus(users, true).isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public PackageTransaction findByUserAndPackageSubscribeAndStatus(Users users, PackageSubscribe packageSubscribe) {
        try {
            return packageTransactionRepo.findByUsersAndPackageSubscribeAndStatus(users,packageSubscribe,true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PackageTransaction> findAll() {
        return packageTransactionRepo.findAll();
    }

    public PackageTransaction findById(Integer id) {
        return packageTransactionRepo.findById(id).orElse(null);
    }

}
