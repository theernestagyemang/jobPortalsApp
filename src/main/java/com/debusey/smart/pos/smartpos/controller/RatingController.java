package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.entity.Rating;
import com.debusey.smart.pos.smartpos.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/admin/rating-page")
    public String getRatingPage() {
        return "admin/Ratings";
    }


    @PostMapping("/admin/rating")
    @ResponseBody
    public ResponseEntity<String> addRating(@RequestBody Rating rating) {
        try {
            String status = ratingService.addRating(rating) ? "SUCCESS" : "FAILED";
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/ratings")
    @ResponseBody
    public List<Rating> findAll() {
        return ratingService.findAll();
    }

    @GetMapping("/admin/rating/{id}")
    @ResponseBody
    public Rating findById(@PathVariable Integer id) {
        return ratingService.findById(id);
    }

    @GetMapping("/admin/delete-rating/{id}")
    @ResponseBody
    public boolean deleteById(@PathVariable Integer id) {
        return ratingService.deleteById(id);
    }
}
