/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MenuRepo extends CrudRepository<Menu, Integer> {
    List<Menu> findByActive(boolean status);
}