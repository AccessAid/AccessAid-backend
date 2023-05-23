package dev.accessaid.AccessAid.Contact.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@PropertySource("classpath:application.properties")
public class EmailText {

    @Value("${EMAIL_SET_TO}")
    private String emailSetToAccessAid;

    @Value("${EMAIL_SUBJECT}")
    private String emailSubject;

    @Value("${EMAIL_TEXT}")
    private String emailText;

}
