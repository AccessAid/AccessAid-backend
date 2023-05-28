package dev.accessaid.AccessAid.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${EMAIL_USERNAME}")
    String username;

    @Value("${EMAIL_PASSWORD}")
    String password;

    @Value("${EMAIL_HOST}")
    String host;

    @Value("${EMAIL_PORT}")
    int port;

    @Value("${EMAIL_SMTP_STARTTLS}")
    String startTls;

    @Bean
    public JavaMailSender javaMailSender() throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", startTls);
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.ssl.checkserveridentity", "true");

        return mailSender;
    }

}
