/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1.st10513315;


import static org.junit.jupiter.api.Assertions.*;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Student
 */
public class MessageTest {
   
 
    @BeforeEach
    void setUp() {
        Message.resetState();
    }
 
    private Message testCase1() {
        return new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }
 
    private Message testCase2() {
        return new Message("1234567890", "08575975889", "Hi Keegan, did you receive the payment?");
    }
 
    @Test
    void messageLengthSuccess() {
        assertEquals("Message ready to send.", testCase1().checkMessageLength());
    }
 
    @Test
    void messageLengthFailure() {
        String longText = "a".repeat(260);
        Message m = new Message("0012345678", "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", m.checkMessageLength());
    }
 
    @Test
    void recipientSuccess() {
        assertEquals("Cell phone number successfully captured.", testCase1().checkRecipientCell());
    }
 
    @Test
    void recipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.", testCase2().checkRecipientCell());
    }
 
    @Test
    void messageHashCorrect() {
        assertEquals("00:0:HITONIGHT", testCase1().createMessageHash());
    }
 
    @Test
    void secondMessageHashUsesNextNumber() {
        testCase1();
        assertEquals("12:1:HIPAYMENT", testCase2().createMessageHash());
    }
 
    @Test
    void messageIdCreated() {
        Message m = new Message("+27718693002", "Hello there");
        assertTrue(m.checkMessageID());
        assertEquals(10, m.getMessageId().length());
    }
 
    @Test
    void messageSent() {
        assertEquals("Message successfully sent.", testCase1().SentMessage(1));
    }
 
    @Test
    void messageDisregarded() {
        assertEquals("Press 0 to delete the message.", testCase2().SentMessage(2));
    }
 
    @Test
    void messageStored() {
        assertEquals("Message successfully stored.", testCase1().SentMessage(3));
    }
 
    @Test
    void totalMessagesCountsOnlySent() {
        testCase1().SentMessage(1);
        testCase2().SentMessage(2);
        assertEquals(1, Message.returnTotalMessages());
    }
 
    @Test
    void printMessagesListsSentMessages() {
        testCase1().SentMessage(1);
        String out = Message.printMessages();
        assertTrue(out.contains("+27718693002"));
        assertTrue(out.contains("00:0:HITONIGHT"));
    }
}


 
     
 


