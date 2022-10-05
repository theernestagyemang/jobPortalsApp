package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.HostelPayment;
import com.ao.schoolerp.services.HostelPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hostel")
public class HostelPaymentController {
    private HostelPaymentService hostelPaymentService;

    public HostelPaymentController(HostelPaymentService hostelPaymentService) {
        this.hostelPaymentService = hostelPaymentService;
    }

    @GetMapping("/payments")
    public String viewHostelPayment (Model model, Principal principal){

        List<HostelPayment> hostelPaymentList = hostelPaymentService.findAll();
        model.addAttribute("hostelPayments", hostelPaymentList);
        return "hostel/payments";
    }

    @PostMapping("/create-payment")
    @ResponseBody
    public ResponseEntity addHostelPayment (@RequestBody HostelPayment hostelPayment){
//       if(hostelPaymentService.save(hostel)  return "redirect: /hostels" : return "re" ;
        boolean status = hostelPaymentService.save(hostelPayment);
        if(status){
            return ResponseEntity.ok().body("SUCCESS");
        }else {
            return ResponseEntity.ok().body("FAILED");
        }
    }

    @GetMapping("/payments/{id}")
    @ResponseBody
    public HostelPayment findById(@PathVariable Integer id, Model model){
        return hostelPaymentService.findById(id).get();
    }
}
