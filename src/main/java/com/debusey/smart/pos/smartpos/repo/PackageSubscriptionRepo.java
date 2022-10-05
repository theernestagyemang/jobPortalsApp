package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.PackageSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageSubscriptionRepo extends JpaRepository<PackageSubscribe, Integer> {
}
