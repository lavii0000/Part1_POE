/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1.st10513315;


import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.ArrayList; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Student
 */
public class MessageTest {
   
    private Message m1, m2, m3, m4, m5;

    @BeforeEach
    public void setUp() {
        Message.resetState();

        // Test data from the brief (flags: Sent / Stored / Disregard)
        m1 = new Message("0000000001", "+27834557896", "Did you get the cake?");
        m1.SentMessage(1);

        m2 = new Message("0000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.SentMessage(3);

        m3 = new Message("0000000003", "+27834484567", "Yohoooo, I am at your gate.");
        m3.SentMessage(2);

        m4 = new Message("0000000004", "0838884567", "It is dinner time!");
        m4.SentMessage(1);

        m5 = new Message("0000000005", "+27838884567", "Ok, I am leaving without you.");
        m5.SentMessage(3);
    }

    @Test
    public void sentMessagesArrayCorrectlyPopulated() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("Did you get the cake?", "It is dinner time!"));
        assertEquals(expected, Message.getSentMessageTexts());
    }

    @Test
    public void displayTheLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                Message.displayLongestMessage());
    }

    @Test
    public void searchForMessageId() {
        String result = Message.searchByMessageId("0000000004");
        assertTrue(result.contains("0838884567"));
        assertTrue(result.contains("It is dinner time!"));
    }

    @Test
    public void searchAllMessagesForARecipient() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."));
        assertEquals(expected, Message.searchByRecipient("+27838884567"));
    }

    @Test
    public void deleteMessageUsingHash() {
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                Message.deleteByHash(m2.getHash()));
    }

    @Test
    public void displayReport() {
        String report = Message.displayReport();
        assertTrue(report.contains(m1.getHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains(m4.getHash()));
        assertTrue(report.contains("0838884567"));
        assertTrue(report.contains("It is dinner time!"));
    }

    @Test
    public void hashAndIdArraysPopulated() {
        assertEquals(5, Message.getMessageHashes().size());
        assertEquals(5, Message.getMessageIds().size());
        assertTrue(Message.getDisregardedMessageTexts().contains("Yohoooo, I am at your gate."));
    }
}

 
     
 


