/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Messages;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MessagesRepo extends CrudRepository<Messages, Integer> {
    List<Messages> findByMsgRead(boolean status);
}