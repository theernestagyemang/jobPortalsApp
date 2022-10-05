package com.debusey.smart.pos.smartpos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

import static com.debusey.smart.pos.smartpos.JsfUtil.DOCUMENTS;

@SpringBootApplication
//@EnableBatchProcessing
public class SmartPosApplication {

    public static void main(String[] args) {

        new File(DOCUMENTS).mkdir();
        new File("uploads").mkdir();
        SpringApplication.run(SmartPosApplication.class, args);

    }

}
