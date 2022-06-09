package org.abhisek.rewardSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EntityScan("org.abhisek.rewardSystem.dao")
@EnableJdbcRepositories("org.abhisek.rewardSystem.repository")
@EnableAutoConfiguration
public class RewardSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardSystemApplication.class, args);
	}

}
