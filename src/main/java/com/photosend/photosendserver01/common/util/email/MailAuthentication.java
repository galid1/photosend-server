package com.photosend.photosendserver01.common.util.email;

import org.springframework.beans.factory.annotation.Value;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthentication extends Authenticator {
    private PasswordAuthentication passwordAuthentication;

    public MailAuthentication(String mailId, String mailPw) {
        passwordAuthentication = new PasswordAuthentication(mailId, mailPw);
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return passwordAuthentication;
    }
}