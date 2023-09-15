package pl.tomwodz.joboffers.domain.offer;

import java.util.List;
import java.util.Optional;

interface OfferRepository {

    Optional<Offer> findById(Long id);

    List<Offer> findAll();

    Offer save(Offer offer);

    boolean existsByOfferUrl(String url);
}
