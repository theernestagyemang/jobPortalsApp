/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Qualifications;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Admin
 */
public interface QualificationRepository extends CrudRepository<Qualifications, Integer> {
}