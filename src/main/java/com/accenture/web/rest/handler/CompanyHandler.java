package com.accenture.web.rest.handler;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.accenture.domain.Company;
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
		
		return ServerResponse.ok().body(generateRequestDTO(), RequestDTO.class);
	}


	private Flux<RequestDTO> generateRequestDTO() {
		RequestDTO obj = new RequestDTO();

		return this.companyService.findAllCompanies()
		.flatMap(company -> {
			obj.setCompanyName(company.getCompanyName());
			return Flux.fromIterable(company.getBusinesses());
		})
		.flatMap(business -> {
			obj.setBusinessUnit(business.getBusinessName());
			return Flux.fromIterable(business.getProjects());
		})
		.concatMap(project -> {
			obj.setProjectName(project.getProjectName());
			return Flux.fromIterable(project.getUsers())
				.flatMap(user -> {
					RequestDTO requestDTO = new RequestDTO();
					requestDTO.setUsername(user.getUsername());
					requestDTO.setUserEmail(user.getEmail());
					requestDTO.setBusinessUnit(obj.getBusinessUnit());
					requestDTO.setCompanyName(obj.getCompanyName());
					requestDTO.setProjectName(obj.getProjectName());
					requestDTO.setAttributeDateRequestDTOs(new ArrayList<>());
					return Mono.just(requestDTO);
				});
		})
		.flatMap(requestDTO -> {
			
			return this.workHourRepository.findByUserEmailAndDateBetweenAndProjectName(requestDTO.getUserEmail(),LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 31), requestDTO.getProjectName())
					.flatMap(workHour -> {
						requestDTO.getAttributeDateRequestDTOs().add(new AttributeDateRequestDTO(workHour.getDate(), workHour.getAttribute()));
						return Mono.just(workHour);
					})
					.then(Mono.just(requestDTO));
		});
	}

	/**
	 * Find all companies by business
	 * 
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
	 * 
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
