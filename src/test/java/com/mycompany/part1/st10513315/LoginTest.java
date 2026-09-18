/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1.st10513315;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author Student
 */
public class LoginTest {
    
  
    @Test
    public void testCheckUserNameCorrect() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&se@ke991",
                "+27838898976"
        );

        assertEquals(true, user.checkUserName());
        System.out.println("Username test passed");
    }

    @Test
    public void testCheckUserNameIncorrect() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyle!!!!!!!",
                "Ch&se@ke991",
                "+27838898976"
        );

        assertEquals(false, user.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexityCorrect() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&se@ke991",
                "+27838898976"
        );

        assertEquals(true, user.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexityIncorrect() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "password",
                "+27838898976"
        );

        assertEquals(false, user.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumberCorrect() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&se@ke991",
                "+27838898976"
        );

        assertEquals(true, user.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberIncorrect() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&se@ke991",
                "08966553"
        );

        assertEquals(false, user.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&se@ke991",
                "+27838898976"
        );

        assertEquals(true, user.loginUser("kyl_1", "Ch&se@ke991"));
    }

    @Test
    public void testLoginFailed() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&se@ke991",
                "+27838898976"
        );

        assertEquals(false, user.loginUser("kyl_1", "password"));
    }

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&se@ke991",
                "+27838898976"
        );

        assertEquals(true, user.checkUserName());
    }
}
