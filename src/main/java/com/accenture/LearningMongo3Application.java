package com.accenture;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.accenture.domain.Business;
import com.accenture.domain.Company;
import com.accenture.domain.Project;
import com.accenture.domain.User;
import com.accenture.domain.WorkHour;
import com.accenture.repository.CompanyRepository;
import com.accenture.repository.UserRepository;
import com.accenture.repository.WorkHourRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class LearningMongo3Application {

	public static void main(String[] args) {
		SpringApplication.run(LearningMongo3Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(CompanyRepository companyRepository,
			UserRepository userRepository, WorkHourRepository workHourRepository) {
		return args -> {
			
			// Create flux of users
			User user1 = new User("y@y.com", "yoven", "1234");
			User user2 = new User("h@h.com", "hema", "1234");
			User user3 = new User("j@j.com", "jed", "1234");
			User user4 = new User("l@l.com", "laurent", "1234");
			User user5 = new User("m@m.com", "manu", "1234");
			User user6 = new User("p@p.com", "prashant", "1234");
			User user7 = new User("e@e.com", "evans", "1234");
			User user8 = new User("b@b.com", "brandon", "1234");
			Flux<User> userFlux = Flux.just(user1, user2, user3, user4, user5, user6, user7, user8);
			
			
			// Create Projects
			Project project1 = new Project("P1", new ArrayList<>(Arrays.asList(user1, user2)));
			Project project2 = new Project("P2", new ArrayList<>(Arrays.asList(user3, user4)));
			Project project3 = new Project("P3", new ArrayList<>(Arrays.asList(user5, user6)));
			
			
			// Create businesses
			Business business1 = new Business("B1", "Port-Louis", new ArrayList<>(Arrays.asList(project1 , project2)), user7);
			Business business2 = new Business("B2", "Rose-Hill", new ArrayList<>(Arrays.asList(project3)), user8);
			List<Business> businesses = new ArrayList<>(Arrays.asList(business1, business2));
		
			
			// Create company
			Company company1 = new Company(null, "C1", businesses, "w@w.com");
			Company company2 = new Company(null, "C2", businesses, "z@z.com");
			
			
			WorkHour workHour1 = new WorkHour();
			workHour1.setUser(user2);
			workHour1.setProjectName("P1");
			workHour1.setAttribute("1.0");
			workHour1.setDate(LocalDate.of(2018, 10, 10));
			
			WorkHour workHour2 = new WorkHour();
			workHour2.setUser(user2);
			workHour2.setProjectName("P1");
			workHour2.setAttribute("2.0");
			workHour2.setDate(LocalDate.of(2018, 10, 11));
			
			WorkHour workHour3 = new WorkHour();
			workHour3.setUser(user3);
			workHour3.setProjectName("P2");
			workHour3.setAttribute("5.0");
			workHour3.setDate(LocalDate.of(2018, 10, 11));
			
			WorkHour workHour4 = new WorkHour();
			workHour4.setUser(user5);
			workHour4.setProjectName("P3");
			workHour4.setAttribute("10.0");
			workHour4.setDate(LocalDate.of(2018, 10, 11));
			
			WorkHour workHour5 = new WorkHour();
			workHour5.setUser(user5);
			workHour5.setProjectName("P3");
			workHour5.setAttribute("15.0");
			workHour5.setDate(LocalDate.of(2018, 10, 11));
			
			// Clear database and then insert users and companies
			companyRepository.deleteAll()
				.then(userRepository.deleteAll())
				.then(workHourRepository.deleteAll())
				.thenMany(userRepository.saveAll(userFlux))
				.then(companyRepository.save(company1))
				.then(workHourRepository.save(workHour1))
				.then(workHourRepository.save(workHour2))
				.then(workHourRepository.save(workHour3))
				.then(workHourRepository.save(workHour4))
				.then(workHourRepository.save(workHour5))
				.subscribe();	
		};
	}
}
