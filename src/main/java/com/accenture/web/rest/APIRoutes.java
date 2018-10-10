package com.accenture.web.rest;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.accenture.web.rest.handler.CompanyHandler;

@Configuration
public class APIRoutes {

	@Bean(name = "companyRoutes")
	public RouterFunction<ServerResponse> companyRouterFunction(final CompanyHandler companyHandler) {
		return nest(path("/api/company"),
				route(GET("/all"), companyHandler::findAllCompanies)
				.andRoute(GET("/project/{projectName}"), companyHandler::findCompanyByProjectName)
				.andRoute(GET("/businesses"), companyHandler::getAllBusinesses));
				//.andRoute(GET("/{email}"), companyHandler::findAllCompaniesByBusinessResponsableEmail));
	}
}
