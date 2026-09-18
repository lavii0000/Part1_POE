/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1.st10513315;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Get user's registration details
        System.out.println("Enter your name:");
        String Name = scanner.nextLine();

        System.out.println("Enter your surname:");
        String Surname = scanner.nextLine();

        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        System.out.println("Enter your cell phone number:");
        String cellPhoneNumber = scanner.nextLine();

        // Create a Login object
        Login user = new Login(
                Name,
                Surname,
                username,
                password,
                cellPhoneNumber
        );

        // Register the user
        System.out.println(user.registerUser());

        // If registration details are valid, ask the user to log in
        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            System.out.println("\n--- LOGIN ---");

            System.out.println("Enter your username:");
            String loginUsername = scanner.nextLine();

            System.out.println("Enter your password:");
            String loginPassword = scanner.nextLine();

            // Check login details
            boolean loginSuccessful = user.loginUser(loginUsername, loginPassword);

            System.out.println(user.returnLoginStatus());
        }
    }
}