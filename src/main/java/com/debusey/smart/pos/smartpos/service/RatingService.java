package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Rating;
import com.debusey.smart.pos.smartpos.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public boolean addRating(Rating rating) {
        try {
            ratingRepository.save(rating);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Rating findById(Integer id) {
        return ratingRepository.findById(id).orElse(null);
    }

    public boolean deleteById(Integer id) {
        try {
            ratingRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
