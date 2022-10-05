package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.PackageSubscribe;
import com.debusey.smart.pos.smartpos.entity.PackageTransaction;
import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageTransactionRepo extends JpaRepository<PackageTransaction, Integer> {
    List<PackageTransaction> findByUsers(Users users);

    List<PackageTransaction> findByUsersAndStatus(Users users, boolean status);

    PackageTransaction findByUsersAndPackageSubscribeAndStatus(Users users, PackageSubscribe packageSubscribe, boolean status);
}
