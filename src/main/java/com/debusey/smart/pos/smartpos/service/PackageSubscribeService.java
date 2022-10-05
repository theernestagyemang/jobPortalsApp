package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.PackageSubscribe;
import com.debusey.smart.pos.smartpos.repo.PackageSubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageSubscribeService {
    @Autowired
    private PackageSubscriptionRepo packageSubscriptionRepo;

    public boolean addPackage(PackageSubscribe packageSubscribe) {
        try {
            packageSubscriptionRepo.save(packageSubscribe);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PackageSubscribe> findAll() {
        try {
            return packageSubscriptionRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PackageSubscribe findById(Integer id) {
        try {
            return packageSubscriptionRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id) {
        try {
            packageSubscriptionRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
