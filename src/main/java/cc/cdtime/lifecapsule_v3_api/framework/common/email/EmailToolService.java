package cc.cdtime.lifecapsule_v3_api.framework.common.email;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
public class EmailToolService implements IEmailToolService {
    /**
     * 发送email
     *
     * @param qIn userEmail
     * @throws Exception
     */
    @Override
    public void sendMail(Map qIn) throws Exception {
        String userEmail = qIn.get("email").toString();
        String subject = qIn.get("subject").toString();

        String mailType = qIn.get("mailType").toString();

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

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));

        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        if (mailType.equals(ESTags.MAIL_TYPE_VALIDATE.toString())) {
            messageBodyPart.setContent(buildContentValidateCode(qIn), "text/html;charset=utf-8");
        } else {
            messageBodyPart.setContent(buildContent(qIn), "text/html;charset=utf-8");
        }

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        Transport.send(msg);
        System.out.println("send mail success：" + userEmail);
    }

    private String buildContent(Map qIn) throws Exception {
        String subject = qIn.get("subject").toString();
        String username = (String) qIn.get("userName");
        String toName = (String) qIn.get("toName");
        String urlLink = qIn.get("urlLink").toString();
        String decode = qIn.get("decode").toString();

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
            log.error("Read mail template error: ", fileName, ex.getMessage());
        } finally {
            inputStream.close();
            fileReader.close();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

        String htmlText = buffer.toString();
        htmlText = htmlText.replaceAll("%toName%", toName);
        htmlText = htmlText.replaceAll("%subject%", subject);
//        htmlText = htmlText.replaceAll("%link%", urlLink);
//        htmlText = htmlText.replaceAll("%decode%", decode);
        if (username != null) {
            htmlText = htmlText.replaceAll("%from%", username);
        } else {
            htmlText = htmlText.replaceAll("%from%", "LifeCapsule User");
        }
        return htmlText;
    }

    private String buildContentValidateCode(Map qIn) throws Exception {
        String code = qIn.get("code").toString();

        String fileName = "classes/MailTmpValidateCode.html";
//        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);

        File file = new File("classes/MailTmpValidateCode.html");
        InputStream inputStream = new FileInputStream(file);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception ex) {
            log.error("Read mail template error: ", fileName, ex.getMessage());
        } finally {
            inputStream.close();
            fileReader.close();
        }
        String htmlText = buffer.toString();
        htmlText = htmlText.replaceAll("%emailcode%", code);
        return htmlText;
    }
}
