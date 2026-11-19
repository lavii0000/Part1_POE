/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1.st10513315;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part 2 + Part 3
 * @author Student
 */
public class Message {
    
    private static final Random RANDOM = new Random();
     private static final String JSON_FILE = "messages.json";
 
    // Part 2 log of sent messages (text)
    private static final StringBuilder sentLog = new StringBuilder();
   
    private static int nextMessageNumber = 0;
    private static int totalSent = 0;
    
    // ---------- Part 3 arrays 
    private static final ArrayList<Message> sentMessage = new ArrayList<>();
    private static final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static final ArrayList<String> messageHash = new ArrayList<>();
    private static final ArrayList<String> messageID = new ArrayList<>();
    
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
    
    /** Used when reading a message back from the JSON file. */
    private Message(String id, String hash, String recipient, String text, boolean fromJson) {
        this.messageId = id;
        this.messageNumber = 0;
        this.recipient = recipient;
        this.text = text;
        this.hash = hash;
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
        
        String idStart = messageId.length() >= 2 ? messageId.substring(0, 2) : messageId;
        return (messageId.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }
 
    /** 1 = send, 2 = disregard, 3 = store. */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentLog.append(getDetails()).append("\n\n");
                totalSent++;
                sentMessage.add(this);
                register();
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(this);
                register();
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
    
    // keeps the Message Hash and Message ID arrays up to date
    private void register() {
        messageHash.add(hash);
        messageID.add(messageId);
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
 
     /** Stores the message in the stored array and in messages.json. */
    public void storeMessage() {
        storedMessages.add(this);
        register();
        writeStoredFile();
    }
    
       // ================= Part 3 =================

    /** Reads messages.json into the Stored Messages array (call once at start-up). */
    public static void loadStoredMessages() {
        Path path = Paths.get(JSON_FILE);
        if (!Files.exists(path)) {
            return;
        }
        try {
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            String str = "\"((?:\\\\.|[^\"\\\\])*)\"";
            Pattern p = Pattern.compile(
                    "\"messageID\"\\s*:\\s*" + str + "\\s*,\\s*"
                    + "\"messageHash\"\\s*:\\s*" + str + "\\s*,\\s*"
                    + "\"recipient\"\\s*:\\s*" + str + "\\s*,\\s*"
                    + "\"message\"\\s*:\\s*" + str);
            Matcher m = p.matcher(json);
            while (m.find()) {
                String id = unesc(m.group(1));
                String hash = unesc(m.group(2));
                if (messageHash.contains(hash)) {
                    continue; // already loaded
                }
                Message msg = new Message(id, hash, unesc(m.group(3)), unesc(m.group(4)), true);
                storedMessages.add(msg);
                msg.register();
            }
        } catch (IOException e) {
            System.out.println("Could not read messages.json: " + e.getMessage());
        }
    }   
    
        /** a. Sender/recipient of all stored messages. */
    public static String displayStoredRecipients() {
        if (storedMessages.isEmpty()) {
        return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
        sb.append("Sender: Developer | Recipient: ").append(m.recipient).append("\n");
        }
        return sb.toString().trim();
    }

            /** b. Longest stored message. */
    public static String displayLongestMessage() {
        String longest = null;
        for (Message m : storedMessages) {
            if (longest == null || m.text.length() > longest.length()) {
                longest = m.text;
            }
        }
        return longest == null ? "No stored messages." : longest;
    }

    /** c. Search by message ID (looks through sent, stored and disregarded). */
    public static String searchByMessageId(String id) {
        Message m = findById(id);
        if (m == null) {
            return "Message ID not found.";
        }
        return "Recipient: " + m.recipient + "\nMessage: " + m.text;
    }

    /** d. All sent or stored messages for one recipient. */
    public static ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> found = new ArrayList<>();
        for (Message m : sentMessage) {
            if (m.recipient.equals(recipient)) {
                found.add(m.text);
            }
        }
        for (Message m : storedMessages) {
            if (m.recipient.equals(recipient)) {
                found.add(m.text);
            }
        }
        return found;
    }
    
       /** e. Delete a message using its hash. */
    public static String deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).hash.equals(hash)) {
                String deleted = storedMessages.remove(i).text;
                removeFromHashIdArrays(hash);
                writeStoredFile();
                return "Message \"" + deleted + "\" successfully deleted.";
            }
        }
        for (int i = 0; i < sentMessage.size(); i++) {
            if (sentMessage.get(i).hash.equals(hash)) {
                String deleted = sentMessage.remove(i).text;
                removeFromHashIdArrays(hash);
                return "Message \"" + deleted + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    /** f. Full report: sent messages, then stored messages. */
    public static String displayReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== SENT MESSAGES =====\n");
        if (sentMessage.isEmpty()) {
            sb.append("None.\n");
        }
        for (Message m : sentMessage) {
            sb.append(m.getDetails()).append("\n\n");
        }
        sb.append("===== STORED MESSAGES =====\n");
        if (storedMessages.isEmpty()) {
            sb.append("None.\n");
        }
        for (Message m : storedMessages) {
            sb.append(m.getDetails()).append("\n\n");
        }
        return sb.toString().trim();
    }

    private static Message findById(String id) {
        for (Message m : sentMessage) {
            if (m.messageId.equals(id)) return m;
        }
        for (Message m : storedMessages) {
            if (m.messageId.equals(id)) return m;
        }
        for (Message m : disregardedMessages) {
            if (m.messageId.equals(id)) return m;
        }
        return null;
    }

    private static void removeFromHashIdArrays(String hash) {
        int i = messageHash.indexOf(hash);
        if (i >= 0) {
            messageHash.remove(i);
            messageID.remove(i);
        }
    }

    // ---- array getters (used by the unit tests) ----
    public static ArrayList<String> getSentMessageTexts() { return texts(sentMessage); }
    public static ArrayList<String> getDisregardedMessageTexts() { return texts(disregardedMessages); }
    public static ArrayList<String> getStoredMessageTexts() { return texts(storedMessages); }
    public static ArrayList<String> getMessageHashes() { return new ArrayList<>(messageHash); }
    public static ArrayList<String> getMessageIds() { return new ArrayList<>(messageID); }

    private static ArrayList<String> texts(ArrayList<Message> list) {
        ArrayList<String> out = new ArrayList<>();
        for (Message m : list) {
            out.add(m.text);
        }
        return out;
    }

    // ---- JSON helpers ----
    private static void writeStoredFile() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append("  {\n")
              .append("    \"messageID\": \"").append(esc(m.messageId)).append("\",\n")
              .append("    \"messageHash\": \"").append(esc(m.hash)).append("\",\n")
              .append("    \"recipient\": \"").append(esc(m.recipient)).append("\",\n")
              .append("    \"message\": \"").append(esc(m.text)).append("\"\n")
              .append("  }");
        }
        try (FileWriter fw = new FileWriter(JSON_FILE)) {
            fw.write("[\n" + sb + "\n]");
        } catch (IOException e) {
            System.out.println("Could not write messages.json: " + e.getMessage());
        }
    }

    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    private static String unesc(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char n = s.charAt(++i);
                sb.append(n == 'n' ? '\n' : n);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public String getMessageId() { return messageId; }
    public String getHash() { return hash; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }

    /** Resets static state (for unit tests). */
    public static void resetState() {
        sentLog.setLength(0);
        sentMessage.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHash.clear();
        messageID.clear();
        nextMessageNumber = 0;
        totalSent = 0;
    }
}
