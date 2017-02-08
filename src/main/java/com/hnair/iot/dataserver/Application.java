package com.hnair.iot.dataserver;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hnair.iot.dataserver.model.TimeUnit;

/**
 * Entry point of IAM application.
 * 
 * @author Bin.Zhang
 */
@SpringBootApplication
@EnableConfigurationProperties(TimeUnit.class)
public class Application {

	/**
	 * Start application
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Banner.Mode.CONSOLE);
		app.run(args);
	}
}
