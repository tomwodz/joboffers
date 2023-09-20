package pl.tomwodz.joboffers.infrastructure.offer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomwodz.joboffers.domain.offer.OfferFacade;
import pl.tomwodz.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

@RestController
@RequestMapping("/offers")
@AllArgsConstructor
public class OfferRestController {

    private final OfferFacade offerFacade;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffers() {
        return ResponseEntity.ok(offerFacade.findAllOffers());
    }

}
