package com.learning.beerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.beerservice.bootstrap.BeerLoader;
import com.learning.beerservice.services.BeerService;
import com.learning.beerservice.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static com.learning.beerservice.web.model.BeerStyleEnum.STOUT;
// for rest doc
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.learning.spring", uriPort = 80)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService service;

    @Test
    void getBeerById() throws Exception {

        given(service.getById(any())).willReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                        .param("iscold", "yes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get")
                        ),
                        requestParameters(
                                parameterWithName("iscold").description("Is Beer Cold Query Param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer."),
                                fieldWithPath("version").description("Version Number."),
                                fieldWithPath("createdDate").description("Date Created."),
                                fieldWithPath("lastModifiedDate").description("Date Updated."),
                                fieldWithPath("beerName").description("Beer Name."),
                                fieldWithPath("beerStyle").description("Beer Style."),
                                fieldWithPath("upc").description("UPC of Beer."),
                                fieldWithPath("price").description("Price of Beer."),
                                fieldWithPath("quantityOnHand").description("Quantity On Hand.")
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception {

        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(service.getById(any())).willReturn(getValidBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Beer UPC").attributes(),
                                fields.withPath("price").description("Beer Price"),
                                fields.withPath("quantityOnHand").ignored()
                        )));
    }

    @Test
    void updateBeerById() throws Exception {

        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(service.getById(any())).willReturn(getValidBeerDto());

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

//    @Test
//    void deleteBeerById() throws Exception {
//
//        mockMvc.perform(delete("api/v1/beer/" + UUID.randomUUID().toString())
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("Ale")
                .beerStyle(STOUT)
                .upc(BeerLoader.BEER_1_UPC)
                .price(BigDecimal.valueOf(12.52))
                .build();
    }
}