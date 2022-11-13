/**
 * 
 */
package com.spond.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spond.challenge.service.MainService;


/**
 * @author gokerilhan
 *
 */
@RestController
public class MainController {
	
	private MainService mainService;
	
	@Autowired
	public MainController( MainService mainService) {
		this.mainService=mainService;
		
	}
	
	@GetMapping("/getStats")
	public ResponseEntity<?> getAsteroids(@RequestParam String startDate,@RequestParam String endDate) {
	    try {
	    	 
	        return new ResponseEntity<>(mainService.findStats(startDate, endDate), HttpStatus.OK);
	    }catch (Exception e){
	        e.printStackTrace();
	        return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@GetMapping("/getLargest")
	public ResponseEntity<?> getAsteroids(@RequestParam int year) {
	    try {
	    	
	        return new ResponseEntity<>(mainService.findLargest(year), HttpStatus.OK);
	    }catch (Exception e){
	        e.printStackTrace();
	        return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
