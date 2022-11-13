/**
 * 
 */
package com.spond.challenge.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spond.challenge.dto.Asteroid;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author gokerilhan
 *
 */
@Document("largest_asteroid")
@Data
@AllArgsConstructor
public class LargestAsteroidDAO {

	 @Id
     private String id;
	 
	 private int year;
	 
	private Asteroid asteroid;

}
