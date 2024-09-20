package com.fastcampuspay.common.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollbackFirmbankingRequestCommand {
	private String rollbackFirmbankingId;
	@TargetAggregateIdentifier
	private String aggregateIdentifier;
	private String rechargeRequestId;
	private String membershipId;
	private String bankName;
	private String bankAccountNumber;
	private int moneyAmount;
}
