/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1.st10513315;

import java.util.Scanner;
/**
 * Part 1 (register + login) continued into Part 2 (QuickChat messaging).
 * @author Student
 */
public class Quickchat {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // ---------- PART 1: registration details ----------
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

        // Create a Login object (your own Part 1 class)
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

            System.out.println(user.returnLoginStatus(loginUsername, loginPassword));

            // ---------- PART 2: only if logged in successfully ----------
            if (loginSuccessful) {
                runQuickChat(scanner);
            }
        }
    }

    private static void runQuickChat(Scanner scanner) {
        System.out.println("=========================");
        System.out.println("   Welcome to QuickChat.");
        System.out.println("=========================");

        System.out.print("How many messages do you wish to enter? ");
        int limit = readInt(scanner);
        int entered = 0;

        while (true) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
            int option = readInt(scanner);

            if (option == 1) {
                if (entered >= limit) {
                    System.out.println("You have reached your message limit.");
                    continue;
                }
                while (entered < limit) {
                    String recipient = askRecipient(scanner);
                    String text = askText(scanner);

                    Message msg = new Message(recipient, text);
                    System.out.println("1) Send Message\n2) Disregard Message\n3) Store Message to send later");
                    int choice = readInt(scanner);
                    System.out.println(msg.SentMessage(choice));
                    if (choice == 1) {
                        System.out.println(msg.getDetails());
                    }
                    entered++;
                }
                System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
            } else if (option == 2) {
                System.out.println("Coming Soon.");
            } else if (option == 3) {
                System.out.println("Goodbye.");
                break;
            } else {
                System.out.println("Please choose 1, 2 or 3.");
            }
        }
    }

    private static String askRecipient(Scanner scanner) {
        while (true) {
            System.out.print("Recipient cell number (e.g. +27718693002): ");
            String r = scanner.nextLine().trim();
            String result = Message.checkRecipientCell(r);
            System.out.println(result);
            if (result.startsWith("Cell phone number successfully")) {
                return r;
            }
        }
    }

    private static String askText(Scanner scanner) {
        while (true) {
            System.out.print("Enter message: ");
            String t = scanner.nextLine();
            if (t.length() <= 250) {
                System.out.println("Message ready to send.");
                return t;
            }
            System.out.println("Please enter a message of less than 250 characters.");
           
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a number: ");
            }
        }
    } 
}