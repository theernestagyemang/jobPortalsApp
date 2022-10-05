package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.AssessmentTransactionResponse;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.PackageSubscribeService;
import com.debusey.smart.pos.smartpos.service.PackageTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Date;

@Controller
public class PackageTransactionController {
    @Autowired
    private PackageSubscribeService packageSubscribeService;

    @Autowired
    private PackageTransactionService packageTransactionService;

    @PostMapping("/employer/package-transaction")
    public String createPackageTransaction(@RequestBody AssessmentTransactionResponse assessmentTransactionResponse,
                                          Principal principal) {

//        TrainingTransaction currentTraining = new TrainingTransaction();
        CourseTransaction currentCourse = new CourseTransaction();
        PackageTransaction currentSubscription = new PackageTransaction();
        try {
            Users user = JsfUtil.findOnline(principal);

            PackageSubscribe packageSubscribe = packageSubscribeService.findById(
                    Integer.parseInt(assessmentTransactionResponse.getAssessmentLine())
            );
//

            currentSubscription = packageTransactionService.findByUserAndPackageSubscribeAndStatus(
                    user,packageSubscribe
            );

            if(currentSubscription == null) {
                PackageTransaction packageTransaction = new PackageTransaction();

                packageTransaction.setUsers(user);
                packageTransaction.setPackageSubscribe(packageSubscribe);
                packageTransaction.setTransactionId(assessmentTransactionResponse.getTransactionId());
                packageTransaction.setAmount(assessmentTransactionResponse.getAmount());
                packageTransaction.setStatus(true);
                packageTransaction.setUserType(user.getUserType());
                packageTransaction.setEntryDate(new Date());

                packageTransactionService.addPackageTransaction(packageTransaction);
                return "redirect:/company-dashboard";
            }else {
                return "redirect:/company-dashboard";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
