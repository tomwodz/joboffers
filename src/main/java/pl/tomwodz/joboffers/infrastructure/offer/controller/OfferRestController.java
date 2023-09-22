package pl.tomwodz.joboffers.infrastructure.offer.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tomwodz.joboffers.domain.offer.OfferFacade;
import pl.tomwodz.joboffers.domain.offer.dto.OfferRequestDto;
import pl.tomwodz.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/offers")
@AllArgsConstructor
public class OfferRestController {

    private final OfferFacade offerFacade;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffers() {
        return ResponseEntity.ok(this.offerFacade.findAllOffers());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable String id) {
        return ResponseEntity.ok(this.offerFacade.findOfferById(id));
    }

    @PostMapping
    public ResponseEntity<OfferResponseDto> postOffer(@RequestBody @Valid OfferRequestDto offerRequestDto){
        return ResponseEntity.status(CREATED)
                .body(this.offerFacade.saveOffer(offerRequestDto));
    }

}
