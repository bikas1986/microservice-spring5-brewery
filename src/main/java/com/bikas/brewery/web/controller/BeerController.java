package com.bikas.brewery.web.controller;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bikas.brewery.services.BeerService;
import com.bikas.brewery.web.model.BeerDto;

@Deprecated
@RestController
@RequestMapping(BeerController.BASE_URL)
public class BeerController {
	public static final String BASE_URL = "/api/v1/beer";
	
	private final BeerService beerService;

	public BeerController(BeerService beerService) {
		super();
		this.beerService = beerService;
	}
	
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeer(@PathVariable UUID beerId){
		return new ResponseEntity<>(beerService.getBeerById(beerId),
				HttpStatus.OK);
	}
	
	@PostMapping // POST - create new beer
    public ResponseEntity handlePost(@RequestBody BeerDto beerDto){

        BeerDto savedDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", BeerController.BASE_URL+"/" + savedDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
	
	@PutMapping({"/{beerId}"})
    public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto){

        beerService.updateBeer(beerId, beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
	
	@DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerService.deleteById(beerId);
    }
}
