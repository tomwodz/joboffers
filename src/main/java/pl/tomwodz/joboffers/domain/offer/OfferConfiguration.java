package pl.tomwodz.joboffers.domain.offer;

import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;

class OfferConfiguration {

    public OfferFacade offerFacade (OfferRepository offerRepository, ClientOfferQuery clientOfferQuery){
        OfferFactory offerFactory = new OfferFactory();
        return new OfferFacade(offerRepository, offerFactory, clientOfferQuery);
    }
}
