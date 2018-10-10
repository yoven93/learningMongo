package com.accenture.web.rest.handler;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.accenture.domain.Company;
import com.accenture.domain.User;
import com.accenture.domain.WorkHour;
import com.accenture.repository.CompanyRepository;
import com.accenture.repository.WorkHourRepository;
import com.accenture.service.CompanyService;
import com.accenture.web.rest.dto.AttributeDateRequestDTO;
import com.accenture.web.rest.dto.RequestDTO;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class CompanyHandler {
	
	private final CompanyService companyService;
	private final CompanyRepository companyRepository;
	private final WorkHourRepository workHourRepository;
	
	/**
	 * Find all companies.
	 */
	public Mono<ServerResponse> findAllCompanies(ServerRequest serverRequest) {

		RequestDTO obj  = new RequestDTO();
		
		companyService.findAllCompanies()
			.flatMap(company -> {
				System.out.println("Company: " + company.getCompanyName());
				obj.setCompanyName(company.getCompanyName());
				return Flux.fromIterable(company.getBusinesses());
			})
			.flatMap(business -> {
				System.out.println("Business: " + business.getBusinessName());
				obj.setBusinessUnit(business.getBusinessName());
				return Flux.fromIterable(business.getProjects());
			})
			.flatMap(project -> {
				System.out.println("Project: " + project.getProjectName());
				obj.setProjectName(project.getProjectName());
				return Flux.fromIterable(project.getUsers());
			})
			.flatMap(user -> {
				System.out.println("User: " + user.getEmail());
				obj.setUsername(user.getUsername());
				obj.setUserEmail(user.getEmail());
				return Mono.just(user);
			});

		
		return ServerResponse.ok().body(null ,RequestDTO.class);
		
		
//		RequestDTO requestDTO = new RequestDTO();
//		requestDTO.setUserEmail(obj.getUserEmail());
//		requestDTO.setCompanyName(obj.getCompanyName());
//		requestDTO.setUsername(obj.getUsername());
//		requestDTO.setProjectName(obj.getProjectName());
//		requestDTO.setBusinessUnit(obj.getBusinessUnit());
//		requestDTO.setAttributeDateRequestDTOs(obj.getAttributeDateRequestDTOs());
//		return Mono.just(requestDTO);
		
		
//		return workHourRepository.findByUserEmailAndDateBetweenAndProjectName("y@y.com", LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 31), "P1")
//				.flatMap(workHour -> {
//					System.out.println("WorkHour: " + workHour);
//					RequestDTO requestDTO = new RequestDTO();
//					requestDTO.setUserEmail(obj.getUserEmail());
//					requestDTO.setCompanyName(obj.getCompanyName());
//					requestDTO.setUsername(obj.getUsername());
//					requestDTO.setProjectName(obj.getProjectName());
//					requestDTO.setBusinessUnit(obj.getBusinessUnit());
//					return Mono.just(requestDTO);
//				});
	}
	
	/**
	 * Find all companies by business 
	 * @param serverRequest
	 * @return
	 */
//	public Mono<ServerResponse> findAllCompaniesByBusinessResponsableEmail(ServerRequest serverRequest) {
//		
//		// Get user email
//		String email = serverRequest.pathVariable("email");
//		System.out.println(email);
//		
//		return ServerResponse.ok().body(companyService.findAllCompaniesByBusinessResponsableEmail(email), Company.class);
//	}
	
	/**
	 * Find Company by project name
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> findCompanyByProjectName(ServerRequest serverRequest) {
		
		// Get project name
		String projectName = serverRequest.pathVariable("projectName");
		
		return companyService.findCompanyByProjectName(projectName)
				.flatMap(company -> ServerResponse.ok().body(Mono.just(company), Company.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	/**
	 * Find All businesses
	 */
	public Mono<ServerResponse> getAllBusinesses(ServerRequest serverRequest) {
		
//		return ServerResponse.ok().body(
//				companyRepository.findAllBusinesses()
//				.flatMap(company -> Flux.fromIterable(company.getBusinesses()))
//				,Business.class);
		
//		return companyRepository.findAllBusinesses()
//				.flatMap(company -> ServerResponse.ok().body(Flux.fromIterable(company.getBusinesses()), Business.class));
		
		
		return companyRepository.findAllBusinesses()
				.flatMap(company -> ServerResponse.ok().body(Mono.just(company), Company.class));
	}
}
