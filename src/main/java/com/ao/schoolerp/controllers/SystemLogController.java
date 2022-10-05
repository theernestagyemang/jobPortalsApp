package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.SystemLog;
import com.ao.schoolerp.services.SystemLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/library")
public class SystemLogController {
    private SystemLogService systemLogService;

    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    @GetMapping("/system-log")
    public String viewSystemLog (Model model, Principal principal){

        List<SystemLog> systemLogList = systemLogService.findAll();
        model.addAttribute("systemLogs", systemLogList);
        return "hostel/system-log";
    }

    @PostMapping("/create-system-log")
    @ResponseBody
    public ResponseEntity addSystemLog(@RequestBody SystemLog systemLog){
//       if(systemLogService.save(hostel)  return "redirect: /hostels" : return "re" ;
        boolean status = systemLogService.save(systemLog);
        if(status){
            return ResponseEntity.ok().body("SUCCESS");
        }else {
            return ResponseEntity.ok().body("FAILED");
        }
    }

    @GetMapping("/system-log/{id}")
    @ResponseBody
    public SystemLog findById(@PathVariable Integer id, Model model){
        return systemLogService.findById(id).get();
    }
}
