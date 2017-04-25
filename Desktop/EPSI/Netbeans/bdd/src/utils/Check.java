/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Date;

/**
 *
 * @author Mary   
 */
public class Check {
    
    public static void checkIsNull(String field, String name) {
        if (field == null) {
            throw new NullPointerException(String.format("%s cannot be null",name));
        }
    }
    
    public static void checkIsEmpty(String field, String name) {
        if (field.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s cannot be empty",name));
        }
    }
    
    public static void checkIsNull(Date field, String name) {
        if (field == null) {
            throw new NullPointerException(String.format("%s cannot be null",name));
        }
    }
    
    public static void checkLetters(String field, String name) {    
        if ( !Valid.isValidOnlyLetters(field) ) {
            throw new IllegalArgumentException(String.format("%s is wrong",name));
       }   
    }
   
    public static void checkPhoneNumber(String phone) {
        if ( !Valid.isValidPhoneNumber(phone) ) {
            throw new IllegalArgumentException("phone is wrong");
        }
    }
    
    public static void checkEmail(String email) {
        if ( !Valid.isValidEmail(email) ) {
            throw new IllegalArgumentException("email is wrong");
        }
    }
    
    public static void checkDate(String date) {
        if (!Valid.isValidDate(date)) {
           throw new IllegalArgumentException("date is wrong");
        }
    }
    
}
