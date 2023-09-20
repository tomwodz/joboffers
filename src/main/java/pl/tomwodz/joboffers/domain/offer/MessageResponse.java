package pl.tomwodz.joboffers.domain.offer;

enum MessageResponse {
    OFFER_NOT_FOUND("Offer not found: "),
    OFFER_ALREADY_EXISTS("Duplicate - Offer with url already exists: ");

        final String info;

        MessageResponse(String info) {
            this.info = info;
        }

}
