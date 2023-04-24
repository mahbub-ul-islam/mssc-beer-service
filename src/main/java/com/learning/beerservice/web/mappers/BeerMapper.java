package com.learning.beerservice.web.mappers;

import com.learning.beerservice.domain.Beer;
import com.learning.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = {DateMapper.class})
//@Component
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
