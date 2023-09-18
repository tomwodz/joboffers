package pl.tomwodz.joboffers.feature;

import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;

import java.util.List;

public interface SampleResponseIntegrationTest {

    default String bodyWithZeroOffersJson() {
        return "[]";
    }

}
