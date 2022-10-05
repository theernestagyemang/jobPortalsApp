package com.ao.schoolerp;

import com.ao.schoolerp.entities.AppUser;
import com.ao.schoolerp.services.MyUserDetails;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public class AuthMiddleware {

    public static AppUser getCurrentUser(Principal principal) {
        MyUserDetails currentUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        if (currentUser == null) {
            return null;
        }
        return currentUser.getUser();
    }
}
