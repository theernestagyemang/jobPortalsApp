/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api;

import com.debusey.smart.pos.smartpos.db.Testimony;
import com.debusey.smart.pos.smartpos.entity.Testimonial;
import com.debusey.smart.pos.smartpos.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Admin
 */

@RestController
@RequestMapping("/api/v1")
public class CustomerApi {

    @Autowired
    private TestimonialService service;

    @GetMapping("/testimonial")
    public List<Testimony> tesmonies() {
        List<Testimonial> list = service.findByStatus(true);

        return createTestimony(list);
    }

    private List<Testimony> createTestimony(List<Testimonial> list) {
        // id,  message, candidtate, fileName, candidateProfession
        return list.stream().map(c ->
                        new Testimony(
                                new Date().getTime(),
                                c.getMessage(),
                                c.getName(),
                                c.getFileName(),
                                c.getProfession()
                        )
                )
                .collect(Collectors.toList());
    }
}
