package com.photosend.photosendserver01.common.util.email;

import com.photosend.photosendserver01.common.util.file.KeyValueFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;
import java.util.Properties;

@Component
@Primary
public class EmailUtilImpl implements EmailUtil{
    private final String IMAGE_UPLOAD_TITLE = "가 이미지를 업로드 하였습니다.";
    private final String ORDER_TITLE = "가 상품을 주문하였습니다.";

    @Autowired
    private KeyValueFileLoader keyValueFileLoader;
    @Value("${photosend.credential.mail.file-path}")
    private String mailCredentialFilePath;
    private final String MAIL_ID_KEY = "mail_id";
    private final String MAIL_PW_KEY = "mail_pw";

    @Override
    public void sendEmailWithImage(String userName, byte[] imageFileBytes) {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");

        String mailId = keyValueFileLoader.getValueFromFile(mailCredentialFilePath, MAIL_ID_KEY);
        String mailPw = keyValueFileLoader.getValueFromFile(mailCredentialFilePath, MAIL_PW_KEY);
        Authenticator auth = new MailAuthentication(mailId, mailPw);

        Session session = Session.getDefaultInstance(prop, auth);

        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setSentDate(new Date());

            msg.setFrom(new InternetAddress("galid9619@gmail.com", "VISITOR"));
            InternetAddress to = new InternetAddress("peoplusapply@gmail.com");
            msg.setRecipient(Message.RecipientType.TO, to);
            msg.setSubject(userName + IMAGE_UPLOAD_TITLE, "UTF-8");
            msg.setText("", "UTF-8");

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource fds = new ByteArrayDataSource(imageFileBytes, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
