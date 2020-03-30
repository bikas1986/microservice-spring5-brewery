package com.bikas.brewery.web.controller.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bikas.brewery.services.v2.BeerServiceV2;
import com.bikas.brewery.web.model.v2.BeerDtoV2;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping(BeerControllerV2.BASE_URL)
public class BeerControllerV2 {
	public static final String BASE_URL = "/api/v2/beer";
	private final BeerServiceV2 beerServiceV2;
	public BeerControllerV2(BeerServiceV2 beerServiceV2) {
		super();
		this.beerServiceV2 = beerServiceV2;
	}
	
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDtoV2> getBeer(@PathVariable UUID beerId){
		return new ResponseEntity<>(this.beerServiceV2.getBeerById(beerId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity handlePost(@Valid @RequestBody BeerDtoV2 beerDto) {
		BeerDtoV2 savedBeerDto = this.beerServiceV2.saveNewBeer(beerDto);
		
		HttpHeaders headers = new HttpHeaders();
		//todo add hostname to url
		headers.add("Location", BeerControllerV2.BASE_URL+"/"+savedBeerDto.getId().toString());
		
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{beerId}")
	public ResponseEntity handleUpdate(@PathVariable UUID beerId, @Valid @RequestBody BeerDtoV2 beerDto) {
		beerServiceV2.updateBeer(beerId, beerDto);
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{beerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBeer(@PathVariable UUID beerId) {
		beerServiceV2.deleteById(beerId);
	}


}
