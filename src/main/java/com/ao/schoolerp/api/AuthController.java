package com.ao.schoolerp.api;

import com.ao.schoolerp.entities.AppUser;
import com.ao.schoolerp.entities.UserRole;
import com.ao.schoolerp.helpers.UserHelper;
import com.ao.schoolerp.helpers.UsersRole;
import com.ao.schoolerp.services.UserRoleService;
import com.ao.schoolerp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public AuthController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/user")
    public AppUser addUser(@RequestBody UserHelper userHelper){
        AppUser user = new AppUser();
        user.setName(userHelper.getName());
        user.setUsername(userHelper.getUsername());
        user.setPassword(userHelper.getPassword());
        user.setActive(true);

        return  userService.addUser(user);
    }

    //api to create the admin account
    @PostMapping("/create-admin")
    @ResponseBody
    public ResponseEntity createAdminAccount(@RequestBody UserHelper userHelper){
        List<AppUser> users = userService.findUsers();

        //let's check if any user exist in the database and then continue the process of stop it
        if(users.size() == 0){
            AppUser user = new AppUser();
            user.setName(userHelper.getName());
            user.setPassword(userHelper.getPassword());
            user.setUsername(userHelper.getUsername());
            user.setActive(true);
//            UserRole userRole = new UserRole(null,"ROLE_ADMIN");

           return ResponseEntity.ok().body(userService.addUser(user));
        }else {
            return  ResponseEntity.ok().body("Admin account already created!");
        }
    }

    @PostMapping("/assign-role-to-user")
    @ResponseBody
    public ResponseEntity assignRoleToUser(@RequestBody UsersRole userRole){
        return ResponseEntity.ok().body(userService.addRoleToUser(userRole.getUsername(), userRole.getRoleName()));
    }

    @GetMapping("/users")
    @ResponseBody
    public List<AppUser> getUsers(){
        return userService.findUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public AppUser findUserById(@PathVariable Integer id){
        return userService.findUserById(id);
    }

    @PostMapping("/role")
    public UserRole addRole(@RequestBody UserRole role){
        return  userRoleService.addRole(role);
    }

    @GetMapping("/roles")
    @ResponseBody
    public List<UserRole> getRoles(){
        return userRoleService.findRoles();
    }

    @GetMapping("/roles/{id}")
    @ResponseBody
    public UserRole findRoleById(@PathVariable Integer id){
        return userRoleService.findRoleById(id);
    }

}
