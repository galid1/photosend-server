package com.photosend.photosendserver01.common.util.email;

import com.photosend.photosendserver01.common.util.file.KeyValueFileLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
@Primary
public class EmailUtilImpl implements EmailUtil {
    private final KeyValueFileLoader keyValueFileLoader;
    @Value("${photosend.credential.mail.file-path}")
    private String mailCredentialFilePath;
    private final String MAIL_ID_KEY = "mail_id";
    private final String MAIL_PW_KEY = "mail_pw";

    private Address[] recipientList;

    private String IMAGE_UPLOADED_SUBJECT = "상품 이미지 업로드 됨.";
    private String ORDERED_SUBJECT = "사용자 주문이 확인됨.";

    private String IMAGE_UPLOADED_BODY = "사용자가 이미지를 업로드했습니다.";
    private String ORDERED_BODY = "사용자가 주문을 완료했습니다.";

    private String IMAGE_UPLOADED_LIST_URL = "https://rest.phsend.com/admin/products";
    private String ORDERED_LIST_URL = "https://rest.phsend.com/admin/orders";

    public EmailUtilImpl(KeyValueFileLoader keyValueFileLoader) {
        this.keyValueFileLoader = keyValueFileLoader;
        initRecipients();
    }

    private void initRecipients() {
        try {
            this.recipientList = new Address[]{
                    new InternetAddress("peoplusapply@gmail.com"),
                    new InternetAddress("oyun0221@naver.com")};
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(EmailType type, long userId, List<byte[]> imageFileByteArrList) {
        Session session = Session.getDefaultInstance(getSessionProperties(), getAuthenticator());

        MimeMessage msg = new MimeMessage(session);
        try {
            configureSenderInformation(msg);
            configureMessageSubject(msg, type);
            configureMessageBody(msg, type, userId, imageFileByteArrList);
            msg.addRecipients(Message.RecipientType.TO, recipientList);
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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

    private void configureSenderInformation(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        msg.setSentDate(new Date());
        msg.setFrom(new InternetAddress("peoplusapply@gmail.com", "VISITOR"));
    }

    private void configureMessageSubject(MimeMessage msg, EmailType type) throws MessagingException {
        switch (type) {
            case IMAGE_UPLOADED:
                msg.setSubject(IMAGE_UPLOADED_SUBJECT);
                break;

            case ORDERED:
                msg.setSubject(ORDERED_SUBJECT);
                break;

            default:
                break;
        }
    }

    private void configureMessageBody(MimeMessage msg, EmailType type, long userId, List<byte[]> imageFileByteArrList) throws MessagingException {
        MimeMultipart multipart = new MimeMultipart("related");

        configureText(multipart, type, userId);
        if(imageFileByteArrList != null && imageFileByteArrList.size() > 0) {
            configureImage(multipart, imageFileByteArrList);
        }

        msg.setContent(multipart);
    }

    private void configureImage(MimeMultipart multipart, List<byte[]> imageFileByteArrList) {
        imageFileByteArrList.stream()
            .forEach(imageFileByteArr -> {
                try {
                    BodyPart imageBodyPart = new MimeBodyPart();
                    imageBodyPart.setHeader("Content-ID", "<image>");
                    imageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(imageFileByteArr, "image/png")));
                    multipart.addBodyPart(imageBodyPart);
                }
                 catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
    }

    private void configureText(MimeMultipart multipart, EmailType type, long userId) throws MessagingException {
        String messageBody = userId + " ";
        switch (type) {
            case IMAGE_UPLOADED:
                messageBody += IMAGE_UPLOADED_BODY + "\n" + IMAGE_UPLOADED_LIST_URL + "#" + userId;
                break;

            case ORDERED:
                messageBody += ORDERED_BODY + "\n" + ORDERED_LIST_URL + "#" + userId;
                break;

            default:
                break;
        }

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(messageBody);

        multipart.addBodyPart(messageBodyPart);
    }
}
