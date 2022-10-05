package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.AppUser;
import com.ao.schoolerp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
//@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/admin/users")
    public String viewUser (Model model, Principal principal){
//
//        List<AppUser> userList = userService.findUsers();
//        model.addAttribute("users", userList);
        return "admin/users";
    }

//    @PostMapping("/create-user")xsb
//    @ResponseBody
//    public ResponseEntity addUser(@RequestBody AppUser user){
////       if(userService.save(hostel)  return "redirect: /hostels" : return "re" ;
//        AppUser status = userService.addUser(user);   //addUser
//
//        if(status.isActive()){
//            return ResponseEntity.ok().body("SUCCESS");
//        }else {
//            return ResponseEntity.ok().body("FAILED");
//        }
//    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public AppUser findById(@PathVariable Integer id, Model model){
        return userService.findUserById(id);
    }

    //redirect to forgot password page
    @GetMapping("/forgot-password")
    public String forgotPassword(){
            return "forgot-password";
    }

    //registering students
    //when they report at the hostel facility
    @GetMapping("/accomodation/registration")
    public String accomdationRegistration(){
        return "hostel/accomodation/registration";
    }

    //student making online reservation from their
    //students portal
    @GetMapping("/accomodation/reservation")
    public String accomdationReservation(){
        return "hostel/accomodation/reservation";
    }

    //when student are done with accomodation
    //and sign off their usage
    @GetMapping("/accomodation/exit")
    public String accomdationExit(){
        return "hostel/accomodation/exit";
    }

    //when student are done with accomodation
    //and sign off their usage
    @GetMapping("/transactions/payment")
    public String accomdationPayment(){
        return "hostel/transactions/payment";
    }


    //hostel maintenance
    @GetMapping("/maintenance/record")
    public String maintenanceRecord(){
        return "hostel/maintenance/record";
    }


    //hostel maintenance
    @GetMapping("/settings/users")
    public String systemUser(){
        return "hostel/settings/users";
    }
}
