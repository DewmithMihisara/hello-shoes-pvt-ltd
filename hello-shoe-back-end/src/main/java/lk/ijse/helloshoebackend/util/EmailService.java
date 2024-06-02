package lk.ijse.helloshoebackend.util;

import jakarta.mail.internet.MimeMessage;
import lk.ijse.helloshoebackend.dto.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */

@Service
@Transactional
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void bdayMail(EmailDTO emailDTO, String buttonUrl, String userName) {
        logger.info("Preparing birthday email for recipient: {}", emailDTO.getRecipient());

        String htmlContent = "";

        sendHtmlEmail(emailDTO, htmlContent);
        logger.info("Birthday email sent to: {}", emailDTO.getRecipient());
    }

    public void pw(EmailDTO emailDTO) {

        String htmlContent = "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 0;\">\n" +
                "<div style=\"width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\">\n" +
                "  <div style=\"text-align: center; padding: 10px 0; background-color: #E91E63; color: #fff;\">\n" +
                "    <h1>Hello Shoe</h1>\n" +
                "  </div>\n" +
                "  <div style=\"padding: 20px; line-height: 1.6;\">\n" +
                "    <h2>Password Reset Request</h2>\n" +
                "    <p>Dear,</p>\n" +
                "    <p>We have reset your password as per your request. Here is your new password:</p>\n" +
                "    <p style=\"font-size: 18px; font-weight: bold; color: #E91E63;\">"+emailDTO.getMsgBody()+"</p>\n" +
                "    <p>Please use this new password to log in to your Hello Shoe account. We recommend that you change your password immediately after logging in to ensure your account remains secure.</p>\n" +
                "    <p>If you did not request this change, please contact our support team immediately.</p>\n" +
                "    <p>Thank you,<br>The Hello Shoe Team</p>\n" +
                "  </div>\n" +
                "  <div style=\"text-align: center; padding: 10px 0; background-color: #f4f4f4; color: #777; font-size: 12px;\">\n" +
                "    &copy; 2024 Hello Shoe. All rights reserved.\n" +
                "  </div>\n" +
                "</div>\n" +
                "</body>";

        sendHtmlEmail(emailDTO, htmlContent);
        logger.info("Birthday email sent to: ", emailDTO.getRecipient());
    }

    private void sendHtmlEmail(EmailDTO emailDTO, String htmlContent) {
        logger.info("Preparing to send HTML email to: {}", emailDTO.getRecipient());
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailDTO.getRecipient());
            helper.setSubject(emailDTO.getSubject());
            helper.setText(htmlContent, true);
            helper.setFrom(sender);
            javaMailSender.send(message);
            logger.info("HTML email sent to: {}", emailDTO.getRecipient());
        } catch (Exception e) {
            logger.error("Error sending HTML email to: {}", emailDTO.getRecipient(), e);
        }
    }

    public Boolean sendSimpleMail(EmailDTO emailDTO) {
        logger.info("Preparing to send simple email to: {}", emailDTO.getRecipient());
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDTO.getRecipient());
            mailMessage.setText(emailDTO.getMsgBody());
            mailMessage.setSubject(emailDTO.getSubject());

            javaMailSender.send(mailMessage);
            logger.info("Simple email sent to: {}", emailDTO.getRecipient());
            return true;
        } catch (Exception e) {
            logger.error("Error sending simple email to: {}", emailDTO.getRecipient(), e);
            return false;
        }
    }
}