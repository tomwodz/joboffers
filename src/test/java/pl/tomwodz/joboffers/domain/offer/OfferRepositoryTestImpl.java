package pl.tomwodz.joboffers.domain.offer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class OfferRepositoryTestImpl implements OfferRepository {
    Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public Optional<Offer> findById(String id) {
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
        String generateId = UUID.randomUUID().toString();
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

    @Override
    public <S extends Offer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Offer> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Offer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
