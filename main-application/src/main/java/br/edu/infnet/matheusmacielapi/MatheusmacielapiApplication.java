package br.edu.infnet.matheusmacielapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MatheusmacielapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatheusmacielapiApplication.class, args);
	}

}
