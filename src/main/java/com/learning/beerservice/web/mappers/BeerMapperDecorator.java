package com.learning.beerservice.web.mappers;

import com.learning.beerservice.domain.Beer;
import com.learning.beerservice.services.inventory.BeerInventoryService;
import com.learning.beerservice.web.model.BeerDto;
import com.learning.beerservice.web.model.BeerStyleEnum;
import org.springframework.beans.factory.annotation.Autowired;

public class BeerMapperDecorator implements BeerMapper {

    private final DateMapper dateMapper = new DateMapper();
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService service) {
        this.beerInventoryService = service;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
//        System.out.println("beeer mapper decorator beer : " + beer);
        BeerDto beerDto = mapper.beerToBeerDto(beer);
//        System.out.println("beeer mapper decorator beerDto : " + beerDto);
        beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
//        System.out.println(beerDto);
//        System.out.println("BeerMapperDecorator invoked");
        return beerDto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return mapper.beerDtoToBeer(beerDto);
    }
}
