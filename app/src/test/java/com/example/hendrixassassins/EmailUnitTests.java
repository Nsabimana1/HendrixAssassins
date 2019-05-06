package com.example.hendrixassassins;

import com.example.hendrixassassins.email.Email;

import org.junit.Test;

import javax.mail.MessagingException;

import static junit.framework.TestCase.assertEquals;

public class EmailUnitTests {
    final String recipient = "test@gmail.com";
    final String subject = "Test Message";
    final String body = "This is a test./nThis is only a test.";
    final Email testMessage = new Email(recipient, subject, body);


    @Test
    public void emailTest(){
        assertEquals (recipient,testMessage.getRecipients().get(0));
        assertEquals(subject,testMessage.getSubject());
        assertEquals(body, testMessage.getBody());
        assertEquals(false,testMessage.getRead());
    }

    @Test
    public void  emailReadTest() throws MessagingException {
        testMessage.setRead(true);
        assertEquals(true,testMessage.getRead());
    }
}