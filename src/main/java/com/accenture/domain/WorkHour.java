package com.accenture.domain;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "work_hours")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkHour {
	
	@Id
	private ObjectId id;
	
	private String attribute;
	
	private LocalDate date;
	
	@DBRef
	private User user;
	
	private String projectName;
}
