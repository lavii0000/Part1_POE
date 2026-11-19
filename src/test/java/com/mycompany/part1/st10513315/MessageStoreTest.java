/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1.st10513315;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageStoreTest {
    
    public MessageStoreTest() {
    }
    
    private MessageStore store;
    private Message m1, m2, m3, m4, m5;

    @BeforeEach
    public void setUp() {
        store = new MessageStore("Developer");
        m1 = new Message("1000000001", "+27834557896", "Did you get the cake?");
        m2 = new Message("1000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m3 = new Message("1000000003", "+27834484567", "Yohoooo, I am at your gate.");
        m4 = new Message("0838884567", "0838884567", "It is dinner time!");
        m5 = new Message("1000000005", "+27838884567", "Ok, I am leaving without you.");
        store.addMessage(m1, "Sent");
        store.addMessage(m2, "Stored");
        store.addMessage(m3, "Disregard");
        store.addMessage(m4, "Sent");
        store.addMessage(m5, "Stored");
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("Did you get the cake?", "It is dinner time!"));
        assertEquals(expected, store.getSentMessages());
    }

    @Test
    public void testDisplayLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", store.longestMessage());
    }

    @Test
    public void testSearchByMessageId() {
        assertEquals("It is dinner time!", store.searchByMessageId("0838884567"));
    }

    @Test
    public void testSearchByRecipient() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."));
        assertEquals(expected, store.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteByHash() {
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                store.deleteByHash(m2.getHash()));
    }

    @Test
    public void testDisplayReport() {
        String report = store.report();
        assertTrue(report.contains(m1.getHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}