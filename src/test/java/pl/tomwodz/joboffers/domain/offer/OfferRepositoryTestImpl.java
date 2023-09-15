package pl.tomwodz.joboffers.domain.offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImpl implements OfferRepository {
    Map<Long, Offer> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public Optional<Offer> findById(Long id) {
        if(inMemoryDatabase.containsKey(id)){
            Offer offer = inMemoryDatabase.get(id);
            offer.setId(id);
            return
                    Optional.of(offer);
        } else
            return Optional.empty();
    }

    @Override
    public List<Offer> findAll() {
        return this.inMemoryDatabase.values()
                .stream()
                .toList();
    }

    @Override
    public Offer save(Offer offer) {
        Random random = new Random();
        Long generateId = random.nextLong();
        this.inMemoryDatabase.put(generateId, offer);
        return new Offer(generateId,
                inMemoryDatabase.get(generateId).getCompanyName(),
                inMemoryDatabase.get(generateId).getPosition(),
                inMemoryDatabase.get(generateId).getSalary(),
                inMemoryDatabase.get(generateId).getOfferUrl());
    }

    @Override
    public boolean existsByOfferUrl(String url) {
        return inMemoryDatabase.values()
                .stream()
                .filter(offer -> offer.getOfferUrl().equals(url))
                .findFirst().isPresent();
    }

}
