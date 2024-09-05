package com.fastcampuspay.banking.adapter.out.persistence;

import java.util.UUID;

import com.fastcampuspay.banking.domain.FirmbankingRequest;

public class FirmbankingRequestConvertor {
	public static FirmbankingRequest entityToDomain(FirmbankingRequestJpaEntity firmbankingRequestJpaEntity, UUID uuid) {
		return FirmbankingRequest.generateFirmbankingRequest(
			new FirmbankingRequest.FirmbankingRequestId(firmbankingRequestJpaEntity.getRequestFirmbankingId() + ""),
			new FirmbankingRequest.FromBankName(firmbankingRequestJpaEntity.getFromBankName()),
			new FirmbankingRequest.FromBankAccountNumber(firmbankingRequestJpaEntity.getFromBankAccountNumber()),
			new FirmbankingRequest.ToBankName(firmbankingRequestJpaEntity.getToBankName()),
			new FirmbankingRequest.ToBankAccountNumber(firmbankingRequestJpaEntity.getToBankAccountNumber()),
			new FirmbankingRequest.MoneyAmount(firmbankingRequestJpaEntity.getMoneyAmount()),
			new FirmbankingRequest.FirmbankingStatus(firmbankingRequestJpaEntity.getFirmbankingStatus()),
			uuid
		);
	}
}
