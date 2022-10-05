package com.ao.schoolerp.controllers;

import com.ao.schoolerp.AuthMiddleware;
import com.ao.schoolerp.entities.AppUser;
import com.ao.schoolerp.entities.HostelPayment;
import com.ao.schoolerp.entities.HostelTransaction;
import com.ao.schoolerp.helpers.HostelTransactionHelper;
import com.ao.schoolerp.services.HostelPaymentService;
import com.ao.schoolerp.services.HostelTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hostel")
public class HostelTransactionController {
    private final HostelTransactionService hostelTransactionService;
    private final HostelPaymentService hostelPaymentService;

    public HostelTransactionController(HostelTransactionService hostelTransactionService, HostelPaymentService hostelPaymentService) {
        this.hostelTransactionService = hostelTransactionService;
        this.hostelPaymentService = hostelPaymentService;
    }

    @GetMapping("/transactions")
    public String viewHostelTransaction (Model model, Principal principal){

        List<HostelTransaction> hostelTransactionList = hostelTransactionService.findAll();
        model.addAttribute("hostelTransactions", hostelTransactionList);
        return "hostel/transactions";
    }

    @PostMapping("/create-transaction")
    @ResponseBody
    public ResponseEntity addHostelTransaction(
            @RequestBody HostelTransactionHelper hostelTransactionHelper, Principal principal){
//       if(hostelTransactionService.save(hostel)  return "redirect: /hostels" : return "re" ;
        Optional<HostelPayment> hostelPayment = hostelPaymentService.findById(hostelTransactionHelper.getHostelPaymentId());
        AppUser user = AuthMiddleware.getCurrentUser(principal);
        if(hostelPayment.isPresent()){
            HostelTransaction hostelTransaction = new HostelTransaction();
            hostelTransaction.setHostelPayment(hostelPayment.get());
            hostelTransaction.setEnteredBy(user);
            hostelTransaction.setStudentEmail(hostelTransactionHelper.getStudentEmail());
            hostelTransaction.setPaymentStatus(hostelTransactionHelper.getPaymentStatus());
            hostelTransaction.setName(hostelTransactionHelper.getName());
            hostelTransaction.setAllotmentDate(hostelTransactionHelper.getAllotmentDate());
            hostelTransaction.setStartDate(hostelTransactionHelper.getStartDate());
            hostelTransaction.setEndDate(hostelTransactionHelper.getEndDate());
            hostelTransaction.setTermOfPayment(hostelTransactionHelper.getTermOfPayment());

            boolean status = hostelTransactionService.save(hostelTransaction);
            if(status){
                return ResponseEntity.ok().body("SUCCESS");
            }else {
                return ResponseEntity.ok().body("FAILED");
            }
        }else {
            return ResponseEntity.ok().body("Payment Selected does not exist");
        }

    }

    @GetMapping("/transactions/{id}")
    @ResponseBody
    public HostelTransaction findById(@PathVariable Integer id,Model model){
        return hostelTransactionService.findById(id).get();
    }
}
