package com.druid;

import com.druid.dao.PersonRepository;
import com.druid.support.CustomRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@SpringBootApplication
public class Application {
	@Autowired
	PersonRepository personRepository;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
