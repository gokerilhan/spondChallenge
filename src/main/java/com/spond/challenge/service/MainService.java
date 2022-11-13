/**
 * 
 */
package com.spond.challenge.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.spond.challenge.dto.Asteroid;

/**
 * @author gokerilhan
 *
 */
public interface MainService {
	public List<Asteroid> findStats(String startDateStr, String endDateStr ) throws ParseException, IOException;
	public Asteroid findLargest(int year) throws ParseException, IOException;

}
