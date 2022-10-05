/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.entity.WebUtils;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import com.debusey.smart.pos.smartpos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;


@Controller
public class PasswordController {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender emailService;
    @Value("${spring.mail.username}")
    private String appTitle;
    @Value("${app.hostName}")
    private String appUrl;

    public static void sendApplicantSMS(String apiUrl, String key, String message, String phoneNumber) throws Exception {

        URL url = new URL("https://apps.mnotify.net/smsapi?key=" + key + "&to=" + phoneNumber + "&msg=" + message + "&sender_id=XJOBS");

        URLConnection connection = url.openConnection();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            inputLine = in.readLine().trim();
            //input line contains the response from mNotify
            //System.out.println("inputLine "+inputLine);

        }
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    // Display forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("recover-pass");
    }

    // Process form submission from forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {


        if (!isValid(userEmail)) {
            modelAndView.addObject("errorMessage", "Invalid Credential");
            modelAndView.setViewName("recover-pass");
            return modelAndView;
        }
        // Lookup user in database by e-mail
        Optional<Users> optional = userService.findByUsername(userEmail);

        if (!optional.isPresent()) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");

        } else {

            // Generate random 36-character string token for reset password
            Users user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            if (userService.updateUsers(user)) {
            } else {
            }



            String msg = "To reset your password, click the link below:\n" + appUrl + "/reset?token=" + user.getResetToken();
            String tel = user.getTelephone();


            try {


                SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
                passwordResetEmail.setTo(user.getUsername());
                passwordResetEmail.setFrom(appTitle);
                passwordResetEmail.setSubject("Password Reset Request");
                passwordResetEmail.setText(msg);

                emailService.send(passwordResetEmail);

                modelAndView.addObject("msg", "A password reset link has been sent to " + userEmail);
            } catch (MailException e) {
                e.printStackTrace();
            }

        }

        modelAndView.setViewName("login");
        return modelAndView;

    }

    // Display form to reset password
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        Optional<Users> user = userService.findByResetToken(token);


        if (user.isPresent()) { // Token found in DB
            //  System.out.println("res.... is present");
            modelAndView.addObject("resetToken", token);
        } else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        // System.out.println("requestParams...."+requestParams);
        // Find the user associated with the reset token
        Optional<Users> user = userService.findByResetToken(requestParams.get("token"));


        // This should always be non-null but we check just in case
        if (user.isPresent()) {

            Users resetUser = user.get();
            // System.out.println("user found...."+user);
            String pass = requestParams.get("password");
            // Set new password
            resetUser.setPassword(encoder.encode(pass));

            // Set the reset token to null so it cannot be used again
            resetUser.setResetToken(null);

            // Save user
            if (userService.updateUsers(resetUser)) {
                //  System.out.println("resetUser..."+resetUser.getId());
            }

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("msg", "You have successfully reset your password.  You may now login.");

            modelAndView.setViewName("redirect:login");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("resetPassword");
        }

        return modelAndView;
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:login");
    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        //   System.out.println("Starting....");

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.userRole(loginedUser);
        Users user = loginedUser.getUser();
        model.addAttribute("user", user);
        model.addAttribute("seeker", new JobSeeker());

        String passwordview = null;

        switch (userInfo) {
            case "ROLE_ADMIN":
            case "ROLE_RECRUITER":
            case "ROLE_HEAD_RECRUITER":
                return "staff-change-password";

            case "ROLE_COMPANY":
                return "comp-change-password";

            case "ROLE_JOBSEEKER":
                return "seeker-change-password";

        }


        return passwordview;
    }

    @PostMapping("/change-password")
    @ResponseBody
    public boolean updatePassword(Principal principal, String pass) {

        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return false;
        }
        user.setPassword(encoder.encode(pass));
        return userService.updateUsers(user);
    }


}
