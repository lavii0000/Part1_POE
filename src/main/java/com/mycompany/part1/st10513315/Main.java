/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1.st10513315;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Part 1 (register + login) continued into Part 2 (QuickChat messaging).
 * @author Student
 */
public class Main {

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
    
        // ---------- Part 3: Stored Messages menu ----------
    private static void runStoredMessagesMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- STORED MESSAGES ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages to a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display report");
            System.out.println("g) Back");
            System.out.print("Choose: ");
            String pick = scanner.nextLine().trim().toLowerCase();

            switch (pick) {
                case "a":
                    System.out.println(Message.displayStoredRecipients());
                    break;
                case "b":
                    System.out.println("Longest stored message: " + Message.displayLongestMessage());
                    break;
                case "c":
                    System.out.print("Enter message ID: ");
                    System.out.println(Message.searchByMessageId(scanner.nextLine().trim()));
                    break;
                case "d":
                    System.out.print("Enter recipient cell number: ");
                    ArrayList<String> found = Message.searchByRecipient(scanner.nextLine().trim());
                    if (found.isEmpty()) {
                        System.out.println("No messages found for that recipient.");
                    } else {
                        for (String s : found) {
                            System.out.println("- " + s);
                        }
                    }
                    break;
                case "e":
                    System.out.print("Enter message hash: ");
                    System.out.println(Message.deleteByHash(scanner.nextLine().trim()));
                    break;
                case "f":
                    System.out.println(Message.displayReport());
                    break;
                case "g":
                    return;
                default:
                    System.out.println("Please choose a, b, c, d, e, f or g.");
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