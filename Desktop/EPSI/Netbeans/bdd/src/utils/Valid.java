/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


/**
 *
 * @author Mary
 */
public class Valid {
    
    /**
     * Method which checks the name format (if it contains only letters and 
     * apostrophe for name, and only letters and hyphen for first name)
     * @param field, the field to be checked 
     * @param nameField, the name of the field to be checked
     * @return true if the field is in the good format, false otherwise
     */
    public static boolean isValidLetters(String field, String nameField) {
        if ( nameField.equals("name") ) { // apostrophe authorized in name field
            if ( field.matches("[a-zA-Z]+") || field.matches("[a-zA-Z]+('[a-zA-Z]+)") ) {
                return true;
            }
        }
        else if ( nameField.equals("first name") ) { // hyphen authorized in first name
            if ( field.matches("[a-zA-Z]+") || field.matches("[a-zA-Z]+.[-].[a-zA-Z]+") ) {
                return true;
            }
        }
        return false;
    }
    
     /**
     * Method which checks the phone number format (if it contains only numbers or +)
     * @param phone, the field to be checked 
     * @return true if the field is in the good format, false otherwise
     */
    public static boolean isValidPhoneNumber(String phone) {
        if ( phone.matches("[0-9]+") && phone.length() >= 10 ) { // vérifier qu'il y ait un + ?
            return true;
        }
        return false;
    }
    
    /**
     * Method which checks the e-mail number format (if it contains one @)
     * @param email, the field to be checked
     * @return true if the field is in the good format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if ( email.matches(".*@.*") ) {
            return true;
        }
        return false;
    }
    
    /*private Date today() {
        return Calendar.getInstance().getTime();
    }*/
    
    /**
     * Method which checks if the date is not empty
     * @param field, the field to be checked 
     * @return true if the field is not empty, false otherwise
     */
    public static boolean isValidDate(String date) {
        if (!date.isEmpty()) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Method which checks if the address is not empty
     * @param address, the field to be checked 
     * @return true if the field is not empty, false otherwise
     */
    public static boolean isValidAddress(String address) {
        if (!address.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * Method which checks if the postcode is not empty
     * @postcode, the field to be checked 
     * @return true if the field is not empty, false otherwise
     */
    public static boolean isValidPostCode(String postCode) {
        if (!postCode.isEmpty()) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Method which checks if the city is not empty
     * @city, the field to be checked 
     * @return true if the field is not empty, false otherwise
     */
    public static boolean isValidCity(String city) {
        if ( city.matches("[a-zA-Z]+") || city.matches("[a-zA-Z]+('[a-zA-Z]+)") || city.matches("[a-zA-Z]+(-[a-zA-Z]+)") ) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Method which checks if the password and its confirmation match
     * @param pwd, pwdConfirmation the field to be checked
     * @return true if fields match, false otherwise
     */
    public static boolean isValidPwd(String pwd, String pwdConfirmation) {
        if ( pwd.equals(pwdConfirmation) ) {
            return true;
        }
        return false;
    }
  
}
