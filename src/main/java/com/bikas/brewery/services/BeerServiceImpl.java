package com.bikas.brewery.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bikas.brewery.web.model.BeerDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

	@Override
	public BeerDto getBeerById(UUID beerId) {
		return BeerDto.builder()
				.id(UUID.randomUUID())
				.beerName("Johny Walker")
				.beerStyle("Pale Ale")
				.build();
	}

	@Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }
	
	@Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        //todo impl - would add a real impl to update beer
    }
	
	@Override
    public void deleteById(UUID beerId) {
        log.debug("Deleting a beer...");
    }
}
