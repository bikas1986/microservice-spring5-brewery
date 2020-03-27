package com.bikas.brewery.services;

import java.util.UUID;

import com.bikas.brewery.web.model.BeerDto;

public interface BeerService {
	BeerDto getBeerById(UUID beerId);
	
	BeerDto saveNewBeer(BeerDto beerDto);
	
	void updateBeer(UUID beerId, BeerDto beerDto);
	
	void deleteById(UUID beerId);
}
