package lk.ijse.helloshoebackend.service.impl;

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

        String htmlContent = ""; // Generate HTML content based on buttonUrl and userName

        sendHtmlEmail(emailDTO, htmlContent);
        logger.info("Birthday email sent to: {}", emailDTO.getRecipient());
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