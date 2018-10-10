package com.accenture.web.rest.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttributeDateRequestDTO {
	
	private LocalDate date;
	private String attribute;
}
