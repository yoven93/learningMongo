package com.accenture.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.accenture.domain.Business;
import com.accenture.domain.Company;
import com.accenture.domain.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanyRepository extends ReactiveMongoRepository<Company, ObjectId> {
	
	/**
	 * Find company by business responsable email
	 */
	//Flux<Company> findByBusinessesResponsableEmail(String email);
	
	/**
	 * Find company by project name
	 */
	Mono<Company> findByBusinessesProjectsProjectName(String projectName);
	
	/**
	 * Find all businesses
	 */
	@Query(value = "{}", fields = "{'businesses' :\r\n" + 
			"          {\r\n" + 
			"              $elemMatch:\r\n" + 
			"              { \r\n" + 
			"                    projects:\r\n" + 
			"                    {\r\n" + 
			"                        $elemMatch:\r\n" + 
			"                        {\r\n" + 
			"                            projectName: 'P1'\r\n" + 
			"                        }\r\n" + 
			"                    }\r\n" + 
			"              }\r\n" + 
			"          },\r\n" + 
			"          \r\n" + 
			"          'businesses.projects': 1\r\n" + 
			"    }")
	public Mono<Company> findAllBusinesses();
	
}
