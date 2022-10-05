/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AlhassanHussein
 */
@RestController     // for rest response
public class CacheController {
    @Autowired
    private CacheManager cacheManager;      // autowire cache manager

    // clear all cache using cache manager
    @RequestMapping(value = "clearCache")
    public void clearCache() {
        for (String name : cacheManager.getCacheNames()) {
            System.out.println("name..." + name);
            cacheManager.getCache(name).clear();            // clear cache by name
        }
    }
}
