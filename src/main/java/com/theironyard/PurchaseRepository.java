package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by will on 6/22/16.
 */
public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
    public Iterable<Purchase> findByCategory(String category);

}
