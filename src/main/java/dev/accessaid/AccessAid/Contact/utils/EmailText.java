package dev.accessaid.AccessAid.Contact.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@PropertySource("classpath:application.properties")
public class EmailText {

    @Value("${spring.mail.setTo}")
    private String emailSetToAccessAid;

    @Value("${spring.mail.subject}")
    private String emailSubject;

    @Value("${spring.mail.text}")
    private String emailText;

}
