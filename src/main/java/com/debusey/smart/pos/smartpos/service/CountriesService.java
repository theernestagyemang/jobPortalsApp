/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Countries;
import com.debusey.smart.pos.smartpos.repo.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class CountriesService {

    @Autowired
    public CountriesRepository productRepository;


    public List<Countries> getAllCountries() {
        List<Countries> items = new ArrayList<>();
        productRepository.findAll().forEach(items::add);

        return items;
    }

    public boolean deleteById(Integer t) {
        try {
            productRepository.deleteById(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean updateCountries(Countries t) {
        try {
            productRepository.save(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void updateCountries(List<Countries> t) {
        productRepository.saveAll(t);
    }

    public void deleteCountries(Countries t) {
        productRepository.delete(t);
    }

    public Optional<Countries> getCountries(Integer id) {
        return productRepository.findById(id);
    }

    public List<Countries> getCountriesByName(String name) {
        return productRepository.findByName(name);
    }


    public List<String> getCountries() {
        List<String> listOfCountries = new ArrayList();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            String cc = obj.getDisplayCountry();
            listOfCountries.add(cc);
        }
        return listOfCountries;
    }


}
