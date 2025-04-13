package com.jetbrains.servermanager;

import com.jetbrains.servermanager.enumeration.Status;
import com.jetbrains.servermanager.model.Server;
import com.jetbrains.servermanager.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServerManagerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository){
		return args -> {

			serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16 GB", "Personal PC",
					"http://localhost:8080/image/server1.png", Status.SERVER_UP, "Dell"));

			serverRepository.save(new Server(null, "192.168.1.58", "Fedora Linux", "16 GB", "Dell Tower",
					"http://localhost:8080/server/image/server2.png", Status.SERVER_DOWN, "Dell"));

			serverRepository.save(new Server(null, "192.168.1.21", "MS 2008", "32 GB", "Web Server",
					"http://localhost:8080/server/image/server3.png", Status.SERVER_UP, "MS"));

			serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "64 GB", "Mail Server",
					"http://localhost:8080/server/image/server4.png", Status.SERVER_DOWN, "HP"));
		};
	}
}
