/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Adverts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Admin
 */
public interface AdvertRepo extends CrudRepository<Adverts, Integer> {

    List<Adverts> findByPageLocation(String seeker);
}
