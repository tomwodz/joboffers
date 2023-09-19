package pl.tomwodz.joboffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;
import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;
import pl.tomwodz.joboffers.domain.offer.dto.OfferRequestDto;
import pl.tomwodz.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OfferFacadeTest {

    OfferRepository offerRepository = new OfferRepositoryTestImpl();

    ClientOfferQuery clientOfferQuery = mock(ClientOfferQuery.class);

    OfferFacade offerFacade = new OfferConfiguration().offerFacade(offerRepository, clientOfferQuery);

    OfferRequestDto offerRequestDto = OfferRequestDto.builder()
            .companyName("Company")
            .position("Programmer")
            .salary("7")
            .offerUrl("https")
            .build();

    @Test
    void ShouldBeAbleSaveOffer(){

        //given

        //when
        OfferResponseDto offerResponseDto = this.offerFacade.saveOffer(offerRequestDto);

        //then
        assertThat(offerResponseDto.id()).isNotNull();
        assertThat(offerResponseDto.companyName()).isEqualTo(offerRequestDto.companyName());
        assertThat(offerResponseDto.position()).isEqualTo(offerRequestDto.position());
        assertThat(offerResponseDto.salary()).isEqualTo(offerRequestDto.salary());
        assertThat(offerResponseDto.offerUrl()).isEqualTo(offerRequestDto.offerUrl());

    }

    @Test
    void ShouldBeAbleFindOfferByIdWhenWasSaved(){

        //given
        OfferResponseDto offerResponseDtoSaved = this.offerFacade.saveOffer(offerRequestDto);

        //when
        OfferResponseDto offerResponseDtoFounded = this.offerFacade.findOfferById(offerResponseDtoSaved.id());

        //then
        assertThat(offerResponseDtoFounded.id()).isNotNull();
        assertThat(offerResponseDtoFounded.companyName()).isEqualTo(offerResponseDtoSaved.companyName());
        assertThat(offerResponseDtoFounded.position()).isEqualTo(offerResponseDtoSaved.position());
        assertThat(offerResponseDtoFounded.salary()).isEqualTo(offerResponseDtoSaved.salary());
        assertThat(offerResponseDtoFounded.offerUrl()).isEqualTo(offerResponseDtoSaved.offerUrl());

    }

    @Test
    void ShouldThrowNotFoundExceptionWhenOfferNotFound(){

        //given
        //when
        //then
        assertThrows(OfferNotFoundException.class, () -> this.offerFacade.findOfferById(""));

    }

    @Test
    void ShouldThrowOfferAlreadyExistException(){

        //given
        OfferResponseDto offerResponseDtoSaved = this.offerFacade.saveOffer(offerRequestDto);

        //when
        OfferRequestDto offerRequestDtoDuplicateUrlSaved = OfferRequestDto.builder()
                .companyName("Company")
                .position("Programmer")
                .salary("7")
                .offerUrl("https")
                .build();

        //then
        assertThrows(OfferAlreadyExistException.class, () -> this.offerFacade.saveOffer(offerRequestDtoDuplicateUrlSaved));

    }

    @Test
    void ShouldBeAbleSaved4OffersWithDifferentUrlWhenDatabaseIsEmpty(){

        //given
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","2","https2"));
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","3","https3"));
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","4","https4"));
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","5","https5"));

        //then
        //when
        assertThat(this.offerFacade.findAllOffers().size()).isEqualTo(4);
    }

    @Test
    void ShouldBeSaveOnlyOffersWithDifferentUrlFromClientOffers(){

        //given
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","2","https2"));
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","3","https3"));
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","4","https4"));
        this.offerFacade.saveOffer(new OfferRequestDto("Company", "Programmer","5","https5"));

        when(clientOfferQuery.fetchOffers()).thenReturn(List.of
                (new JobOfferResponse("Company", "Programmer", "2", "https7"),
                        new JobOfferResponse("Company", "Programmer", "2", "https2")
                        ));


        //when
        List<OfferResponseDto> offersResponseDto = this.offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        //then
        assertThat(offersResponseDto.size()).isEqualTo(1);

    }


}