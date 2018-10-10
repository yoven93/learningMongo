package com.accenture.domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "companies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
	
	@Id
	private ObjectId id;
	
	@Field
	private String companyName;
	
	@Field
	private List<Business> businesses;
	
	@Field
	private String responsable;
}
