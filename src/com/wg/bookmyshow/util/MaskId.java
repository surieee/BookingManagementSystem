package com.wg.bookmyshow.util;

public class MaskId {

   
    public static String maskId(String id) {
        if (id == null || id.length() <= 4) {
            return "****"; // Return masked value if ID is too short
        }
        return "****" + id.substring(id.length() - 4); // Mask all but last 4 digits
    }


}
