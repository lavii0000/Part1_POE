/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1.st10513315;

/**
 *
 * @author Student
 */
public class Login {

    private String Name;
    private String Surname;
    private String username;
    private String password;
    private String cellPhoneNumber;
    

    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {

        this.Name = firstName;
        this.Surname = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }
    
    // Checks if username contains an underscore
    // and is no more than 5 characters long
     public boolean checkUserName() {
    return username.contains("_") && username.length() <= 5;
    
    }

    // Checks password complexity
    public boolean checkPasswordComplexity() {

        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char c : password.toCharArray()) {

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            }

            if (Character.isDigit(c)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapital && hasNumber && hasSpecialCharacter;
    }

    // Checks South African cellphone number
    public boolean checkCellPhoneNumber() {

        return cellPhoneNumber.matches("^\\+27\\d{9}$");
    }

    // Registers the user
    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters long.";

        } else if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        } else if (!checkCellPhoneNumber()) {

            return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";

        } else {

            return "User has been registered successfully.";
        }
    }

    // Checks whether login details match
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }

    // Returns login status message
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {

            return "Welcome " + Name + " " + Surname
                    + ", it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }
    public int getUsernameLength() {
         if (username == null) {
        return 0;
    }

      int length = username.length();

         if (length > 5) {
        return length;
         }

    return length;
    }
    public boolean isPasswordLongEnough() {
       if (password == null) {
        return false;
         }

     int passwordLength = password.length();

      if (passwordLength >= 8) {
        return true;
         }

    return false;
     }
    public String getlName() {
    if (Name == null || Surname == null) {
        return "Name not available";
       }

    String fullName = Name + " " + Surname;

    if (fullName.length() > 0) {
        return fullName;
       }

    return "Name not available";
   }
    public boolean isUserRegistered() {
    if (checkUserName()
            && checkPasswordComplexity()
            && checkCellPhoneNumber()) {

        return true;
       }

    return false;
   }
   public boolean isPhoneNumberInternational() {
    if (cellPhoneNumber == null) {
        return false;
        }

    if (cellPhoneNumber.startsWith("+27")) {
        return true;
        }

    return false;
   }
    boolean returnLoginStatus() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    

