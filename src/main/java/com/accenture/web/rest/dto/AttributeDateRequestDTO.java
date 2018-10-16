package com.accenture.web.rest.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDateRequestDTO {
	
	private LocalDate date;
	private String attribute;
}
