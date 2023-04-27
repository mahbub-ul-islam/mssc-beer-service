package com.learning.beerservice.services.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerInventoryDto {

    private UUID id;
    private OffsetDateTime createdDate;
    private OffsetDateTime LastModifiedDate;
    private UUID beerId;
    private Integer quantityOnHand;
}
