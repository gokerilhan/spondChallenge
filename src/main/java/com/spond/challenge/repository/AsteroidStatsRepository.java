package com.spond.challenge.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spond.challenge.dao.AsteroidStatsDAO;


@Repository
public interface AsteroidStatsRepository extends MongoRepository<AsteroidStatsDAO, String> {
	
	 
	Optional<AsteroidStatsDAO> findByStartDateAndEndDate(Date startDate,Date endDate);
}
