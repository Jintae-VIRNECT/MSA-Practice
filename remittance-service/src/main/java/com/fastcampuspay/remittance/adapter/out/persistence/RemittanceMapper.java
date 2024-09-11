package com.fastcampuspay.remittance.adapter.out.persistence;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fastcampuspay.remittance.domain.Remittance;

@Component
public class RemittanceMapper {
	public Remittance mapToDomainEntity(RemittanceJpaEntity remittanceJpaEntity, UUID uuid) {
		return Remittance.generateRemittance(

		);
	}
}
