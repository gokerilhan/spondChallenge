/**
 * 
 */
package com.spond.challenge.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spond.challenge.dto.Asteroid;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author gokerilhan
 *
 */
@Document("asteroid_stats")
@Data
@AllArgsConstructor
public class AsteroidStatsDAO {
	
	 @Id
     private String id;
	 
	 private Date startDate;
	 
	 private Date endDate;
	 
	 private List<Asteroid> asteroidList;
	
}
