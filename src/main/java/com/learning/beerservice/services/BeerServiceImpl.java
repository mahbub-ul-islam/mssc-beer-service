package com.learning.beerservice.services;

import com.learning.beerservice.domain.Beer;
import com.learning.beerservice.repositories.BeerRepository;
import com.learning.beerservice.web.exceptions.NotFoundException;
import com.learning.beerservice.web.mappers.BeerMapper;
import com.learning.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Override
    public BeerDto getById(UUID beerId) {
        return mapper.BeerToBeerDto(repository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return mapper.BeerToBeerDto(repository.save(mapper.BeerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = repository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return mapper.BeerToBeerDto(repository.save(beer));
    }
}
