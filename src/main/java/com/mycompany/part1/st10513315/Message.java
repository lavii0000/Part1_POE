/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1.st10513315;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;  

/**
 *
 * @author Student
 */
public class Message {
    
    private static final Random RANDOM = new Random();
 
    // Text logs instead of lists/arrays
    private static final StringBuilder sentLog = new StringBuilder();
    private static final StringBuilder storedJson = new StringBuilder();
    private static int storedCount = 0;
    private static int nextMessageNumber = 0;
    private static int totalSent = 0;
 
    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String hash;
 
    /** Normal constructor: auto-generates the message ID. */
    public Message(String recipient, String text) {
        this(generateMessageId(), recipient, text);
    }
 
    /** Constructor with a supplied ID (used by unit tests). */
    public Message(String messageId, String recipient, String text) {
        this.messageId = messageId;
        this.messageNumber = nextMessageNumber++;
        this.recipient = recipient;
        this.text = text;
        this.hash = createMessageHash();
    }
 
    private static String generateMessageId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
 
    /** Message ID must be no more than ten characters. */
    public boolean checkMessageID() {
        return messageId != null && !messageId.isEmpty() && messageId.length() <= 10;
    }
 
    public String checkRecipientCell() {
        return checkRecipientCell(recipient);
    }
 
    /** International code (+) followed by digits; at most ten digits after the country code. */
    public static String checkRecipientCell(String number) {
        if (number != null && number.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.";
    }
 
    public String checkMessageLength() {
        int length = text == null ? 0 : text.length();
        if (length <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (length - 250) + "; please reduce the size.";
    }
 
    /** Format: first two ID digits : message number : FIRSTWORDLASTWORD (all caps). */
    public String createMessageHash() {
        String trimmed = text.trim();
        int firstSpace = trimmed.indexOf(' ');
        int lastSpace = trimmed.lastIndexOf(' ');
 
        String firstWord;
        String lastWord;
        if (firstSpace == -1) {
            firstWord = trimmed;
            lastWord = trimmed;
        } else {
            firstWord = trimmed.substring(0, firstSpace);
            lastWord = trimmed.substring(lastSpace + 1);
        }
 
        firstWord = firstWord.replaceAll("[^A-Za-z0-9]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");
 
        return (messageId.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }
 
    /** 1 = send, 2 = disregard, 3 = store. */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentLog.append(getDetails()).append("\n\n");
                totalSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
 
    public String getDetails() {
        return "Message ID: " + messageId + "\n"
                + "Message Hash: " + hash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + text;
    }
 
    public static String printMessages() {
        if (totalSent == 0) {
            return "No messages have been sent.";
        }
        return sentLog.toString().trim();
    }
 
    public static int returnTotalMessages() {
        return totalSent;
    }
 
    /** Stores messages in messages.json. */
    public void storeMessage() {
        if (storedCount > 0) {
            storedJson.append(",\n");
        }
        storedJson.append("  {\n")
                .append("    \"messageID\": \"").append(esc(messageId)).append("\",\n")
                .append("    \"messageHash\": \"").append(esc(hash)).append("\",\n")
                .append("    \"recipient\": \"").append(esc(recipient)).append("\",\n")
                .append("    \"message\": \"").append(esc(text)).append("\"\n")
                .append("  }");
        storedCount++;
 
        try (FileWriter fw = new FileWriter("messages.json")) {
            fw.write("[\n" + storedJson + "\n]");
        } catch (IOException e) {
            System.out.println("Could not write messages.json: " + e.getMessage());
        }
    }
 
    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
 
    public String getMessageId() { return messageId; }
    public String getHash() { return hash; }
 
    /** Resets static state (for unit tests). */
    public static void resetState() {
        sentLog.setLength(0);
        storedJson.setLength(0);
        storedCount = 0;
        nextMessageNumber = 0;
        totalSent = 0;
    }
}
 