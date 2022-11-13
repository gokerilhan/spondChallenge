/**
 * 
 */
package com.spond.challenge.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spond.challenge.dao.AsteroidStatsDAO;
import com.spond.challenge.dao.LargestAsteroidDAO;
import com.spond.challenge.dto.Asteroid;
import com.spond.challenge.dto.AsteroidByDates;
import com.spond.challenge.repository.AsteroidStatsRepository;
import com.spond.challenge.repository.LargestAsteroidRepository;
import com.spond.challenge.service.MainService;
import com.spond.challenge.utils.AsteroidsDeserializer;



/**
 * @author gokerilhan
 *
 */
@Service
public class MainServiceImpl implements MainService{

	private LargestAsteroidRepository largestAsteroidRepository;
	
	private AsteroidStatsRepository asteroidStatsRepository;
	
	@Value("${nasa.api.uri.date.feed}")
	private String feedUri;
	
	@Value("${nasa.api.key}")
	private String key;
	
   private  RestTemplate restTemplate = new RestTemplate();
	
	
	private  AsteroidByDates asteroidByDates=null;;
	
	
	private Gson gsonByDates = new GsonBuilder()
            .registerTypeAdapter(AsteroidByDates.class, new AsteroidsDeserializer())
            .create();
 
	
	
	@Autowired
	public MainServiceImpl(LargestAsteroidRepository largestAsteroidRepository,AsteroidStatsRepository asteroidStatsRepository){
		this.largestAsteroidRepository=largestAsteroidRepository;
		this.asteroidStatsRepository=asteroidStatsRepository;
	}
	
	public List<Asteroid> findStats(String startDateStr, String endDateStr ) throws ParseException, IOException {
		Date startDate=new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
		Date endDate=new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
		//Checking if searched values are already cached in mongo...
		Optional<AsteroidStatsDAO> top10List= asteroidStatsRepository.findByStartDateAndEndDate(startDate, endDate);
		if(top10List.isPresent()) {
			 AsteroidStatsDAO result=top10List.get();
			 //found in mongo!
			 return result.getAsteroidList();
		}
			 
		LocalDate startLocalDate=startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endLocalDate=endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 
			
		LocalDate weekLater = startLocalDate.plusDays(7);
       
       
       if (weekLater.isAfter(endLocalDate) || weekLater.isEqual(endLocalDate)){
    	//day difference is less then 7
       	asteroidByDates=getFromNASA(startDateStr, endDateStr);
       }else {
       	//day difference is more then 7, so will check NASA for every 7 days between start end end dates!!!
       	checkForDates(startLocalDate, endLocalDate, weekLater);
       		 
       		 
        }
		
		Map<String, ArrayList<Asteroid>> mapOfAsteroids=asteroidByDates.getMapOfAsteroids();
		TreeMap<Double,Asteroid> top10ByMissedDistanceTree=new TreeMap<Double,Asteroid>(Collections.reverseOrder());
	
		//building a tree map for finding the top 10 results!
		for(String key:mapOfAsteroids.keySet()) {
			for(Asteroid asteroid:mapOfAsteroids.get(key)) {
				if(top10ByMissedDistanceTree.size()>=10) {
					top10ByMissedDistanceTree.remove(top10ByMissedDistanceTree.firstKey());
				}
				top10ByMissedDistanceTree.put(asteroid.getFirstCloseApproachMissDistanceKM(), asteroid);
			}
		}
		AsteroidStatsDAO asteroidStatsDAO=new AsteroidStatsDAO(null,startDate, endDate, top10ByMissedDistanceTree.values().stream().collect(Collectors.toList()) );
		asteroidStatsRepository.save(asteroidStatsDAO);
		
		return asteroidStatsDAO.getAsteroidList();
		
	}

	private void checkForDates(LocalDate startLocalDate, LocalDate endLocalDate, LocalDate weekLater) {
		LocalDate tempStartDate=startLocalDate;
       	LocalDate tempEndDate=weekLater;        	
       	
       	
       	 while(tempEndDate.isBefore(endLocalDate) && !tempEndDate.isEqual(endLocalDate)) {
       		 
       		 
       		 AsteroidByDates temp = getFromNASA(tempStartDate.toString(), tempEndDate.toString());
       		 if(asteroidByDates==null)
       			 asteroidByDates=temp;
       		 else
       			 asteroidByDates.join(temp);
       		 tempStartDate=tempEndDate.plusDays(1);
       		 tempEndDate= tempEndDate.plusDays(7);
       		 if (tempEndDate.isAfter(endLocalDate) || tempEndDate.isEqual(endLocalDate)){
       			 tempEndDate=endLocalDate;
       			 temp = getFromNASA(tempStartDate.toString(), endLocalDate.toString());
       			 asteroidByDates.join(temp);
       		 }
       		 
       	 }
	}
	
	public Asteroid findLargest(int year) throws ParseException, IOException {
		String startDateStr=year+"-01-01";
		String endDateStr=year+"-12-31";
		 Date startDate=new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
		 Date endDate=new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
		 
		 Optional<LargestAsteroidDAO> resultObj=largestAsteroidRepository.findByYear(year);
		 //Checking if searched values are already cached in mongo...
		 if(resultObj.isPresent()) {
			 //found in mongo!
			 LargestAsteroidDAO result=resultObj.get();
			 return result.getAsteroid();
		 }
		 
		 LocalDate startLocalDate=startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 LocalDate endLocalDate=endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 
		
       LocalDate weekLater = startLocalDate.plusDays(7);
       
       
       if (weekLater.isAfter(endLocalDate) || weekLater.isEqual(endLocalDate)){
       	asteroidByDates=getFromNASA(startDateStr, endDateStr);
       }else {
       	checkForDates(startLocalDate, endLocalDate, weekLater);
        }
       //Checking for largest diameter
       Map<String, ArrayList<Asteroid>> mapOfAsteroids=asteroidByDates.getMapOfAsteroids();
       double maxDiameter=0;
       LargestAsteroidDAO lam=new LargestAsteroidDAO(null, year, null);
       for(String key:mapOfAsteroids.keySet()) {
	    	for(Asteroid asteroid:mapOfAsteroids.get(key)) {
	    		double diameter=asteroid.getEstimatedDiameter().getAvgDiameterInMeters();
	    		if(diameter>maxDiameter) {
	    			maxDiameter=diameter;
	    			lam.setAsteroid(asteroid);	    		}
	    	}
	    }
	    
	    largestAsteroidRepository.save(lam);
		 return lam.getAsteroid();
		
	}
	
	
	
	
	private AsteroidByDates getFromNASA(String startDateStr, String endDateStr) {
		String uri=feedUri + "?start_date=" + startDateStr + "&end_date=" + endDateStr + "&api_key=" + key;
	     //checking from NASA
		 String resultJson = restTemplate.getForObject(uri, String.class);
		 AsteroidByDates asteroidByDates = gsonByDates.fromJson(resultJson, AsteroidByDates.class);
		return asteroidByDates;
	}
	
	
	
	
	
}
