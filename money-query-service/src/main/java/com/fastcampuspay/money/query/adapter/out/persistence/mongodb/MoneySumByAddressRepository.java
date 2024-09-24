package com.fastcampuspay.money.query.adapter.out.persistence.mongodb;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoneySumByAddressRepository extends MongoRepository<MoneySumByAddress, String> {
	Optional<MoneySumByAddress> findByPkAndSk(String pk, String sk);
}
