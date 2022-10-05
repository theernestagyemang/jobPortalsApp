/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.entity.Messages;
import com.debusey.smart.pos.smartpos.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author Admin
 */

@Controller
public class MessagesController {
    @Autowired
    private MessagesService service;


    @PostMapping("/create-message")
    @ResponseBody
    public boolean createMessage(String name, String email, String message) {
        Messages msg = new Messages();
        msg.setEmail(email);
        msg.setEntryDate(new Date());
        msg.setMessage(message);
        msg.setName(name);
        msg.setMsgRead(Boolean.TRUE);

        return service.save(msg);
    }
}
