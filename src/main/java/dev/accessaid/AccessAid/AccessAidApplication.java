package dev.accessaid.AccessAid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AccessAidApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccessAidApplication.class, args);
	}

}
