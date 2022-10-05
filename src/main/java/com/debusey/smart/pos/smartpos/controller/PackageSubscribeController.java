package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.db.PackageSubscribeHelper;
import com.debusey.smart.pos.smartpos.entity.PackageSubscribe;
import com.debusey.smart.pos.smartpos.service.PackageSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PackageSubscribeController {
    @Autowired
    private PackageSubscribeService packageSubscribeService;

    @GetMapping("/admin/package-page")
    public String getPackagePage() {
        return "admin/package-manager";
    }

    @GetMapping("/employer-package/subscription")
    public String getPackagePagePublic(Model model) {
        List<PackageSubscribe> packages = packageSubscribeService.findAll();
        model.addAttribute("packages", packages);
        return "employer/employer-subscriptions";
    }

    @PostMapping("/admin/add-package")
    @ResponseBody
    public ResponseEntity<String> addPackage(@RequestBody PackageSubscribeHelper packageSubscribeHelper) {

        PackageSubscribe packageSubscribe = new PackageSubscribe();
        packageSubscribe.setName(packageSubscribeHelper.getName());
        packageSubscribe.setPrice(packageSubscribeHelper.getPrice());
        packageSubscribe.setComment1(packageSubscribeHelper.getComment1());
        packageSubscribe.setComment2(packageSubscribeHelper.getComment2());
        packageSubscribe.setComment3(packageSubscribeHelper.getComment3());
        packageSubscribe.setComment4(packageSubscribeHelper.getComment4());
        packageSubscribe.setComment5(packageSubscribeHelper.getComment5());
        packageSubscribe.setComment6(packageSubscribeHelper.getComment6());

        try {
            String status = packageSubscribeService.addPackage(packageSubscribe) ? "SUCCESS" : "FAILED";
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/packages")
    @ResponseBody
    public List<PackageSubscribe> findAll() {
        return packageSubscribeService.findAll();
    }

    @GetMapping("/admin/package/{id}")
    @ResponseBody
    public PackageSubscribe findById(@PathVariable Integer id) {
        return packageSubscribeService.findById(id);
    }

    @GetMapping("/admin/packages/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            String status = packageSubscribeService.deleteById(id) ? "SUCCESS" : "FAILED";
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
