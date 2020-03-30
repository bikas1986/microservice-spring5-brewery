package com.bikas.brewery.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bikas.brewery.services.BeerService;
import com.bikas.brewery.web.model.BeerDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {
	
	@MockBean
	BeerService beerService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private BeerDto beerDto;
	
	@BeforeEach
	void setUp() throws Exception {
		beerDto = BeerDto.builder().id(UUID.randomUUID())
					.beerName("Johny Walker")
					.beerStyle("PALE_ALE")
					.upc(123456789012l)
					.build();
	}

	@Test
	void testGetBeer() throws Exception {
		given(beerService.getBeerById(any(UUID.class))).willReturn(beerDto);
		
		mockMvc.perform(get(BeerController.BASE_URL+"/"+beerDto.getId().toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(beerDto.getId().toString())))
				.andExpect(jsonPath("$.beerName", is("Johny Walker")));
	}

	@Test
	void testHandlePost() throws Exception {
		BeerDto givenBeerDto = beerDto;
		givenBeerDto.setId(null);
		BeerDto savedDto = BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
		String beerDtoJson = objectMapper.writeValueAsString(givenBeerDto);
		
		given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(savedDto);
		
		mockMvc.perform(post(BeerController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isCreated());
	}

	@Test
	void testHandleUpdate() throws Exception {
		BeerDto givenBeerDto = beerDto;
		givenBeerDto.setId(null);
		String beerDtoJson = objectMapper.writeValueAsString(givenBeerDto);
		
		mockMvc.perform(put(BeerController.BASE_URL+"/"+UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isNoContent());
		
		then(beerService).should().updateBeer(any(), any());
	}
	
	

}
