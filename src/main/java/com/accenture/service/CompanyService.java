package com.accenture.service;

import org.springframework.stereotype.Service;

import com.accenture.domain.Company;
import com.accenture.repository.CompanyRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CompanyService {
	
	private final CompanyRepository companyRepository;
	
	/**
	 * Find all companies
	 */
	public Flux<Company> findAllCompanies() {
		return companyRepository.findAll();
	}
	
	/**
	 * Find all companies by business responsable email
	 */
//	public Flux<Company> findAllCompaniesByBusinessResponsableEmail(String email) {
//		return companyRepository.findByBusinessesResponsableEmail(email);
//	}
	
	/**
	 * Find company by project name
	 */
	public Mono<Company> findCompanyByProjectName(String projectName) {
		return companyRepository.findByBusinessesProjectsProjectName(projectName);
	}

}
