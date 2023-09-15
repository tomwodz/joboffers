package pl.tomwodz.joboffers.domain.offer;

import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferFacade;

class OfferConfiguration {

    public OfferFacade offerFacade (OfferRepository offerRepository, ClientOfferFacade clientOfferFacade){
        OfferFactory offerFactory = new OfferFactory();
        return new OfferFacade(offerRepository, offerFactory, clientOfferFacade);
    }
}
