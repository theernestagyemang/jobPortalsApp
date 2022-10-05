/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.Base64;

/**
 * @author Admin
 */
public class ImageUtil {
    public String getImgData(byte[] byteData) {
        if (byteData == null) {
            return null;
        }
        return Base64.getMimeEncoder().encodeToString(byteData);
    }
}


