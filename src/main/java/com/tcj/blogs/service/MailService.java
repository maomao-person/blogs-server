package com.tcj.blogs.service;

import com.tcj.blogs.utils.ReadApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private final ReadApplicationProperties readConfig;

    public void sendNormalMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(readConfig.getFrom());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
        log.info("mail already send");
    }
}
