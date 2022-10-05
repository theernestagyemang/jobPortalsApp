package com.ao.schoolerp.controllers;

import com.ao.schoolerp.AuthMiddleware;
import com.ao.schoolerp.entities.AppUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import java.util.HashSet;
import java.util.Set;


@Controller
public class MainController {

    @GetMapping("/home")
    public String index(Model model, Principal principal){
        AppUser user = AuthMiddleware.getCurrentUser(principal);
        Set<String> userRole = new HashSet<>();

        System.out.println("/n/n==== user details =====");
        System.out.println(user.getName());
        System.out.println(user.getUsername());
        user.getRoles().forEach(role -> {
            userRole.add(role.getName());
            System.out.println("/n/n==== user =====");
            System.out.println(role.getName());
        });
        // switch ()
        if(userRole.contains("ROLE_ADMIN")){
            return "index";
        } else if (userRole.contains("ROLE_HOSTEL_MANAGER")) {  // || userRole.contains("ROLE_HOSTEL_USER")
            return "hostel/index";
        } else if (userRole.contains("ROLE_LIBRARY_MANAGER")) {  // || userRole.contains("ROLE_LIBRARY_USER")
            return "library/index";
        }else{
            return "redirect:/login";
        }

    }
}
