package com.EasyShiftScheduler.CalEnder.Entities;

import javax.management.ConstructorParameters;

public class UserOperations {

    public void login(String username, String password){
        //
    }

    public void resetPassword(String username){
        //
    }


    /** 
     * Checks if the password has a lowercase, uppercase, number, special character, and is at least 8 characters long.
     * @param password The password to check.
     * @return true if the password is strong, false otherwise.
    **/    
    public boolean checkPasswordStrength(String password){
        if (password.length() < 8) return false;
        boolean noSpaces = !password.contains(" ");
        boolean hasLower = password.matches(".*[a-z].*$");
        boolean hasUpper = password.matches(".*[A-Z].*$");
        boolean hasNumber = password.matches(".*\\d.*$");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()\\-+].*$");
        return noSpaces && hasUpper && hasLower && hasNumber && hasSpecial;
    }
}
