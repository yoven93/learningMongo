package com.accenture.repository;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.accenture.domain.WorkHour;

import reactor.core.publisher.Flux;

public interface WorkHourRepository extends ReactiveMongoRepository<WorkHour, ObjectId> {

	public Flux<WorkHour> findByUserEmailAndDateBetweenAndProjectName(String email, LocalDate start, LocalDate end, String projectName);
}
