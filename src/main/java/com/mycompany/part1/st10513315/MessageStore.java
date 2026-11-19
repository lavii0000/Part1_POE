/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1.st10513315;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class MessageStore {
 
    private final ArrayList<String> sentMessages = new ArrayList<>();
    private final ArrayList<String> disregardedMessages = new ArrayList<>();
    private final ArrayList<String> storedMessages = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> messageIds = new ArrayList<>();

    private final ArrayList<String> recipients = new ArrayList<>();
    private final ArrayList<String> texts = new ArrayList<>();
    private final ArrayList<String> flags = new ArrayList<>();

    private final String sender;

    public MessageStore(String sender) {
        this.sender = sender;
    }

    public void addMessage(String id, String hash, String recipient, String text, String flag) {
        messageIds.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);
        texts.add(text);
        flags.add(flag);
        switch (flag) {
            case "Sent": sentMessages.add(text); break;
            case "Stored": storedMessages.add(text); break;
            default: disregardedMessages.add(text);
        }
    }

    public void addMessage(Message m, String flag) {
        addMessage(m.getMessageId(), m.getHash(), m.getRecipient(), m.getText(), flag);
    }

private static String esc(String s) {
    return s.replace("\\", "\\\\").replace("\"", "\\\"");
}

private static String unesc(String s) {
    return s.replace("\\\"", "\"").replace("\\\\", "\\");
}

public void saveStoredToJson(String path) throws IOException {
    StringBuilder sb = new StringBuilder("[\n");
    boolean first = true;
    for (int i = 0; i < flags.size(); i++) {
        if (flags.get(i).equals("Stored")) {
            if (!first) {
                sb.append(",\n");
            }
            first = false;
            sb.append("  {\"messageId\":\"").append(esc(messageIds.get(i)))
              .append("\",\"hash\":\"").append(esc(messageHashes.get(i)))
              .append("\",\"recipient\":\"").append(esc(recipients.get(i)))
              .append("\",\"message\":\"").append(esc(texts.get(i))).append("\"}");
        }
    }
    sb.append("\n]");
    Files.write(Paths.get(path), sb.toString().getBytes());
}

public void loadStoredFromJson(String path) throws IOException {
    String json = new String(Files.readAllBytes(Paths.get(path)));
    String str = "\"((?:[^\"\\\\]|\\\\.)*)\"";
    Pattern p = Pattern.compile("\\{\\s*\"messageId\"\\s*:\\s*" + str
            + "\\s*,\\s*\"hash\"\\s*:\\s*" + str
            + "\\s*,\\s*\"recipient\"\\s*:\\s*" + str
            + "\\s*,\\s*\"message\"\\s*:\\s*" + str + "\\s*\\}");
    Matcher m = p.matcher(json);
    while (m.find()) {
        String id = unesc(m.group(1));
        if (!messageIds.contains(id)) {
            addMessage(id, unesc(m.group(2)), unesc(m.group(3)), unesc(m.group(4)), "Stored");
        }
    }
}

    public String displaySenderAndRecipient() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i).equals("Stored")) {
                sb.append("Sender: ").append(sender)
                  .append(" | Recipient: ").append(recipients.get(i)).append("\n");
            }
        }
        return sb.length() == 0 ? "No stored messages." : sb.toString();
    }

    public String longestMessage() {
        String longest = "";
        for (int i = 0; i < texts.size(); i++) {
            if (!flags.get(i).equals("Disregard") && texts.get(i).length() > longest.length()) {
                longest = texts.get(i);
            }
        }
        return longest.isEmpty() ? "No messages." : longest;
    }

    public String searchByMessageId(String id) {
        int i = messageIds.indexOf(id);
        return i < 0 ? "Message ID not found." : texts.get(i);
    }

    public String displayById(String id) {
        int i = messageIds.indexOf(id);
        return i < 0 ? "Message ID not found."
                : "Recipient: " + recipients.get(i) + "\nMessage: " + texts.get(i);
    }

    public ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(recipient) && !flags.get(i).equals("Disregard")) {
                found.add(texts.get(i));
            }
        }
        return found;
    }

    public String deleteByHash(String hash) {
        int i = messageHashes.indexOf(hash);
        if (i < 0) {
            return "Message hash not found.";
        }
        String text = texts.get(i);
        switch (flags.get(i)) {
            case "Sent": sentMessages.remove(text); break;
            case "Stored": storedMessages.remove(text); break;
            default: disregardedMessages.remove(text);
        }
        messageIds.remove(i);
        messageHashes.remove(i);
        recipients.remove(i);
        texts.remove(i);
        flags.remove(i);
        return "Message \"" + text + "\" successfully deleted.";
    }

    public String report() {
        StringBuilder sb = new StringBuilder("===== MESSAGE REPORT =====\n");
        for (int i = 0; i < flags.size(); i++) {
            if (!flags.get(i).equals("Disregard")) {
                sb.append("Message Hash: ").append(messageHashes.get(i)).append("\n")
                  .append("Message ID: ").append(messageIds.get(i)).append("\n")
                  .append("Recipient: ").append(recipients.get(i)).append("\n")
                  .append("Message: ").append(texts.get(i)).append("\n")
                  .append("Flag: ").append(flags.get(i)).append("\n\n");
            }
        }
        return sb.toString();
    }

    public ArrayList<String> getSentMessages() { return sentMessages; }
    public ArrayList<String> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<String> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIds() { return messageIds; }
}

