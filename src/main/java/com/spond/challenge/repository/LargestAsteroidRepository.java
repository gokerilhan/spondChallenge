/**
 * 
 */
package com.spond.challenge.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spond.challenge.dao.LargestAsteroidDAO;

/**
 * @author gokerilhan
 *
 */
@Repository
public interface LargestAsteroidRepository extends MongoRepository<LargestAsteroidDAO, String> {
    
	
	Optional<LargestAsteroidDAO> findByYear(Integer year);
	
 

}
