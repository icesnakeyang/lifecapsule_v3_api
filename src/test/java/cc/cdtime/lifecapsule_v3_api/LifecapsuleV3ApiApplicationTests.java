package cc.cdtime.lifecapsule_v3_api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

@SpringBootTest
class LifecapsuleV3ApiApplicationTests {

    @Test
    void contextLoads() {
        // Recipient's email ID needs to be mentioned.
        String to = "flow_ocean@yahoo.com";

        // Sender's email ID needs to be mentioned
        String from = "cdtim117@gmail.com";
//        String from = "goodlife@timecap.com";
//        String username="cdtime117@gmail";
        String username = "info@tellmeafter.com";
//        String password="33455432ccdeedd";
        String password = "+QWtSPX.]?Me";

        // Assuming you are sending email from through gmails smtp
        String host = "mail.tellmeafter.com";

        // Get system properties
        Properties props = new Properties();

        // Setup mail server
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "26");
        props.put("mail.smtp.ssl.enable", "false");


        // Get the Session object.// and pass username and password
//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("oh yeah, oh yeah");


            // Send message
            Transport.send(message);

//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setContent(buildContent(), "text/html;charset=utf-8");
//
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);

//            message.setContent(multipart);
//            message.setContent("<h1>hello</h1><p></p><div>dear sir:</div>", "text/html");

            System.out.println("sending...");

//            Transport.send(message);

            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    @Test
    void sendMail2() {
        String userEmail = "flow_ocean@yahoo.com";
        String subject = "subject";

        String to = userEmail;
        String from = "cdtime117@gmail.com";
        String username = "info@tellmeafter.com";
        String password = "+QWtSPX.]?Me";
        String host = "mail.tellmeafter.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "26");
        props.put("mail.smtp.ssl.enable", "false");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        session.setDebug(true);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);

//            msg.setText("this is the test for send mail from life capsule");
//            Transport.send(msg);

            // message contains HTML markups
//            String message = "<i>Greetings!</i><br>";
//            message += "<b>Wish you a nice day!</b><br>";
//            message += "<font color=red>Duke</font>";
//            msg.setContent(message, "text/html");


            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(buildContent(), "text/html;charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
            System.out.println("send mail success：" + userEmail);
        } catch (Exception ex) {
            System.out.println("Send mail error:" + ex.getMessage());
        }
    }


    private String buildContent() {
        String subject = "subject";
        String username = "userName";
        String toName = "toName";
        String urlLink = "urlLink";
        String decode = "decode";

        String fileName = "MailTmp1.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception ex) {
            System.out.println("Read mail template error: " + fileName + "/" + ex.getMessage());
        } finally {
            try {
                inputStream.close();
                fileReader.close();
            } catch (Exception ex) {
                System.out.println("Error2:" + ex.getMessage());
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

        String htmlText = buffer.toString();
        htmlText = htmlText.replaceAll("%toName%", toName);
        htmlText = htmlText.replaceAll("%subject%", subject);
        htmlText = htmlText.replaceAll("%link%", urlLink);
        htmlText = htmlText.replaceAll("%decode%", decode);
        if (username != null) {
            htmlText = htmlText.replaceAll("%from%", username);
        } else {
            htmlText = htmlText.replaceAll("%from%", "LifeCapsule User");
        }
        return htmlText;
    }

}
