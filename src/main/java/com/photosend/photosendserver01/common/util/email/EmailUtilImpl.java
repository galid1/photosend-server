package com.photosend.photosendserver01.common.util.email;

import com.photosend.photosendserver01.common.util.file.KeyValueFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
@Primary
public class EmailUtilImpl implements EmailUtil {
    @Autowired
    private KeyValueFileLoader keyValueFileLoader;
    @Value("${photosend.credential.mail.file-path}")
    private String mailCredentialFilePath;
    private final String MAIL_ID_KEY = "mail_id";
    private final String MAIL_PW_KEY = "mail_pw";

    private List<String> receiveEmailList = Arrays.asList("peoplusapply@gmail.com", "oyun0221@naver.com");

    @Override
    public void sendEmailWithImage(byte[] imageFileBytes) {
        Session session = Session.getDefaultInstance(getSessionProperties(), getAuthenticator());

        MimeMessage msg = new MimeMessage(session);
        configureMsg(imageFileBytes, msg);

        receiveEmailList.forEach(receiveEmail -> {
            addRecipient(msg, receiveEmail);
        });

        try {
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Authenticator getAuthenticator() {
        String mailId = keyValueFileLoader.getValueFromFile(mailCredentialFilePath, MAIL_ID_KEY);
        String mailPw = keyValueFileLoader.getValueFromFile(mailCredentialFilePath, MAIL_PW_KEY);
        return new MailAuthentication(mailId, mailPw);
    }

    private Properties getSessionProperties() {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        return prop;
    }

    private void addRecipient(MimeMessage msg, String receiveEmail) {
        try {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmail));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void configureMsg(byte[] imageFileBytes, MimeMessage msg) {
        try {

            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress("peoplusapply@gmail.com", "VISITOR"));
            msg.setText("https://rest.phsend.com/admin/products", "UTF-8");

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource fds = new ByteArrayDataSource(imageFileBytes, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);
        }

        catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
