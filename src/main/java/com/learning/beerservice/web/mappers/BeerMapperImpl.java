//package com.learning.beerservice.web.mappers;
//
//import com.learning.beerservice.domain.Beer;
//import com.learning.beerservice.web.model.BeerDto;
//import com.learning.beerservice.web.model.BeerStyleEnum;
//
//public class BeerMapperImpl implements BeerMapper{
//
//    private final DateMapper dateMapper = new DateMapper();
//
//
//    @Override
//    public BeerDto beerToBeerDto(Beer beer) {
//        if ( beer == null ) {
//            return null;
//        }
//
//        BeerDto.BeerDtoBuilder beerDto = BeerDto.builder();
//
//        beerDto.id( beer.getId() );
//        if ( beer.getVersion() != null ) {
//            beerDto.version( beer.getVersion().intValue() );
//        }
//        beerDto.createdDate( dateMapper.asOffsetDateTime(beer.getCreatedDate() ) );
//        beerDto.lastModifiedDate( dateMapper.asOffsetDateTime(beer.getLastModifiedDate() ) );
//        beerDto.beerName( beer.getBeerName() );
//        if ( beer.getBeerStyle() != null ) {
//            beerDto.beerStyle( Enum.valueOf(BeerStyleEnum.class, beer.getBeerStyle() ) );
//        }
//        beerDto.upc( beer.getUpc() );
//        beerDto.price( beer.getPrice() );
//
//        return beerDto.build();
//    }
//
//    @Override
//    public Beer beerDtoToBeer(BeerDto beerDto) {
//        if ( beerDto == null ) {
//            return null;
//        }
//
//        Beer.BeerBuilder beer = Beer.builder();
//
//        beer.id( beerDto.getId() );
//        if ( beerDto.getVersion() != null ) {
//            beer.version( beerDto.getVersion().longValue() );
//        }
//        beer.createdDate( dateMapper.asTimestamp(beerDto.getCreatedDate() ) );
//        beer.lastModifiedDate( dateMapper.asTimestamp(beerDto.getLastModifiedDate() ) );
//        beer.beerName( beerDto.getBeerName() );
//        if ( beerDto.getBeerStyle() != null ) {
//            beer.beerStyle( beerDto.getBeerStyle().name() );
//        }
//        beer.upc( beerDto.getUpc() );
//        beer.price( beerDto.getPrice() );
//
//        return beer.build();
//    }
//}
