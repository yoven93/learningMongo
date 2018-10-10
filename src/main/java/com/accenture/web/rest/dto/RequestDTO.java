package com.accenture.web.rest.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDTO {
	
	private String userEmail;
	private String username;
	private String companyName;
	private String projectName;
	private String businessUnit;
	private List<AttributeDateRequestDTO> attributeDateRequestDTOs;
}
