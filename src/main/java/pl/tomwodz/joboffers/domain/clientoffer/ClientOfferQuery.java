package pl.tomwodz.joboffers.domain.clientoffer;

import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;

import java.util.List;

public interface ClientOfferQuery {
    List<JobOfferResponse> fetchOffers();

}
