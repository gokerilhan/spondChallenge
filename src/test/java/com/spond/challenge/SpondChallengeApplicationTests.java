package com.spond.challenge;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spond.challenge.controller.MainController;
import com.spond.challenge.dto.Asteroid;
import com.spond.challenge.dto.CloseApproachDatum;
import com.spond.challenge.dto.EstimatedDiameter;
import com.spond.challenge.dto.Links;
import com.spond.challenge.dto.OrbitalData;
import com.spond.challenge.service.MainService;
import com.spond.challenge.service.impl.MainServiceImpl;

/**
 * @author gokerilhan
 *
 */
@WebMvcTest(MainController.class)
@ActiveProfiles("test")
class SpondChallengeApplicationTests {
	static {
        System.setProperty("spring.mongodb.embedded.version","5.0.0");
    }
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	MainServiceImpl mainService;
	
	@Test
	public void get_rangeOfDates_returnsOkWithListOfAsteroids() throws Exception {

		
		 
		  MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/getStats?startDate=2022-10-16&endDate=2022-10-17");
		  Links links=new Links();
		  EstimatedDiameter ed=new EstimatedDiameter();
		  
		 Asteroid asteroid1=new Asteroid(links,"3733133","3733133","(2015 VW)","","http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3733133",20.9, new EstimatedDiameter(),Boolean.FALSE,new ArrayList<CloseApproachDatum>(),new OrbitalData(),Boolean.FALSE);
		  List<Asteroid> asteroidList=new ArrayList<Asteroid>();
		  asteroidList.add(asteroid1);
		  Mockito.when(mainService.findStats("2022-10-16", "2022-12-17")).thenReturn(asteroidList);
		 mockMvc.perform(builder).andExpect(status().isOk());
		
		
		
	}

	

}

	

	
